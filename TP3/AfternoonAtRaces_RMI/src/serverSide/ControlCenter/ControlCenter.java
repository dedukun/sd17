package serverSide.ControlCenter;

//import Stubs.GenReposStub;
import auxiliary.ReturnStruct;
import interfaces.GenReposInterface;
import auxiliary.SimulPar;
import auxiliary.SpectatorStates;
import auxiliary.TimeVector;
import interfaces.ControlCenterInterface;
import java.util.Arrays;

/**
 * Control Center.<br>
 * Where all general operations happens.
 */
public class ControlCenter implements ControlCenterInterface{

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
    private GenReposInterface genRepos;
    
    /**
     * Reference to Time Vector.
     */
    private TimeVector clk;
    
    /**
     * ControlCenter intilization.
     */
    public ControlCenter(GenReposInterface genRepos){
        this.genRepos= genRepos;

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
    public synchronized ReturnStruct waitForNextRace(int specId, TimeVector clk){
        this.clk.updateTime(clk.getTime());
        //((Spectators) Thread.currentThread()).setState(SpectatorStates.WRS);
        genRepos.setSpectatorState(specId, SpectatorStates.WRS, clk);

        while(waitForNextRace){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        return new ReturnStruct(clk,theresANewRace);
    }

    /**
     * Broker is waiting for the spectators finishing seeing the horses.
     */
    public synchronized ReturnStruct summonHorsesToPaddock(TimeVector clk){
        this.clk.updateTime(clk.getTime());
        while(waitForEvaluation){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        // Reset var for next race
        waitForEvaluation = true;
        return new ReturnStruct(clk);
    }

    /**
     * Last Spectator that finished watching the horses wakes up Broker.
     */
    public synchronized ReturnStruct unblockGoCheckHorses(TimeVector clk){
        this.clk.updateTime(clk.getTime());
        // reset var for next race
        waitForNextRace = true;

        //
        waitForEvaluation = false;

        notifyAll();
        
        return new ReturnStruct(clk);
    }


    /**
     * The last Horse/Jockey pair to arrive at the Paddock wakes up Spectators to go see the parade.
     */
    public synchronized ReturnStruct unblockProceedToPaddock(TimeVector clk){
        this.clk.updateTime(clk.getTime());
        waitForNextRace = false;

        notifyAll(); // Wake up Spectators
        return new ReturnStruct(clk);
    }


    /**
     * Broker is starting the race.
     */
    public synchronized ReturnStruct startTheRace(TimeVector clk){
        this.clk.updateTime(clk.getTime());
        while(waitForEndRaceBroker){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        // Reset for next race
        waitForEndRaceBroker = true;
        return new ReturnStruct(clk);
    }

    /**
     * Last Horse/Jockey pair to make a move wakes up the Broker.
     */
    public synchronized ReturnStruct unblockMakeAMove(TimeVector clk){
        this.clk.updateTime(clk.getTime());
        waitForEndRaceBroker = false;

        notifyAll();
        return new ReturnStruct(clk);
    }

    /**
     * Spectator is watching the race.
     * 
     *   @param specId
     */
    public synchronized ReturnStruct goWatchTheRace(int specId, TimeVector clk){
        this.clk.updateTime(clk.getTime());
        //((Spectators) Thread.currentThread()).setState(SpectatorStates.WAR);
        genRepos.setSpectatorState(specId , SpectatorStates.WAR, clk);

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
        
        return new ReturnStruct(clk);
        
    }

    /**
     * Broker is reporting the results to the Spectators.
     *
     *   @param winners Array with the identifier of the winning Horse/Jockey pair(s)
     */
    public synchronized ReturnStruct reportResults(int[] winners, TimeVector clk){
        this.clk.updateTime(clk.getTime());
        raceWinners = winners;
        waitForEndOfRace = false;

        notifyAll();
        
        return new ReturnStruct(clk);
    }

    /**
     * Spectator if checking if that horse that he betted has won.
     *
     *   @param hjid Horse/Jockey pair identifier
     *   @return true if the pair has won, false if not.
     */
    public synchronized ReturnStruct haveIWon(int hjid, TimeVector clk){
        this.clk.updateTime(clk.getTime());
        for(int winner : raceWinners){
            if(winner == hjid){
                return new ReturnStruct(clk, true);
            }
        }
        return new ReturnStruct(clk, false);
    }

    /**
    * Broker is entertaining the guests.
    */
    public synchronized ReturnStruct entertainTheGuests(TimeVector clk){
        this.clk.updateTime(clk.getTime());
        waitForNextRace = false;
        theresANewRace = false;

        notifyAll();
        
        return new ReturnStruct(clk);
        
    }

    /**
    * Spectator is relaxing.
    *   
    *   @param specId
    */
    public synchronized ReturnStruct relaxABit(int specId, TimeVector clk){
        //((Spectators) Thread.currentThread()).setState(SpectatorStates.CB);
        this.clk.updateTime(clk.getTime());
        
        genRepos.setSpectatorState(specId, SpectatorStates.CB, clk);
        
        return new ReturnStruct(clk);
    }
    
    
    /**
     * Send a message to the General Reposutory telling that this server is shutting down 
     */
    public synchronized void shutdownGenRepo(){
        //genRepos.endServer();
    }
}
