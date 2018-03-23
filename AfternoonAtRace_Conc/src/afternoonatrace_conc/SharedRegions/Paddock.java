package afternoonatrace_conc.SharedRegions;
import afternoonatrace_conc.Main.SimulPar;
import afternoonatrace_conc.Entities.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 *
 */
public class Paddock {

    /**
     * Identifier of last horse leaving paddock.
     */
    private int lastHorse;

    /**
     * Number of horses at the paddock
     */
    private int horsesAtPaddock;

    /**
     * Number of Spectators evaluating Horses.
     */
    private int spectatorsAtParade;

    /**
     * List of Horses agilities.
     */
    private int[] horsesAgilities;

    /**
     * Horse/Jockey pair is in the parade - synchronization condition.
     */
    private boolean paradingHorses;

    /**
     * Spectator is apraising the horses - synchronization condition.
     */
    private boolean evaluatingHorses;

    /**
     * Reference to General Repository
     */
    private GeneralRepository genRepos;


    /**
     * Paddock initialization
     *
     *  @param genRepos Reference to General Repository
     */
    public Paddock(GeneralRepository genRepos){
        this.genRepos = genRepos;

        horsesAtPaddock = 0;
        spectatorsAtParade = 0;
        lastHorse = -1;
        horsesAgilities = new int[SimulPar.C];

        paradingHorses = true;
        evaluatingHorses = true;
    }

    /**
     * Horse/Jockey pair are parading at the paddock.
     */
    public synchronized void proceedToPaddock(){

        ((HorseJockey) Thread.currentThread()).setState(HorseJockey.States.AT_THE_PADDOCK);

        System.out.println(Thread.currentThread().getName() + " at the paddock");

        int horseId = ((HorseJockey) Thread.currentThread()).getHJId();
        int horseAgility = ((HorseJockey) Thread.currentThread()).getAgility();

        horsesAgilities[horseId] = horseAgility;

        horsesAtPaddock++;

        while(paradingHorses){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        horsesAtPaddock--;
        if(horsesAtPaddock == 0)
            lastHorse = horseId;

        System.out.println(Thread.currentThread().getName() + " leaving the paddock");
    }

    /**
     * Checks if current Horse/Jockey pair is the last to arrive at the paddock.
     *
     *   @return
     */
    public synchronized boolean lastArrivedToPaddock() {
        return horsesAtPaddock == SimulPar.C - 1;
    }

    /**
     * Spectator is checking the horses nad chooses the horse to bet.
     *
     *   @return The identifier of the Horse/Jockey pair to bet on
     */
    public synchronized int goCheckHorses(){

        ((Spectators) Thread.currentThread()).setState(Spectators.States.APPRAISING_THE_HORSES);

        System.out.println(((Spectators) Thread.currentThread()).getName() + " is checking the horses");

        spectatorsAtParade++;

        while(evaluatingHorses){
            try{
                wait();
            }catch(InterruptedException e){}
        }
        System.out.println(((Spectators) Thread.currentThread()).getName() + " finished checking the horses");

        // Logic choice of horse TODO

        return ThreadLocalRandom.current().nextInt(1,SimulPar.C);
    }

    /**
     * Spectator checks if he is the last one to check the horses.
     *
     *   @return
     */
    public synchronized boolean lastCheckHorses(){
        if(spectatorsAtParade == SimulPar.S - 1){
            // reset vars for next race
            evaluatingHorses = true;
            spectatorsAtParade = 0;

            return true;
        }
        return false;
    }

    /**
     * Last Spectator to check the horses wakes them up.
     */
    public synchronized void unblockGoCheckHorses(){

        System.out.println(((Spectators) Thread.currentThread()).getName() + " is waking up the Horses");

        paradingHorses = false;

        notifyAll();
    }

    /**
     * Last Horse/Jockey pair leaving the paddock wakes up the Spectators.
     */
    public synchronized void unblockProceedToStartLine(){
        int horseId = ((HorseJockey) Thread.currentThread()).getHJId();
        if( horseId == lastHorse ){
            System.out.println(((HorseJockey) Thread.currentThread()).getName() + " is waking up the Spectators");

            //reset values for next race
            paradingHorses = true;
            lastHorse = -1;

            evaluatingHorses = false;

            notifyAll();
        }
    }
}
