package Main;

import java.util.concurrent.ThreadLocalRandom;
import Stubs.GenReposStub;
import Auxiliar.HorseJockeyStates;
import Auxiliar.SpectatorStates;
import Auxiliar.SimulPar;

/**
 * Paddock.<br>
 * Where horses can be first saw by spectators.
 */
public class Paddock {

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
    private GenReposStub genRepos;


    /**
     * Paddock initialization.
     */
    public Paddock(){
        this.genRepos = new GenReposStub();

        horsesAtPaddock = 0;
        horsesLeftPaddock = 0;
        spectatorsAtParade = 0;
        horsesAgilities = new int[SimulPar.C];

        // sync conditions
        paradingHorses = true;
        evaluatingHorses = true;
    }

    /**
     * Horse/Jockey pair are parading at the paddock.
     * 
     *   @param hId
     *   @param hAgl
     */
    public synchronized void proceedToPaddock(int hId, int hAgl){

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
    }

    /**
     * Checks if current Horse/Jockey pair is the last to arrive at the paddock.
     *
     *   @param hId
     *   @return true if the pair is the last one to arrive, false if not.
     */
    public synchronized boolean lastArrivedToPaddock(int hId) {

        //((HorseJockey) Thread.currentThread()).setState(HorseJockeyStates.ATP);

        int horseId = hId;
        genRepos.setHorseState(horseId, HorseJockeyStates.ATP);

        horsesAtPaddock++;

        if(horsesAtPaddock == SimulPar.C){

            // reset vars for next race
            evaluatingHorses = true;
            spectatorsAtParade = 0;

            return true;
        }
        return false;
    }

    /**
     * Spectator is checking the Horse/Jockey pairs parading at the paddock and chooses the one he is betting on.
     *
     *   @param specId
     *   @return The identifier of the Horse/Jockey pair to bet on
     */
    public synchronized int goCheckHorses(int specId){

        //((Spectators) Thread.currentThread()).setState(SpectatorStates.ATH);

        int spectatorId = specId;

        while(evaluatingHorses){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        int horseToBet = chooseHorse(spectatorId);

        return horseToBet;
    }

    /**
     * Spectator checks if he is the last one to check the horses.
     *
     *   @param specId
     *   @return true if it is the last horse to be checked, false if not.
     */
    public synchronized boolean lastCheckHorses(int specId){

        spectatorsAtParade++;

        //((Spectators) Thread.currentThread()).setState(SpectatorStates.ATH);

        int spectatorId = specId;

        genRepos.setSpectatorState(spectatorId, SpectatorStates.ATH);

        return spectatorsAtParade == SimulPar.S;
    }

    /**
     * Last Spectator to check the horses wakes them up.
     */
    public synchronized void unblockGoCheckHorses(){

        paradingHorses = false;

        notifyAll();
    }

    /**
     * The Spectator chooses the horse depending of who he is.
     *
     *   @param spectatorId Identifier of the Spectator
     *   @return Identifier of the chosen Horse/Jockey pair
     */
    private int chooseHorse(int spectatorId){

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
     * Send a message to the General Reposutory telling that this server is shutting down 
     */
    public synchronized void shutdownGenRepo(){
        genRepos.endServer();
    }
}
