package afternoonatrace_conc.SharedRegions;
import afternoonatrace_conc.Main.SimulPar;
import afternoonatrace_conc.Entities.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;

/**
 *
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
        horseMoving = -1;
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
        trackSize = ThreadLocalRandom.current().nextInt(20, 50);

        System.out.println(Thread.currentThread().getName() + " started the race with size " + trackSize);

        waitToMove = false;
        horseMoving = 0;

        notifyAll();
    }

    /**
     * Horse/Jockey pair is waiting at the start line of the race track.
     */
    public synchronized void proceedToStartLine(){
        ((HorseJockey) Thread.currentThread()).setState(HorseJockey.States.AT_THE_START_LINE);

        System.out.println(Thread.currentThread().getName() + " is at the starting line");

        int horseID = ((HorseJockey) Thread.currentThread()).getHJId();

        while(waitToMove || horseID != horseMoving){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        waitToMove = true;

        System.out.println(Thread.currentThread().getName() + " left the start line");

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
            ((HorseJockey) Thread.currentThread()).setState(HorseJockey.States.RUNNING);
            int horseAgility = ((HorseJockey) Thread.currentThread()).getAgility();
            int move = ThreadLocalRandom.current().nextInt(1, horseAgility);

            horsesTravelledDistance[horseID] += move;
            System.out.println(Thread.currentThread().getName() + " move -> " + move + " (Total:" + horsesTravelledDistance[horseID] + ")");

            if(horsesTravelledDistance[horseID] >= trackSize){
                System.out.println(Thread.currentThread().getName() + " finished the race");
                finishedHorses++;

                // If there are no winners in this group of moves, then this Horse is one of them
                if(!winnersMet){
                    System.out.println(Thread.currentThread().getName() + " won!!");
                    winningHorses.add(horseID);
                }
            }
        }
        else{
            ((HorseJockey) Thread.currentThread()).setState(HorseJockey.States.AT_THE_FINNISH_LINE);
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

        while(waitToMove || horseID != horseMoving){
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
        System.out.println(Thread.currentThread().getName() + " got the results");
        return winningHorses.stream().mapToInt(i->i).toArray();
    }
}
