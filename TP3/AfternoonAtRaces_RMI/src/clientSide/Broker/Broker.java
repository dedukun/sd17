package clientSide.Broker;

import auxiliary.BrokerStates;

import serverSide.*;
import auxiliary.SimulPar;
import auxiliary.TimeVector;
import genclass.GenericIO;
import interfaces.BettingCenterInterface;
import interfaces.ControlCenterInterface;
import interfaces.GenReposInterface;
import interfaces.RaceTrackInterface;
import interfaces.StableInterface;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import registry.RegistryConfiguration;
import serverSide.BettingCenter.BettingCenter;
import serverSide.ControlCenter.ControlCenter;
import serverSide.RaceTrack.RaceTrack;
import serverSide.Stable.Stable;

/**
 * Broker Entity.<br>
 * Definition of a broker.
 */
public class Broker extends Thread{

    /**
     * Broker's name.
     */
    private String name;

    /**
     * Current state of the Broker.
     */
    private BrokerStates bstate;

    /**
     * Reference to Betting Center.
     */
    private BettingCenterInterface bettingCenter;

    /**
     * Reference to Control Center.
     */
    private ControlCenterInterface controlCenter;

    /**
     * Reference to Race Track;
     */
    private RaceTrackInterface raceTrack;

    /**
     * Reference to Stable
     */
    private StableInterface stable;
    
    /**
     * Reference to Time Vector.
     */
    private TimeVector clk;

    /**
     * Broker initialization.
     *
     *   @param name Broker's name
     */
    public Broker(String name, BettingCenterInterface bc, ControlCenterInterface cc, RaceTrackInterface rt, StableInterface st) {
        super(name);
        this.name = name;
        this.bstate = BrokerStates.OTE;

        this.bettingCenter = bc;
        this.controlCenter = cc;
        this.raceTrack = rt;
        this.stable = st;
        
        clk = new TimeVector();
    }

    /**
     * Set new state.
     *
     * 	 @param bstate The state
     */
    public void setState(BrokerStates bstate) {
        this.bstate = bstate;
    }

    /**
     * Get the current state of the Broker.
     *
     *   @return The current state
     */
    public BrokerStates getBState() {
        return bstate;
    }

    /**
     * Broker life cycle.
     */
    @Override
    public void run() {
        //Modificar isto para ir buscar parametetros ao ficheiro de confguração
     GenericIO.writeString ("Nome do nó de processamento onde está localizado o serviço de registo? ");
     String rmiRegHostName = GenericIO.readlnString ();
     GenericIO.writeString ("Número do port de escuta do serviço de registo? ");
     int rmiRegPortNumb = GenericIO.readlnInt ();

     //Vai buscar interface do Betting Center
     try {
            Registry registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
            bettingCenter = (BettingCenterInterface) registry.lookup(RegistryConfiguration.REGISTRY_BETTING_CENTER);
        } catch (RemoteException e) {
            System.out.println("Exception finding logger: " + e.getMessage() + "!");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("Logger is not registed: " + e.getMessage() + "!");
            e.printStackTrace();
            System.exit(1);
    }
     
     //Vai buscar interface do Betting Center
     try {
            Registry registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
            controlCenter = (ControlCenterInterface) registry.lookup(RegistryConfiguration.REGISTRY_CONTROL_CENTER);
        } catch (RemoteException e) {
            System.out.println("Exception finding logger: " + e.getMessage() + "!");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("Logger is not registed: " + e.getMessage() + "!");
            e.printStackTrace();
            System.exit(1);
    }
     
    //Vai buscar interface do Betting Center
     try {
            Registry registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
            stable = (StableInterface) registry.lookup(RegistryConfiguration.REGISTRY_STABLE);
        } catch (RemoteException e) {
            System.out.println("Exception finding logger: " + e.getMessage() + "!");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("Logger is not registed: " + e.getMessage() + "!");
            e.printStackTrace();
            System.exit(1);
    }
     
     //Vai buscar interface do Betting Center
     try {
            Registry registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
            raceTrack = (RaceTrackInterface) registry.lookup(RegistryConfiguration.REGISTRY_RACE_TRACK);
        } catch (RemoteException e) {
            System.out.println("Exception finding logger: " + e.getMessage() + "!");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("Logger is not registed: " + e.getMessage() + "!");
            e.printStackTrace();
            System.exit(1);
    } 
        
        for(int k=0; k < SimulPar.K; k++){
         try {
             double [] horsesWinningProbabilities = stable.summonHorsesToPaddock(k, clk).getRet_dou_arr();
             bettingCenter.setHorsesWinningChances(horsesWinningProbabilities,clk);
             controlCenter.summonHorsesToPaddock(clk); //Blocked
             //unblocked by lastCheckHorses() or placeABet()
             while(!bettingCenter.acceptedAllBets(clk).isRet_bool()){
                 bettingCenter.acceptTheBet(clk); //Blocked
             }
             //Unblocked by unblockMakeAMove()
             raceTrack.startTheRace(clk);
             controlCenter.startTheRace(clk);//Blocked
             int[] winnerHorses = raceTrack.getResults(clk).getRet_int_arr();
             controlCenter.reportResults(winnerHorses, clk);
             if(bettingCenter.areThereAnyWinners(winnerHorses, clk).isRet_bool()){
                 while(!bettingCenter.honouredAllTheBets(clk).isRet_bool())
                     bettingCenter.honourTheBet(clk);//Blocked
             }
             stable.entertainTheGuests(clk); // Unblock Horses
             controlCenter.entertainTheGuests(clk);
         } catch (RemoteException ex) {
             Logger.getLogger(Broker.class.getName()).log(Level.SEVERE, null, ex);
         }
        }

        // send shutdown
        /*bettingCenter.endServer();
        controlCenter.endServer();
        raceTrack.endServer();
        stable.endServer();*/
    }
}
