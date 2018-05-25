package serverSide.Stable;

import auxiliary.SimulPar;
import auxiliary.BrokerStates;
import auxiliary.HorseJockeyStates;
import Stubs.GenReposStub;
import interfaces.StableInterface;

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
    @Override
    public synchronized ReturnStruct proceedToStable(int hId, int hRaceNumber, int horseAgl, TimeVector clk){

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

        return new ReturnStruct(clk);
    }

    /**
     * Broker awakes Horse/Jockey pairs in the stable.
     *
     *   @param raceNumber Number of the race that is going to occur
     *   @return List of the winning chances of the horses in the current race
     */
    @Override
    public synchronized ReturnStruct[] summonHorsesToPaddock(int raceNumber, TimeVector clk){
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

        return new ReturnStruct(clk, horsesChances);
    }

    /**
     * Broker is closing the event and is waking up horses from stable.
     */
    @Override
    public synchronized ReturnStruct entertainTheGuests(, TimeVector clk){
        //((Broker) Thread.currentThread()).setState(BrokerStates.PHAB);
        genRepos.setBrokerState(BrokerStates.PHAB);

        endEvent = true;

        notifyAll();

        return new ReturnStruct(clk);
    }

    /**
     * Send a message to the General Reposutory telling that this server is shutting down
     */
    public synchronized ReturnStruct shutdownGenRepo(, TimeVector clk){
        genRepos.endServer();

        return new ReturnStruct(clk);
    }
}
