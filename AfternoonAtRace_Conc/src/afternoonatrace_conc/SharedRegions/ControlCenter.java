package afternoonatrace_conc.SharedRegions;
import afternoonatrace_conc.Entities.*;
import afternoonatrace_conc.Main.SimulPar;
import java.util.ArrayList;
import java.lang.*;

/**
 *
 */
public class ControlCenter{

    /**
     * Boolean that represents all the races were already runned. //TODO change this
     */
    private boolean eventFinished;

    /**
     * Broker is waiting for spectators to evaluate the horses - synchronization condition.
     */
    private boolean waitForEvaluation;

    /**
     * Spectators are waiting for the next race to start - synchronization condition.
     */
    private boolean waitForNextRace;

    /**
     * Spectators are watching the race - synchronization condition.
     */
    private boolean waitForEndOfRace;

    /**
     * Broker is waiting for the race to finish - synchronization condition.
     */
    private boolean waitForEndOfRace2;

    /**
     * List with race winners.
     */
    private int[] raceWinners;

    /**
     * Reference to General Repository.
     */
    private GeneralRepository genRepos;

    /**
     * ControlCenter intilization.
     *
     *    @param genRepos reference to General Repository
     */
    public ControlCenter(GeneralRepository genRepos){
        this.genRepos=genRepos;

        // sync conditions
        eventFinished = false;
        waitForEvaluation = true;
        waitForNextRace = true;
        waitForEndOfRace = true;
        waitForEndOfRace2 = true;
    }

    /**
     * The Spectator is awaiting in the Control Center for the next race to start.
     *
     *   @return There's at least a race left
     */
    public synchronized boolean waitForNextRace(){

        ((Spectators) Thread.currentThread()).setState(Spectators.States.WAITING_FOR_A_RACE_TO_START);

        System.out.println(((Spectators) Thread.currentThread()).getName() + " is waitng for next race");

        while(waitForNextRace){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        System.out.println(((Spectators) Thread.currentThread()).getName() + " is going to parade");

        return !eventFinished;
    }

    /**
     * Broker is waiting for the spectators finishing seeing the horses.
     */
    public synchronized void  summonHorsesToPaddock(){

        ((Broker) Thread.currentThread()).setState(Broker.States.ANNOUNCING_NEXT_RACE);

        while(waitForEvaluation){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        // Reset var for next race
        waitForEvaluation = true;
    }

    /**
     * Last Spectator that finished watching the horses wakes up Broker
     */
    public synchronized void unblockGoCheckHorses(){

        System.out.println(((Spectators) Thread.currentThread()).getName() + " is waking up the Broker");

        // reset var for next race
        waitForNextRace = true;

        //
        waitForEvaluation = false;

        notifyAll();
    }


    /**
     * Horses wake up Spectators to go see the parade.
     */
    public synchronized void unblockProceedToPaddock(){

        System.out.println(((HorseJockey) Thread.currentThread()).getName() + " is waking up Spectators to parade");

        waitForNextRace = false;

        notifyAll(); // Wake up Spectators
    }


    /**
     * Broker is starting the race.
     */
    public synchronized void startTheRace(){
        ((Broker) Thread.currentThread()).setState(Broker.States.SUPERVISING_THE_RACE);

        System.out.println(((Broker) Thread.currentThread()).getName() + " is supervising the race");

        while(waitForEndOfRace2){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        System.out.println(((Broker) Thread.currentThread()).getName() + " finished supervising the race");

    }

    /**
     * Last Horse/Jockey pair to make a move wakes up the Broker.
     */
    public synchronized void unblockMakeAMove(){
        waitForEndOfRace2 = false;

        notifyAll();
    }

    /**
     * Spectator is watching the race.
     */
    public synchronized void goWatchTheRace(){
        ((Spectators) Thread.currentThread()).setState(Spectators.States.WATCHING_A_RACE);

        System.out.println(Thread.currentThread().getName() + " is watching the race");

        while(waitForEndOfRace){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        System.out.println(Thread.currentThread().getName() + " finished watching the race");
    }

    /**
     * Broker is reporting the results to the Spectators.
     *
     *   @param winners Array with the identifier of the winning Horse/Jockey pair(s)
     */
    public synchronized void reportResults(int[] winners){
        raceWinners = winners;
        waitForEndOfRace = false;

        notifyAll();
    }

    /**
     * Spectator if checking if that horse that he betted has won.
     *
     *   @param hjid Horse/Jocker pair identifier
     */
    public synchronized boolean haveIWon(int hjid){
        for(int winner : raceWinners){
            if(winner == hjid){
                System.out.println(Thread.currentThread().getName() + " WON THE BET");
                return true;
            }
        }
        System.out.println(Thread.currentThread().getName() + " DIDNT WIN");
        return false;
    }

    public synchronized void entertainTheGuests(){
        System.out.println(Thread.currentThread().getName() + " is enternaining the guests");

        ((Broker) Thread.currentThread()).setState(Broker.States.PLAYING_HOST_AT_THE_BAR);

        eventFinished = true;

        notifyAll();
    }

    public synchronized void relaxABit(){
        System.out.println(Thread.currentThread().getName() + " is relaxing");

        ((Spectators) Thread.currentThread()).setState(Spectators.States.CELEBRATING);
    }
}
