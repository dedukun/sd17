package afternoonatrace_conc.SharedRegions;
import afternoonatrace_conc.Entities.*;

/**
 *
 */
public class Stable {

    /**
     * Current race number.
     */
    private int currentRace;

    /**
     * Reference to General Repository
     */
    private GeneralRepository genRepos;

    /**
     * Horse/Jockey waiting at the stable for the start of the parade - synchronization condition.
     */
    private boolean waitAtStable;

    /**
     * Stable initialization
     *
     *  @param genRepos Reference to General Repository
     */
    public Stable(GeneralRepository genRepos){
        this.genRepos = genRepos;

        currentRace = 0;

        // Sync conditions
        waitAtStable = true;
    }

    /**
     * Horse/Jockey pair waits at the stable.
     */
    public synchronized void proceedToStable(){

        ((HorseJockey) Thread.currentThread()).setState(HorseJockey.States.AT_THE_STABLE);

        System.out.println(Thread.currentThread().getName() + " is in the stable");

        int horseRaceNumber = ((HorseJockey) Thread.currentThread()).getRaceNumber();

        while(waitAtStable || horseRaceNumber != currentRace){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        System.out.println(Thread.currentThread().getName() + " left the stable");
    }

    /**
     * Broker awakes Horse/Jockey pairs in the stable.
     *
     *   @param raceNumber Number of the race that is going to occur
     */
    public synchronized void summonHorsesToPaddock(int raceNumber){

        currentRace = raceNumber;

        waitAtStable = false;

        notifyAll(); // Wakes up Horses
    }
}
