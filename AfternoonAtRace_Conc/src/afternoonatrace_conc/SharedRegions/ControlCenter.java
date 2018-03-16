package afternoonatrace_conc.SharedRegions;
import afternoonatrace_conc.Entities.*;
import afternoonatrace_conc.Main.*;
import java.lang.*;

/**
 *
 */
public class ControlCenter{


    /**
     * Broker is waiting for spectators to evaluate the horses - synchronization condition
     */
    private boolean waitForEvalution;

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
        waitForEvalution=true;
    }

    public static boolean unblockProceedToPaddock(){
        return true;
    }

    public static void unblockMakeAMove(){

    }

    public static synchronized void  summonHorsesToPaddock(){

        ((Broker) Thread.currentThread()).setBState(Broker.States.ANNOUNCING_NEXT_RACE);

        while(waitForEvalution){
            waitForEvalution = false;
            try{
                    wait();
            }catch(InterruptedException e){}
        }

        waitForEvalution = true;
    }

    public static void startTheRace(){

    }

    public static void reportResults(){

    }

    public static boolean areThereAnyWinners(){
        return true;
    }

    public static void entertainTheGuests(){

    }

    public static boolean waitForNextRace(){
        return true;
    }

    public static boolean lastCheckHorses(){
        return true;
    }

    public static void goWatchTheRace(){

    }

    public static boolean haveIWon(){
        return true;
    }

    public static void relaxABit(){

    }
}
