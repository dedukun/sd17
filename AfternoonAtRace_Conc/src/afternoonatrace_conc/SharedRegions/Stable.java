package afternoonatrace_conc.SharedRegions;
import afternoonatrace_conc.Entities.*;
import afternoonatrace_conc.Main.SimulPar;

/**
 *
 */
public class Stable {

    /**
     * Current race number.
     */
    private int currentRace;

    /**
     * Current horses at the stable for the nextrace number.
     */
    private int currentHorses;

    /**
     * List of Horses winning chances.
     */
    private int[] horsesWinningChances;

    /**
     * Reference to General Repository
     */
    private GeneralRepository genRepos;

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

        currentRace = 0;
        currentHorses = 0;
        int totalHorses = SimulPar.C * SimulPar.K;
        horsesWinningChances = new int[totalHorses];

        // Sync conditions
        waitAtStable = true;
    }

    /**
     * Horse/Jockey pair waits at the stable.
     */
    public synchronized void proceedToStable(){

        ((HorseJockey) Thread.currentThread()).setState(HorseJockey.States.AT_THE_STABLE);

        System.out.println(Thread.currentThread().getName() + " is in the stable");

        int horseRaceNumber = ((HorseJockey) Thread.currentThread()).getRaceNumber();
        int horseId = ((HorseJockey) Thread.currentThread()).getHJId();
        int horseAgility = ((HorseJockey) Thread.currentThread()).getAgility();

        int index = horseId + (SimulPar.C * horseRaceNumber);

        horsesWinningChances[index] = horseAgility;

        currentHorses++;

        while(waitAtStable || horseRaceNumber != currentRace){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        currentHorses--;

        // Last horse leaving the stable resets it for the next race
        if(currentHorses == 0){
            waitAtStable = true;
        }

        System.out.println(Thread.currentThread().getName() + " left the stable");
    }

    /**
     * Broker awakes Horse/Jockey pairs in the stable.
     *
     *   @param raceNumber Number of the race that is going to occur
     *   @return List of the winning chances of the horses in the current race
     */
    public synchronized int[] summonHorsesToPaddock(int raceNumber){

        // Wake up horses
        currentRace = raceNumber;
        waitAtStable = false;

        notifyAll(); // Wakes up Horses

        int [] horsesChances = new int[SimulPar.C];
        int sumAgilities = 0;
        for(int i = 0; i < SimulPar.C; i++){
            int idx = i + (SimulPar.C * raceNumber);
            sumAgilities += horsesWinningChances[ idx ];
        }

        for(int i = 0; i < SimulPar.C; i++){
            int idx = i + (SimulPar.C * raceNumber);
            horsesChances[i] = horsesWinningChances[ idx ] / sumAgilities;
        }

        return horsesChances;
    }
}
