package serverSide.ControlCenter;

//import Stubs.GenReposStub;
import auxiliary.ReturnStruct;
import interfaces.GenReposInterface;
import extras.SimulPar;
import extras.SpectatorStates;
import auxiliary.TimeVector;
import interfaces.ControlCenterInterface;
import interfaces.Register;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.logging.Level;
import registry.RegistryConfiguration;
import serverSide.BettingCenter.BettingCenter;

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
     * Shutdown signal
     */
    private boolean waitShut;

    /**
     * Connected clients
     */
    private int numClients;

    /**
     * ControlCenter intilization.
     */
    public ControlCenter(GenReposInterface genRepos) throws RemoteException{
        this.genRepos= genRepos;
        clk = new TimeVector();
        waitShut = true;

        spectatosLeavingStands = 0;

        // sync conditions
        theresANewRace = true;
        waitForEvaluation = true;
        waitForNextRace = true;
        waitForEndOfRace = true;
        waitForEndRaceBroker = true;

        numClients = 3;
    }

    /**
     * The Spectator is waiting in the Control Center for the next race to start.
     *
     *   @param specId
     *   @return There's at least a race left
     */
    @Override
    public synchronized ReturnStruct waitForNextRace(int specId, TimeVector clk) throws RemoteException {
        this.clk.updateTime(clk.getTime());
        //((Spectators) Thread.currentThread()).setState(SpectatorStates.WRS);
        genRepos.setSpectatorState(specId, SpectatorStates.WRS, clk);

        while(waitForNextRace){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        return new ReturnStruct(this.clk,theresANewRace);
    }

    /**
     * Broker is waiting for the spectators finishing seeing the horses.
     */
    @Override
    public synchronized ReturnStruct summonHorsesToPaddock(TimeVector clk) throws RemoteException{
        this.clk.updateTime(clk.getTime());
        while(waitForEvaluation){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        // Reset var for next race
        waitForEvaluation = true;
        return new ReturnStruct(this.clk);
    }

    /**
     * Last Spectator that finished watching the horses wakes up Broker.
     */
    @Override
    public synchronized ReturnStruct unblockGoCheckHorses(TimeVector clk) throws RemoteException{
        this.clk.updateTime(clk.getTime());
        // reset var for next race
        waitForNextRace = true;

        //
        waitForEvaluation = false;

        notifyAll();

        return new ReturnStruct(this.clk);
    }


    /**
     * The last Horse/Jockey pair to arrive at the Paddock wakes up Spectators to go see the parade.
     */
    @Override
    public synchronized ReturnStruct unblockProceedToPaddock(TimeVector clk) throws RemoteException{
        this.clk.updateTime(clk.getTime());
        waitForNextRace = false;

        notifyAll(); // Wake up Spectators
        return new ReturnStruct(this.clk);
    }


    /**
     * Broker is starting the race.
     */
    @Override
    public synchronized ReturnStruct startTheRace(TimeVector clk) throws RemoteException{
        this.clk.updateTime(clk.getTime());
        while(waitForEndRaceBroker){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        // Reset for next race
        waitForEndRaceBroker = true;
        return new ReturnStruct(this.clk);
    }

    /**
     * Last Horse/Jockey pair to make a move wakes up the Broker.
     */
    @Override
    public synchronized ReturnStruct unblockMakeAMove(TimeVector clk) throws RemoteException{
        this.clk.updateTime(clk.getTime());
        waitForEndRaceBroker = false;

        notifyAll();
        return new ReturnStruct(this.clk);
    }

    /**
     * Spectator is watching the race.
     *
     *   @param specId
     */
    @Override
    public synchronized ReturnStruct goWatchTheRace(int specId, TimeVector clk) throws RemoteException{
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

        return new ReturnStruct(this.clk);

    }

    /**
     * Broker is reporting the results to the Spectators.
     *
     *   @param winners Array with the identifier of the winning Horse/Jockey pair(s)
     */
    @Override
    public synchronized ReturnStruct reportResults(int[] winners, TimeVector clk) throws RemoteException{
        this.clk.updateTime(clk.getTime());
        raceWinners = winners;
        waitForEndOfRace = false;

        notifyAll();

        return new ReturnStruct(this.clk);
    }

    /**
     * Spectator if checking if that horse that he betted has won.
     *
     *   @param hjid Horse/Jockey pair identifier
     *   @return true if the pair has won, false if not.
     */
    @Override
    public synchronized ReturnStruct haveIWon(int hjid, TimeVector clk) throws RemoteException{
        this.clk.updateTime(clk.getTime());
        for(int winner : raceWinners){
            if(winner == hjid){
                return new ReturnStruct(this.clk, true);
            }
        }
        return new ReturnStruct(this.clk, false);
    }

    /**
    * Broker is entertaining the guests.
    */
    @Override
    public synchronized ReturnStruct entertainTheGuests(TimeVector clk){
        this.clk.updateTime(clk.getTime());
        waitForNextRace = false;
        theresANewRace = false;

        notifyAll();

        return new ReturnStruct(this.clk);

    }

    /**
    * Spectator is relaxing.
    *
    *   @param specId
    */
    @Override
    public synchronized ReturnStruct relaxABit(int specId, TimeVector clk) throws RemoteException{
        //((Spectators) Thread.currentThread()).setState(SpectatorStates.CB);
        this.clk.updateTime(clk.getTime());

        genRepos.setSpectatorState(specId, SpectatorStates.CB, clk);

        return new ReturnStruct(this.clk);
    }

    /**
     * Server is waiting for a shutdown signal
     */
    public synchronized void waitForShutdown() {
        try{
            while(waitShut){
                this.wait();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }


    /**
     * Disconnect client from server
     *
     *   @return Clk
     */
    @Override
    public synchronized ReturnStruct disconnect(TimeVector clk) throws RemoteException{
        this.clk.updateTime(clk.getTime());
        numClients--;

        if(numClients == 0){
            genRepos.disconnect(clk);
            waitShut = false;
            notifyAll();
        }

        return new ReturnStruct(this.clk);
    }
}
