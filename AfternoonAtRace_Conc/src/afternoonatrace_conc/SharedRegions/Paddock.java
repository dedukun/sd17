package afternoonatrace_conc.SharedRegions;
import afternoonatrace_conc.Main.SimulPar;
import afternoonatrace_conc.Entities.*;

/**
 *
 *
 */
public class Paddock {

    /**
     * Number of horses in the Paddock.
     */
    private int horsesAtPaddock;

    /**
     * Number of Spectators evaluating Horses.
     */
    private int spectatorsAtParade;


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

        paradingHorses = true;
        evaluatingHorses = true;
    }


    /**
     * Horse/Jockey pair is at the paddock.
     */
    public synchronized void proceedToPaddock(){

        ((HorseJockey) Thread.currentThread()).setState(HorseJockey.States.AT_THE_PADDOCK);

        horsesAtPaddock++; // Probably needs to be an array or something

        while(paradingHorses){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        horsesAtPaddock--;
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
     * Check if it's the last Spectator turn to check the horses.
     *
     *   @return
     */
    public synchronized boolean lastCheckHorses(){
        return spectatorsAtParade == SimulPar.S - 1;
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
     * Spectator is checking the horses.
     */
    public synchronized int goCheckHorses(){

        ((Spectators) Thread.currentThread()).setState(Spectators.States.APPRAISING_THE_HORSES);

        System.out.println(((Spectators) Thread.currentThread()).getName() + " is checking the horses");

        spectatorsAtParade++; // Probably needs to be an array or something

        while(evaluatingHorses){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        spectatorsAtParade--;

        System.out.println(((Spectators) Thread.currentThread()).getName() + " finished checking the horses");

        return ThreadLocalRandom.current.nextInt(1,SimulPar.C);
    }


    public void prooceedToStartLine(){

    }


    public synchronized void unblockProceedToStartLine(){
        if( horsesAtPaddock == 0){

            System.out.println(((HorseJockey) Thread.currentThread()).getName() + " is waking up the Spectators");

            evaluatingHorses = false;

            notifyAll();
        }
    }
}
