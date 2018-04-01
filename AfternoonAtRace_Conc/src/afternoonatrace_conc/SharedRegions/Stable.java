package afternoonatrace_conc.SharedRegions;
import afternoonatrace_conc.Entities.*;
import afternoonatrace_conc.Main.SimulPar;

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
    private GeneralRepository genRepos;

    /**
     * Number of horses that left the stable.
     */
    private int horsesThatLeftStable;

    /**
     * Horse/Jockey waiting at the stable for the start of the parade - synchronization condition.
     */
    private boolean waitAtStable;

    /**
     * Stable initialization
     *
     *  @param genRepos Reference to General Repository
     */
    public Stable(GeneralRepository genRepos){
        this.genRepos = genRepos;

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
     */
    public synchronized void proceedToStable(){

        ((HorseJockey) Thread.currentThread()).setState(HorseJockey.States.ATS);


        int horseRaceNumber = ((HorseJockey) Thread.currentThread()).getRaceNumber();
        int horseId = ((HorseJockey) Thread.currentThread()).getHJId();
        int horseAgility = ((HorseJockey) Thread.currentThread()).getAgility();

        int index = horseId + (SimulPar.C * horseRaceNumber);
        horsesAgilities[index] = horseAgility;

        genRepos.setHorseState(horseId, HorseJockey.States.ATS);

        while( !endEvent && horseRaceNumber != currentRace){
            try{
                wait();
            }catch(InterruptedException e){}
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

        genRepos.setRaceNumber(raceNumber);

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

            genRepos.setHorseState(i, HorseJockey.States.ATS);
            genRepos.setHorseAgility(i, (int)horseAgility);
            genRepos.setOdds(i, horsesChances[i]*100);
        }

        return horsesChances;
    }

    /**
     * Broker is closing the event and is waking up horses from stable.
     */
    public synchronized void entertainTheGuests(){

        endEvent = true;

        notifyAll();
    }
}
