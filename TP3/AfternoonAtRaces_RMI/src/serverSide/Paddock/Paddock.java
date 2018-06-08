package serverSide.Paddock;

import java.util.concurrent.ThreadLocalRandom;
import auxiliary.HorseJockeyStates;
import auxiliary.ReturnStruct;
import auxiliary.SpectatorStates;
import auxiliary.SimulPar;
import auxiliary.TimeVector;
import interfaces.GenReposInterface;
import interfaces.PaddockInterface;
import interfaces.Register;
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
 * Paddock.<br>
 * Where horses can be first saw by spectators.
 */
public class Paddock  implements PaddockInterface{

    /**
     * Number of horses at the paddock
     */
    private int horsesAtPaddock;

    /**
     * Number of horses that left the Paddock.
     */
    private int horsesLeftPaddock;

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
     * Paddock initialization.
     */
    public Paddock(GenReposInterface genRepos) throws RemoteException{
        this.genRepos = genRepos;
        clk = new TimeVector();
        horsesAtPaddock = 0;
        horsesLeftPaddock = 0;
        spectatorsAtParade = 0;
        horsesAgilities = new int[SimulPar.C];

        // sync conditions
        paradingHorses = true;
        evaluatingHorses = true;
        
        waitShut = true;
    }

    /**
     * Horse/Jockey pair are parading at the paddock.
     *
     *   @param hId
     *   @param hAgl
     */
    @Override
    public synchronized ReturnStruct proceedToPaddock(int hId, int hAgl, TimeVector clk) throws RemoteException{
        this.clk.updateTime(clk.getTime());
        int horseId = hId;
        int horseAgility = hAgl;

        horsesAgilities[horseId] = horseAgility;

        while(paradingHorses){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        horsesLeftPaddock++;

        // Last Horse/Jockey pair wakes up the Spectators
        if(horsesLeftPaddock == SimulPar.C){

            // reset for next race
            horsesAtPaddock = 0;
            horsesLeftPaddock = 0;
            paradingHorses = true;

            evaluatingHorses = false;

            notifyAll();
        }

        return new ReturnStruct(this.clk);
    }

    /**
     * Checks if current Horse/Jockey pair is the last to arrive at the paddock.
     *
     *   @param hId
     *   @return true if the pair is the last one to arrive, false if not.
     */
    @Override
    public synchronized ReturnStruct lastArrivedToPaddock(int hId, TimeVector clk) throws RemoteException{
        this.clk.updateTime(clk.getTime());
        //((HorseJockey) Thread.currentThread()).setState(HorseJockeyStates.ATP);

        int horseId = hId;
        genRepos.setHorseState(horseId, HorseJockeyStates.ATP, clk);

        horsesAtPaddock++;

        if(horsesAtPaddock == SimulPar.C){

            // reset vars for next race
            evaluatingHorses = true;
            spectatorsAtParade = 0;

            return new ReturnStruct(clk, true);
        }
        return new ReturnStruct(this.clk, false);
    }

    /**
     * Spectator is checking the Horse/Jockey pairs parading at the paddock and chooses the one he is betting on.
     *
     *   @param specId
     *   @return The identifier of the Horse/Jockey pair to bet on
     */
    @Override
    public synchronized ReturnStruct goCheckHorses(int specId, TimeVector clk) throws RemoteException{
        this.clk.updateTime(clk.getTime());
        //((Spectators) Thread.currentThread()).setState(SpectatorStates.ATH);

        int spectatorId = specId;

        while(evaluatingHorses){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        int horseToBet = chooseHorse(spectatorId);

        return new ReturnStruct(this.clk, horseToBet);
    }

    /**
     * Spectator checks if he is the last one to check the horses.
     *
     *   @param specId
     *   @return true if it is the last horse to be checked, false if not.
     */
    @Override
    public synchronized ReturnStruct lastCheckHorses(int specId, TimeVector clk) throws RemoteException{
        this.clk.updateTime(clk.getTime());
        spectatorsAtParade++;

        //((Spectators) Thread.currentThread()).setState(SpectatorStates.ATH);

        int spectatorId = specId;

        genRepos.setSpectatorState(spectatorId, SpectatorStates.ATH, clk);

        return new ReturnStruct(this.clk, spectatorsAtParade == SimulPar.S);
    }

    /**
     * Last Spectator to check the horses wakes them up.
     */
    @Override
    public synchronized ReturnStruct unblockGoCheckHorses(TimeVector clk) throws RemoteException{
        this.clk.updateTime(clk.getTime());
        paradingHorses = false;

        notifyAll();

        return new ReturnStruct(this.clk);
    }

    /**
     * The Spectator chooses the horse depending of who he is.
     *
     *   @param spectatorId Identifier of the Spectator
     *   @return Identifier of the chosen Horse/Jockey pair
     */
    private int chooseHorse(int spectatorId) throws RemoteException{

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
     * Send a message to the General Reposutory telling that this server is shutting down
     */
    @Override
    public synchronized void shutdown() throws RemoteException{
        //Bloquear server atraves de mecanismos de sincroniação

        String nameEntryBase = RegistryConfiguration.REGISTRY_RMI;
        String nameEntryObject = RegistryConfiguration.REGISTRY_PADDOCK;
        Registry registry = null;
        Register reg = null;
        String rmiRegHostName;
        int rmiRegPortNumb;
       
        rmiRegHostName = RegistryConfiguration.REGISTRY_RMI_HOST;
        rmiRegPortNumb = RegistryConfiguration.REGISTRY_RMI_PORT;
     
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
