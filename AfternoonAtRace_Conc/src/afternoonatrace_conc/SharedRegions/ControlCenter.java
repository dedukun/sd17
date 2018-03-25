package afternoonatrace_conc.SharedRegions;
import afternoonatrace_conc.Entities.*;
import afternoonatrace_conc.Main.SimulPar;

/**
 * Control Center.<br>
 * Where all general operations happens.
 */
public class ControlCenter{

    /**
     * Boolean that represents all the races were already runned. //TODO change this
     */
    private boolean theresANewRace;

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
    private boolean waitForEndRaceBroker;

    /**
     * Number of spectators that left the watching stands.
     */
    private int spectatosLeavingStands;

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

        spectatosLeavingStands = 0;

        // sync conditions
        theresANewRace = true;
        waitForEvaluation = true;
        waitForNextRace = true;
        waitForEndOfRace = true;
        waitForEndRaceBroker = true;
    }

    /**
     * The Spectator is awaiting in the Control Center for the next race to start.
     *
     *   @return There's at least a race left
     */
    public synchronized boolean waitForNextRace(){

        ((Spectators) Thread.currentThread()).setState(Spectators.States.WRS);

        while(waitForNextRace){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        genRepos.setSpectatorState((((Spectators) Thread.currentThread()).getSID()), Spectators.States.WRS);
        return theresANewRace;
    }

    /**
     * Broker is waiting for the spectators finishing seeing the horses.
     */
    public synchronized void  summonHorsesToPaddock(){
        ((Broker) Thread.currentThread()).setState(Broker.States.ANR);

        while(waitForEvaluation){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        // Reset var for next race
        waitForEvaluation = true;

        genRepos.setBrokerState(Broker.States.ANR);
    }

    /**
     * Last Spectator that finished watching the horses wakes up Broker.
     */
    public synchronized void unblockGoCheckHorses(){

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

        waitForNextRace = false;

        notifyAll(); // Wake up Spectators
    }


    /**
     * Broker is starting the race.
     */
    public synchronized void startTheRace(){
        ((Broker) Thread.currentThread()).setState(Broker.States.STR);

        while(waitForEndRaceBroker){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        // Reset for next race
        waitForEndRaceBroker = true;

        genRepos.setBrokerState(Broker.States.STR);
    }

    /**
     * Last Horse/Jockey pair to make a move wakes up the Broker.
     */
    public synchronized void unblockMakeAMove(){
        waitForEndRaceBroker = false;

        notifyAll();
    }

    /**
     * Spectator is watching the race.
     */
    public synchronized void goWatchTheRace(){
        ((Spectators) Thread.currentThread()).setState(Spectators.States.WAR);

        while(waitForEndOfRace){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        spectatosLeavingStands++;
        if(spectatosLeavingStands == SimulPar.S){
            // Reset Vars
            spectatosLeavingStands = 0;
            waitForEndOfRace = true;
        }

        genRepos.setSpectatorState((((Spectators) Thread.currentThread()).getSID()) , Spectators.States.WAR);
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
     *   @return true if the pair has won, false if not.
     */
    public synchronized boolean haveIWon(int hjid){
        for(int winner : raceWinners){
            if(winner == hjid){
                return true;
            }
        }
        return false;
    }

    /*
    * Broker entertains the guests.
    */
    public synchronized void entertainTheGuests(){
        ((Broker) Thread.currentThread()).setState(Broker.States.PHAB);

        waitForNextRace = false;
        theresANewRace = false;
        genRepos.setBrokerState(Broker.States.PHAB);

        notifyAll();
    }
    
    /*
    * Spectator relaxes.
    */
    public synchronized void relaxABit(){

        ((Spectators) Thread.currentThread()).setState(Spectators.States.CB);

        genRepos.setSpectatorState((((Spectators) Thread.currentThread()).getSID()), Spectators.States.CB);

    }
}
