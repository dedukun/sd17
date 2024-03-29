package Main;

import Auxiliar.SimulPar;
import Auxiliar.BrokerStates;
import Auxiliar.HorseJockeyStates;
import Stubs.GenReposStub;

/**
 * Stable.<br>
 * Where horses stay before a race.
 */
public class Stable {

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
    private GenReposStub genRepos;

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
    public Stable(){
        this.genRepos = new GenReposStub();

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
    public synchronized void proceedToStable(int hId, int hRaceNumber, int horseAgl){

        //((HorseJockey) Thread.currentThread()).setState(HorseJockeyStates.ATS);
        int horseRaceNumber = hRaceNumber;
        int horseId = hId;
        int horseAgility = horseAgl;
        
        int index = horseId + (SimulPar.C * horseRaceNumber);
        horsesAgilities[index] = horseAgility;

        if(hRaceNumber == currentRace || (currentRace == -1 && hRaceNumber == 0)){
            genRepos.setHorseState(horseId, HorseJockeyStates.ATS);
        }

        while( !endEvent && horseRaceNumber != currentRace){
            try{
                wait();
            }catch(InterruptedException e){}
        }
        
        if(endEvent){
            genRepos.setHorseState(horseId, HorseJockeyStates.ATS);
        }

        horsesThatLeftStable++;
        if(horsesThatLeftStable == SimulPar.C){
            //Reset vars
            horsesThatLeftStable = 0;
            currentRace = -1;
        }
    }

    /**
     * Broker awakes Horse/Jockey pairs in the stable.
     *
     *   @param raceNumber Number of the race that is going to occur
     *   @return List of the winning chances of the horses in the current race
     */
    public synchronized double[] summonHorsesToPaddock(int raceNumber){
        //((Broker) Thread.currentThread()).setState(BrokerStates.ANR);
        
        genRepos.setBrokerState(BrokerStates.OTE);
        genRepos.setRaceNumber(raceNumber);
        genRepos.setBrokerState(BrokerStates.ANR);

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

            genRepos.setHorseAgility(i, (int)horseAgility);
            genRepos.setOdds(i, horsesChances[i]*100);
        }

        return horsesChances;
    }

    /**
     * Broker is closing the event and is waking up horses from stable.
     */
    public synchronized void entertainTheGuests(){
        //((Broker) Thread.currentThread()).setState(BrokerStates.PHAB);
        genRepos.setBrokerState(BrokerStates.PHAB);

        endEvent = true;

        notifyAll();
    }
    
    /**
     * Send a message to the General Reposutory telling that this server is shutting down 
     */
    public synchronized void shutdownGenRepo(){
        genRepos.endServer();
    }
}
