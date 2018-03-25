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
     * List of Horses winning chances.
     */
    private int[] horsesAgilities;

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

        currentRace = -1;
        int totalHorses = SimulPar.C * SimulPar.K;
        horsesAgilities = new int[totalHorses];

        // Sync conditions
        waitAtStable = true;
    }

    /**
     * Horse/Jockey pair waits at the stable.
     */
    public synchronized void proceedToStable(){

        ((HorseJockey) Thread.currentThread()).setState(HorseJockey.States.ATS);

        System.out.println(Thread.currentThread().getName() + " is in the stable");

        int horseRaceNumber = ((HorseJockey) Thread.currentThread()).getRaceNumber();
        int horseId = ((HorseJockey) Thread.currentThread()).getHJId();
        int horseAgility = ((HorseJockey) Thread.currentThread()).getAgility();

        int index = horseId + (SimulPar.C * horseRaceNumber);
        horsesAgilities[index] = horseAgility;

        while(horseRaceNumber != currentRace){
            try{
                wait();
            }catch(InterruptedException e){}
        }
        
        genRepos.setHorseState(horseId, HorseJockey.States.ATS);
        System.out.println(Thread.currentThread().getName() + " left the stable");
    }

    /**
     * Broker awakes Horse/Jockey pairs in the stable.
     *
     *   @param raceNumber Number of the race that is going to occur
     *   @return List of the winning chances of the horses in the current race
     */
    public synchronized double[] summonHorsesToPaddock(int raceNumber){

        System.out.println("\n" + Thread.currentThread().getName() + " -> Race " + raceNumber + " is starting");
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
        }

        return horsesChances;
    }

    /**
     * Horses procced to stable and then die.
     */
    public synchronized void proceedToStableToDie(){
        ((HorseJockey) Thread.currentThread()).setState(HorseJockey.States.ATS);
        
        genRepos.setHorseState(((HorseJockey) Thread.currentThread()).getHJId(), HorseJockey.States.ATS);
        
        System.out.println(Thread.currentThread().getName() + " is in the stable and died");
    }
}
