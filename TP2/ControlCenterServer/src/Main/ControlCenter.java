package Main;

import Stubs.GenReposStub;
import Auxiliar.SimulPar;
import Auxiliar.SpectatorStates;
import java.util.Arrays;

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
    private GenReposStub genRepos;

    /**
     * ControlCenter intilization.
     */
    public ControlCenter(){
        this.genRepos= new GenReposStub();

        spectatosLeavingStands = 0;

        // sync conditions
        theresANewRace = true;
        waitForEvaluation = true;
        waitForNextRace = true;
        waitForEndOfRace = true;
        waitForEndRaceBroker = true;
    }

    /**
     * The Spectator is waiting in the Control Center for the next race to start.
     *
     *   @param specId
     *   @return There's at least a race left
     */
    public synchronized boolean waitForNextRace(int specId){

        //((Spectators) Thread.currentThread()).setState(SpectatorStates.WRS);
        genRepos.setSpectatorState(specId, SpectatorStates.WRS);

        while(waitForNextRace){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        return theresANewRace;
    }

    /**
     * Broker is waiting for the spectators finishing seeing the horses.
     */
    public synchronized void  summonHorsesToPaddock(){
        while(waitForEvaluation){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        // Reset var for next race
        waitForEvaluation = true;
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
     * The last Horse/Jockey pair to arrive at the Paddock wakes up Spectators to go see the parade.
     */
    public synchronized void unblockProceedToPaddock(){

        waitForNextRace = false;

        notifyAll(); // Wake up Spectators
    }


    /**
     * Broker is starting the race.
     */
    public synchronized void startTheRace(){
        while(waitForEndRaceBroker){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        // Reset for next race
        waitForEndRaceBroker = true;
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
     * 
     *   @param specId
     */
    public synchronized void goWatchTheRace(int specId){
        //((Spectators) Thread.currentThread()).setState(SpectatorStates.WAR);
        genRepos.setSpectatorState(specId , SpectatorStates.WAR);

        while(waitForEndOfRace){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        System.out.println("Spectator "+specId+" finished WAR");
        spectatosLeavingStands++;
        if(spectatosLeavingStands == SimulPar.S){
            // Reset Vars
            spectatosLeavingStands = 0;
            waitForEndOfRace = true;
        }
    }

    /**
     * Broker is reporting the results to the Spectators.
     *
     *   @param winners Array with the identifier of the winning Horse/Jockey pair(s)
     */
    public synchronized void reportResults(int[] winners){
        System.out.println("Report Resuts "+Arrays.toString(winners));
        raceWinners = winners;
        waitForEndOfRace = false;

        notifyAll();
    }

    /**
     * Spectator if checking if that horse that he betted has won.
     *
     *   @param hjid Horse/Jockey pair identifier
     *   @return true if the pair has won, false if not.
     */
    public synchronized boolean haveIWon(int hjid){
        System.out.println("WINNERS "+Arrays.toString(raceWinners));
        for(int winner : raceWinners){
            if(winner == hjid){
                return true;
            }
        }
        return false;
    }

    /**
    * Broker is entertaining the guests.
    */
    public synchronized void entertainTheGuests(){
        waitForNextRace = false;
        theresANewRace = false;

        notifyAll();
    }

    /**
    * Spectator is relaxing.
    *   
    *   @param specId
    */
    public synchronized void relaxABit(int specId){
        //((Spectators) Thread.currentThread()).setState(SpectatorStates.CB);

        genRepos.setSpectatorState(specId, SpectatorStates.CB);
    }
}
