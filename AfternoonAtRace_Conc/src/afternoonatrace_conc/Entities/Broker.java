package afternoonatrace_conc.Entities;
import afternoonatrace_conc.Main.SimulPar;
import afternoonatrace_conc.SharedRegions.*;

/**
 * Broker Thread.
 */
public class Broker extends Thread{

    /**
     * Broker's name.
     */
    private String name;

    /**
     * Current state of the Broker.
     */
    private States bstate;

    /**
     * Enumerate with Broker States.
     */
    public static enum States
    {
        OPENING_THE_EVENT,
        ANNOUNCING_NEXT_RACE,
        WAITING_FOR_BETS,
        SUPERVISING_THE_RACE,
        SETTLING_ACCOUNTS,
        PLAYING_HOST_AT_THE_BAR
    }

    /**
     * Reference to Betting Center.
     */
    private BettingCenter bettingCenter;

    /**
     * Reference to Control Center.
     */
    private ControlCenter controlCenter;

    /**
     * Reference to Race Track;
     */
    private RaceTrack raceTrack;

    /**
     * Reference to Stable
     */
    private Stable stable;

    /**
     * Broker initialization.
     *
     *   @param name Broker's name
     *   @param bettingCenter Reference to Betting Center
     *   @param controlCenter Reference to Control Center
     *   @param raceTrack Reference to Race Track
     *   @param stable Reference to Stable
     */
    public Broker(String name, BettingCenter bettingCenter, ControlCenter controlCenter, RaceTrack raceTrack, Stable stable) {
        super(name);
        this.name = name;
        this.bstate = States.OPENING_THE_EVENT;

        this.bettingCenter = bettingCenter;
        this.controlCenter = controlCenter;
        this.raceTrack = raceTrack;
        this.stable = stable;
    }

    /**
     * Set new state.
     *
     * 	 @param bstate The state
     */
    public void setState(States bstate) {
        this.bstate = bstate;
    }

    /**
     * Get the current state of the Broker.
     *
     *   @return The current state
     */
    public States getBState() {
        return bstate;
    }

    /**
     * Broker life cycle.
     */
    @Override
    public void run(){
        System.out.println(name + " is opening the event");
        for(int k=0; k < SimulPar.K; k++){
            double [] horsesWinningProbabilities = stable.summonHorsesToPaddock(k);
            bettingCenter.setHorsesWinningChances(horsesWinningProbabilities);
            controlCenter.summonHorsesToPaddock(); //Blocked
            //unblocked by lastCheckHorses() or placeABet()
            while(!bettingCenter.acceptedAllBets()){
                bettingCenter.acceptTheBet(); //Blocked
            }
            //Unblocked by unblockMakeAMove()
            raceTrack.startTheRace();
            controlCenter.startTheRace();//Blocked
            int[] winnerHorses = raceTrack.getResults();
            controlCenter.reportResults(winnerHorses);
            if(bettingCenter.areThereAnyWinners(winnerHorses)){
                while(!bettingCenter.honouredAllTheBets())
                    bettingCenter.honourTheBet();//Blocked
            }
        }
        stable.entertainTheGuests(); // Unblock Horses
        controlCenter.entertainTheGuests();
    }
}
