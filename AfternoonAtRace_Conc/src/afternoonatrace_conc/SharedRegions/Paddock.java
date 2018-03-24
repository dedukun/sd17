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
    private int lastHorseLeaving;

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
        lastHorseLeaving = -1;
        horsesAgilities = new int[SimulPar.C];

        // sync conditions
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
            lastHorseLeaving = horseId;

        System.out.println(Thread.currentThread().getName() + " leaving the paddock");
    }

    /**
     * Checks if current Horse/Jockey pair is the last to arrive at the paddock.
     *
     *   @return
     */
    public synchronized boolean lastArrivedToPaddock() {
        System.out.println("Last Arrived -> " + horsesAtPaddock);
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

        int spectatorId = ((Spectators) Thread.currentThread()).getSID();
        int horseToBet = chooseHorse(spectatorId);

        System.out.println(((Spectators) Thread.currentThread()).getName() + " choose horse -> " + horseToBet);

        return horseToBet;
    }

    /**
     * Spectator checks if he is the last one to check the horses.
     *
     *   @return
     */
    public synchronized boolean lastCheckHorses(){
        System.out.println(Thread.currentThread().getName() + " is last? -> " + spectatorsAtParade);
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
        if( horseId == lastHorseLeaving ){
            System.out.println(((HorseJockey) Thread.currentThread()).getName() + " is waking up the Spectators");

            //reset values for next race
            paradingHorses = true;
            lastHorseLeaving = -1;

            evaluatingHorses = false;

            notifyAll();
        }
    }

    /**
     * The Spectator chooses the horse depending of who he is.
     *
     *   @param spectatorId Identifier of the Spectator
     *   @return Identifier of the chosen Horse/Jockey pair
     */
    private int chooseHorse(int spectatorId){

        int horse = 0;
        switch(spectatorId){
            // Best horse
            case 0:
            case 1:
                int maxAgility = 0;
                for(int i = 0; i < horsesAgilities.length; i++){
                    if( horsesAgilities[i] > maxAgility){
                        maxAgility = horsesAgilities[i];
                        horse = i;
                    }
                }
                break;
            // Worst horse
            case 2:
                int minAgility = 10;
                for(int i = 0; i < horsesAgilities.length; i++){
                    if( horsesAgilities[i] < minAgility){
                        minAgility = horsesAgilities[i];
                        horse = i;
                    }
                }
                break;
            default:
                horse = ThreadLocalRandom.current().nextInt(1,SimulPar.C);
        }
        return horse;
    }
}
