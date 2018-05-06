package racetrackserver.Entities;
import afternoonatrace_conc.Main.SimulPar;
import afternoonatrace_conc.Entities.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Race track.<br>
 * Where the race happens.
 */
public class RaceTrack {

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
    private GeneralRepository genRepos;

    /**
     * Race Track initialization
     *
     *  @param genRepos Reference to General Repository
     */
    public RaceTrack(GeneralRepository genRepos){
        this.genRepos = genRepos;

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
    public synchronized void startTheRace(){
        ((Broker) Thread.currentThread()).setState(BrokerStates.STR);
        genRepos.setBrokerState(BrokerStates.STR);

        trackSize = ThreadLocalRandom.current().nextInt(20, 50);
        genRepos.setTrackSize(trackSize);

        // Prepare variables for new race
        Arrays.fill(horsesTravelledDistance, 0);
        horseMoving = 0;
        winningHorses.clear();
        winnersMet = false;
        finishedHorses = 0;

        waitToMove = false;

        notifyAll();
    }

    /**
     * Horse/Jockey pair is waiting at the start line of the race track.
     */
    public synchronized void proceedToStartLine(){
        ((HorseJockey) Thread.currentThread()).setState(HorseJockeyStates.ASL);

        int horseID = ((HorseJockey) Thread.currentThread()).getHJId();

        genRepos.setHorseState(horseID, HorseJockeyStates.ASL);

        while(waitToMove || horseID != horseMoving){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        waitToMove = true;

    }

    /**
     * Horse/Jockey pair makes a move, wakes up next Horse/Jockey pair and checks if the race has ended.
     *
     *   @return Boolean representing if the race has ended
     */
    public synchronized boolean makeAMove(){

        int horseID = ((HorseJockey) Thread.currentThread()).getHJId();

        // Check if horse already finished the race
        if(horsesTravelledDistance[horseID] < trackSize){
            ((HorseJockey) Thread.currentThread()).setState(HorseJockeyStates.RU);

            int horseAgility = ((HorseJockey) Thread.currentThread()).getAgility();
            int move = ThreadLocalRandom.current().nextInt(1, horseAgility);

            horsesTravelledDistance[horseID] += move;

            genRepos.setHorsePosition(horseID, horsesTravelledDistance[horseID]);
            genRepos.setHorseIteration(horseID);
            genRepos.setHorseState(horseID, HorseJockeyStates.RU);

            if(horsesTravelledDistance[horseID] >= trackSize){

                ((HorseJockey) Thread.currentThread()).setState(HorseJockeyStates.AFL);

                finishedHorses++;
                // If there are no winners in this group of moves, then this Horse is one of them
                if(!winnersMet){
                    if(!winningHorses.isEmpty()){
                        if(horsesTravelledDistance[horseID] > horsesTravelledDistance[winningHorses.get(0)]){
                            for(int secPlaceId : winningHorses){
                                genRepos.setHorseEnd(secPlaceId, 2);
                            }

                            winningHorses.clear();
                            winningHorses.add(horseID);
                        }
                        else if(horsesTravelledDistance[horseID] == horsesTravelledDistance[winningHorses.get(0)]){
                            genRepos.setHorseEnd(horseID, 1);

                            winningHorses.add(horseID);
                        }
                    }
                    else{
                        winningHorses.add(horseID);

                        genRepos.setHorseEnd(horseID, 1);
                    }
                }
                else{
                    genRepos.setHorseEnd(horseID, finishedHorses);
                }

                genRepos.setHorseState(horseID, HorseJockeyStates.AFL);
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

        return finishedHorses == SimulPar.C;
    }

    /**
     * Horse/Jockey pair is waiting to make another move and returns the a boolean representing if the race has finished.
     *
     *   @return Race finished
     */
    public synchronized boolean hasRaceFinished(){

        int horseID = ((HorseJockey) Thread.currentThread()).getHJId();

        while( (finishedHorses != SimulPar.C) && (waitToMove || horseID != horseMoving)){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        waitToMove = true;

        return finishedHorses == SimulPar.C;
    }

    /**
     * Broker is getting the list winner(s) of the race.
     *
     *   @return Array of identifier of the horse(s) that have won the race
     */
    public synchronized int[] getResults(){
        return winningHorses.stream().mapToInt(i->i).toArray();
    }
}
