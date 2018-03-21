package afternoonatrace_conc.SharedRegions;
import afternoonatrace_conc.Main.SimulPar;
import afternoonatrace_conc.Entities.*;
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

        // Sync conditions
        waitToMove = true;
    }

    /**
     * Broker wakes up one horse.
     */
    public synchronized void startTheRace(){
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

        int horseID = ((HorseJockey) Thread.currentThread()).getHJID();

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

        System.out.println(Thread.currentThread().getName() + "is making a move");

        // Check if horse already finished the race
        if(horsesTravelledDistance[horseID] < SimulPar.trackSize){
            int horseID = ((HorseJockey) Thread.currentThread()).getHJID();
            int horseAgility = ((HorseJockey) Thread.currentThread()).getAgility();
            int move = ThreadLocalRandom.current().nextInt(1, horseAgilities[horseID]);

            horsesTravelledDistance[horseID] += move;
        }

        //Wake up next horse
        waitToMove = false;
        horseMoving++;

        notifyAll();

        return ;

    }

    /**
     * Horse/Jockey pair is waiting to make another move and returns the a boolean representing if the race has finished.
     *
     *   @return Race finished
     */
    public synchronized boolean hasRaceFinished(){
        ((HorseJockey) Thread.currentThread()).setState(HorseJockey.States.RUNNING);

        int horseID = ((HorseJockey) Thread.currentThread()).getHJID();

        while(waitToMove || horseID != horseMoving){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        waitToMove = true;

        return false;
    }

    /**
     * Broker is getting the list winner(s) of the race.
     *
     *   @return Array of identifier of the horse(s) that have won the race
     */
    public synchronized int[] getResults(){
        return winningHorses.toArray();
    }
}
