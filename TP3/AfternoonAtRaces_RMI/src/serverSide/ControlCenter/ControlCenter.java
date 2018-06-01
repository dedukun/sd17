package serverSide.ControlCenter;

//import Stubs.GenReposStub;
import auxiliary.ReturnStruct;
import interfaces.GenReposInterface;
import auxiliary.SimulPar;
import auxiliary.SpectatorStates;
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
     * ControlCenter intilization.
     */
    public ControlCenter(GenReposInterface genRepos) throws RemoteException{
        this.genRepos= genRepos;
        clk = new TimeVector();
        
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
     * Send a message to the General Reposutory telling that this server is shutting down
     */
    @Override
    public synchronized void shutdown() throws RemoteException{
        //Bloquear server atraves de mecanismos de sincroniação

        String nameEntryBase = RegistryConfiguration.REGISTRY_RMI;
        String nameEntryObject = RegistryConfiguration.REGISTRY_CONTROL_CENTER;
        Registry registry = null;
        Register reg = null;
        String rmiRegHostName;
        int rmiRegPortNumb;
       
        rmiRegHostName = RegistryConfiguration.REGISTRY_RMI_HOST;
        rmiRegPortNumb = RegistryConfiguration.REGISTRY_RMI_PORT ;
     
        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        } catch (RemoteException ex) {
            java.util.logging.Logger.getLogger(BettingCenter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
            reg = (Register) registry.lookup(nameEntryBase);
        } catch (RemoteException e) {
            System.out.println("RegisterRemoteObject lookup exception: " + e.getMessage());
            java.util.logging.Logger.getLogger(BettingCenter.class.getName()).log(Level.SEVERE, null, e);
        } catch (NotBoundException e) {
            System.out.println("RegisterRemoteObject not bound exception: " + e.getMessage());
            java.util.logging.Logger.getLogger(BettingCenter.class.getName()).log(Level.SEVERE, null, e);
        }
        
        //reg.unbind , retirar referenceia do registo
        try {
            reg.unbind(nameEntryObject);
        } catch (RemoteException e) {
            System.out.println("ControlCenter registration exception: " + e.getMessage());
            java.util.logging.Logger.getLogger(BettingCenter.class.getName()).log(Level.SEVERE, null, e);
        } catch (NotBoundException e) {
            System.out.println("ControlCenter not bound exception: " + e.getMessage());
            java.util.logging.Logger.getLogger(BettingCenter.class.getName()).log(Level.SEVERE, null, e);
        }
        
        //matar thread base, unexportObject
        try {
            UnicastRemoteObject.unexportObject(this, true);
        } catch (NoSuchObjectException ex) {
            java.util.logging.Logger.getLogger(BettingCenter.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("ControlCenter shutdown.");
    }
}
