package Main;

import Auxiliar.BrokerStates;

import Stubs.BettingCenterStub;
import Stubs.ControlCenterStub;
import Stubs.RaceTrackStub;
import Stubs.StableStub;

import Auxiliar.SimulPar;

/**
 * Broker Entity.<br>
 * Definition of a broker.
 */
public class Broker extends Thread{

    /**
     * Broker's name.
     */
    private String name;

    /**
     * Current state of the Broker.
     */
    private BrokerStates bstate;

    /**
     * Reference to Betting Center.
     */
    private BettingCenterStub bettingCenter;

    /**
     * Reference to Control Center.
     */
    private ControlCenterStub controlCenter;

    /**
     * Reference to Race Track;
     */
    private RaceTrackStub raceTrack;

    /**
     * Reference to Stable
     */
    private StableStub stable;

    /**
     * Broker initialization.
     *
     *   @param name Broker's name
     */
    public Broker(String name) {
        super(name);
        this.name = name;
        this.bstate = BrokerStates.OTE;

        this.bettingCenter = new BettingCenterStub();
        this.controlCenter = new ControlCenterStub();
        this.raceTrack = new RaceTrackStub();
        this.stable = new StableStub();
    }

    /**
     * Set new state.
     *
     * 	 @param bstate The state
     */
    public void setState(BrokerStates bstate) {
        this.bstate = bstate;
    }

    /**
     * Get the current state of the Broker.
     *
     *   @return The current state
     */
    public BrokerStates getBState() {
        return bstate;
    }

    /**
     * Broker life cycle.
     */
    @Override
    public void run(){
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
