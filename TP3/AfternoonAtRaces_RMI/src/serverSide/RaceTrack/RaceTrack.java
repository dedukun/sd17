package serverSide.RaceTrack;

import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.Arrays;

import auxiliary.ReturnStruct;
import auxiliary.SimulPar;
import auxiliary.BrokerStates;
import auxiliary.HorseJockeyStates;
import auxiliary.TimeVector;
import interfaces.GenReposInterface;
import interfaces.RaceTrackInterface;
import java.rmi.RemoteException;

/**
 * Race track.<br>
 * Where the race happens.
 */
public class RaceTrack implements RaceTrackInterface{

    /**
     * Horse/Jockey is waiting to make a move - synchronization condition.
     */
    private boolean waitToMove;

    /**
     * Array with the current distance travelled by every horse.
     */
    private int[] horsesTravelledDistance;

    /**
     * Current Horse/Jockey pair making a move.
     */
    private int horseMoving;

    /**
     * List of the identifier of the winning horses of the current race.
     */
    private ArrayList<Integer> winningHorses;

    /**
     * Some Horse/Jockey pair(s) won the race.
     */
    private boolean winnersMet;

    /**
     * Number of horses that finished the race.
     */
    private int finishedHorses;

    /**
     * Track size.
     */
    private int trackSize;

    /**
     * Reference to General Repository
     */
    private GenReposInterface genRepos;
    
    /**
     * Reference to Time Vector.
     */
    private TimeVector clk;

    /**
     * Race Track initialization
     */
    public RaceTrack(GenReposInterface genRepos) throws RemoteException{
        this.genRepos = genRepos;
        clk = new TimeVector();
        horsesTravelledDistance = new int[SimulPar.C];
        horseMoving = 0;
        winningHorses = new ArrayList<>();
        winnersMet = false;
        finishedHorses = 0;
        trackSize = 0;

        // Sync conditions
        waitToMove = true;
    }

    /**
     * Broker wakes up one horse.
     */
    @Override
    public synchronized ReturnStruct startTheRace(TimeVector clk) throws RemoteException{
        this.clk.updateTime(clk.getTime());
        //((Broker) Thread.currentThread()).setState(BrokerStates.STR);
        genRepos.setBrokerState(BrokerStates.STR, this.clk);

        trackSize = ThreadLocalRandom.current().nextInt(20, 50);
        genRepos.setTrackSize(trackSize, this.clk);

        // Prepare variables for new race
        Arrays.fill(horsesTravelledDistance, 0);
        horseMoving = 0;
        winningHorses.clear();
        winnersMet = false;
        finishedHorses = 0;

        waitToMove = false;

        notifyAll();

        return new ReturnStruct(this.clk);
    }

    /**
     * Horse/Jockey pair is waiting at the start line of the race track.
     */
    @Override
    public synchronized ReturnStruct proceedToStartLine(int hId, TimeVector clk) throws RemoteException{
        //((HorseJockey) Thread.currentThread()).setState(HorseJockeyStates.ASL);

        int horseID = hId;
        genRepos.setHorseState(horseID, HorseJockeyStates.ASL, this.clk);

        while(waitToMove || horseID != horseMoving){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        waitToMove = true;


        return new ReturnStruct(this.clk);
    }

    /**
     * Horse/Jockey pair makes a move, wakes up next Horse/Jockey pair and checks if the race has ended.
     *
     *   @param hId
     *   @param hAgl
     *   @return Boolean representing if the race has ended
     */
    @Override
    public synchronized ReturnStruct makeAMove(int hId, int hAgl, TimeVector clk) throws RemoteException{
        this.clk.updateTime(clk.getTime());
        int horseID = hId;

        // Check if horse already finished the race
        if(horsesTravelledDistance[horseID] < trackSize){
            //((HorseJockey) Thread.currentThread()).setState(HorseJockeyStates.RU);

            int horseAgility = hAgl;
            int move = ThreadLocalRandom.current().nextInt(1, horseAgility);

            horsesTravelledDistance[horseID] += move;

            genRepos.setHorsePosition(horseID, horsesTravelledDistance[horseID], this.clk);
            genRepos.setHorseIteration(horseID, this.clk);
            genRepos.setHorseState(horseID, HorseJockeyStates.RU, this.clk);

            if(horsesTravelledDistance[horseID] >= trackSize){

                //((HorseJockey) Thread.currentThread()).setState(HorseJockeyStates.AFL);

                finishedHorses++;
                // If there are no winners in this group of moves, then this Horse is one of them
                if(!winnersMet){
                    if(!winningHorses.isEmpty()){
                        if(horsesTravelledDistance[horseID] > horsesTravelledDistance[winningHorses.get(0)]){
                            for(int secPlaceId : winningHorses){
                                genRepos.setHorseEnd(secPlaceId, 2, this.clk);
                            }

                            winningHorses.clear();
                            winningHorses.add(horseID);
                        }
                        else if(horsesTravelledDistance[horseID] == horsesTravelledDistance[winningHorses.get(0)]){
                            genRepos.setHorseEnd(horseID, 1, this.clk);

                            winningHorses.add(horseID);
                        }
                    }
                    else{
                        winningHorses.add(horseID);

                        genRepos.setHorseEnd(horseID, 1, this.clk);
                    }
                }
                else{
                    genRepos.setHorseEnd(horseID, finishedHorses,this.clk);
                }

                genRepos.setHorseState(horseID, HorseJockeyStates.AFL, this.clk);
            }
        }

        //Wake up next horse
        waitToMove = false;
        horseMoving++;

        // Check if all horses move the round
        if(horseMoving == SimulPar.C){
            horseMoving = 0;

            if(!winningHorses.isEmpty()){
                winnersMet = true;
            }
        }

        notifyAll();

        return new ReturnStruct(clk, finishedHorses == SimulPar.C);
    }

    /**
     * Horse/Jockey pair is waiting to make another move and returns the a boolean representing if the race has finished.
     *
     *   @param hId
     *   @return Race finished
     */
    @Override
    public synchronized ReturnStruct hasRaceFinished(int hId, TimeVector clk){
        this.clk.updateTime(clk.getTime());
        int horseID = hId;

        while( (finishedHorses != SimulPar.C) && (waitToMove || horseID != horseMoving)){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        waitToMove = true;

        return new ReturnStruct(clk, finishedHorses == SimulPar.C);
    }

    /**
     * Broker is getting the list winner(s) of the race.
     *
     *   @return Array of identifier of the horse(s) that have won the race
     */
    @Override
    public synchronized ReturnStruct getResults(TimeVector clk){
        this.clk.updateTime(clk.getTime());
        return new ReturnStruct(clk, winningHorses.stream().mapToInt(i->i).toArray());
    }

    /**
     * Send a message to the General Reposutory telling that this server is shutting down
     */
    public synchronized void shutdownGenRepo(TimeVector clk){
        //genRepos.endServer();

        //return new ReturnStruct(this.clk);
    }
}
