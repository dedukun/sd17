package serverSide.Paddock;

import java.util.concurrent.ThreadLocalRandom;
import auxiliary.HorseJockeyStates;
import auxiliary.ReturnStruct;
import auxiliary.SpectatorStates;
import extras.SimulPar;
import auxiliary.TimeVector;
import interfaces.GenReposInterface;
import interfaces.PaddockInterface;
import java.rmi.RemoteException;

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
     * Connected clients
     */
    private int numClients;

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
        numClients = 2;
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
     * Disconnect client from server.
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
