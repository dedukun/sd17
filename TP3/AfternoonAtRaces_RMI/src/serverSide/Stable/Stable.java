package serverSide.Stable;

import auxiliary.SimulPar;
import auxiliary.BrokerStates;
import auxiliary.HorseJockeyStates;
import auxiliary.ReturnStruct;
import auxiliary.TimeVector;
import interfaces.GenReposInterface;
import interfaces.Register;
import interfaces.StableInterface;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import registry.RegistryConfiguration;
import serverSide.BettingCenter.BettingCenter;

/**
 * Stable.<br>
 * Where horses stay before a race.
 */
public class Stable implements StableInterface{

    /**
     * Current race number.
     */
    private int currentRace;

    /**
     * List of Horses winning chances.
     */
    private int[] horsesAgilities;

    /**
     * End of event.
     */
    private boolean endEvent;

    /**
     * Reference to General Repository
     */
    private GenReposInterface genRepos;
    
    /**
     * Reference to Time Vector.
     */
    private TimeVector clk;

    /**
     * Number of horses that left the stable.
     */
    private int horsesThatLeftStable;

    /**
     * Horse/Jockey waiting at the stable for the start of the parade - synchronization condition.
     */
    private boolean waitAtStable;

    /**
     * Stable initialization.
     */
    public Stable(GenReposInterface genRepos) throws RemoteException{
        this.genRepos = genRepos;
        clk = new TimeVector();
        currentRace = -1;
        int totalHorses = SimulPar.C * SimulPar.K;
        horsesAgilities = new int[totalHorses];
        horsesThatLeftStable = 0;

        // Sync conditions
        waitAtStable = true;
        endEvent = false;
    }

    /**
     * Horse/Jockey pair waits at the stable.
     *
     *   @param hId
     *   @param hRaceNumber
     *   @param horseAgl
     */
    @Override
    public synchronized ReturnStruct proceedToStable(int hId, int hRaceNumber, int horseAgl, TimeVector clk) throws RemoteException{
        this.clk.updateTime(clk.getTime());
        //((HorseJockey) Thread.currentThread()).setState(HorseJockeyStates.ATS);
        int horseRaceNumber = hRaceNumber;
        int horseId = hId;
        int horseAgility = horseAgl;

        int index = horseId + (SimulPar.C * horseRaceNumber);
        horsesAgilities[index] = horseAgility;

        if(hRaceNumber == currentRace || (currentRace == -1 && hRaceNumber == 0)){
            genRepos.setHorseState(horseId, HorseJockeyStates.ATS, this.clk);
        }

        while( !endEvent && horseRaceNumber != currentRace){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        if(endEvent){
            genRepos.setHorseState(horseId, HorseJockeyStates.ATS, this.clk);
        }

        horsesThatLeftStable++;
        if(horsesThatLeftStable == SimulPar.C){
            //Reset vars
            horsesThatLeftStable = 0;
            currentRace = -1;
        }

        return new ReturnStruct(this.clk);
    }

    /**
     * Broker awakes Horse/Jockey pairs in the stable.
     *
     *   @param raceNumber Number of the race that is going to occur
     *   @return List of the winning chances of the horses in the current race
     */
    @Override
    public synchronized ReturnStruct summonHorsesToPaddock(int raceNumber, TimeVector clk) throws RemoteException{
        this.clk.updateTime(clk.getTime());
        //((Broker) Thread.currentThread()).setState(BrokerStates.ANR);

        if(raceNumber == 0)
            genRepos.setBrokerState(BrokerStates.OTE, this.clk);
        genRepos.setRaceNumber(raceNumber,this.clk);
        genRepos.setBrokerState(BrokerStates.ANR,this.clk);

        // Wake up horses
        currentRace = raceNumber;
        waitAtStable = false;

        notifyAll(); // Wakes up Horses

        double [] horsesChances = new double[SimulPar.C];
        int sumAgilities = 0;
        for(int i = 0; i < SimulPar.C; i++){
            int idx = i + (SimulPar.C * raceNumber);
            sumAgilities += horsesAgilities[ idx ];
        }

        for(int i = 0; i < SimulPar.C; i++){
            int idx = i + (SimulPar.C * raceNumber);
            double horseAgility = horsesAgilities[ idx ];
            horsesChances[i] =  horseAgility / sumAgilities;

            genRepos.setHorseAgility(i, (int)horseAgility,this.clk);
            genRepos.setOdds(i, horsesChances[i]*100,this.clk);
        }

        return new ReturnStruct(this.clk, horsesChances);
    }

    /**
     * Broker is closing the event and is waking up horses from stable.
     */
    @Override
    public synchronized ReturnStruct entertainTheGuests(TimeVector clk) throws RemoteException{
        this.clk.updateTime(clk.getTime());
        //((Broker) Thread.currentThread()).setState(BrokerStates.PHAB);
        genRepos.setBrokerState(BrokerStates.PHAB,this.clk);

        endEvent = true;

        notifyAll();

        return new ReturnStruct(this.clk);
    }

    /**
     * Send a message to the General Reposutory telling that this server is shutting down
     */
    @Override
    public synchronized void shutdown() throws RemoteException{
        
    //Bloquear server atraves de mecanismos de sincroniação
    
        String nameEntryBase = RegistryConfiguration.REGISTRY_RMI;
        String nameEntryObject = RegistryConfiguration.REGISTRY_STABLE;
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
            System.out.println("Paddock registration exception: " + e.getMessage());
            java.util.logging.Logger.getLogger(BettingCenter.class.getName()).log(Level.SEVERE, null, e);
        } catch (NotBoundException e) {
            System.out.println("Paddock not bound exception: " + e.getMessage());
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
