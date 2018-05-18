package clientSide.Broker;

import auxiliary.BrokerStates;

import serverSide.*;
import auxiliary.SimulPar;
import serverSide.BettingCenter.BettingCenter;
import serverSide.ControlCenter.ControlCenter;
import serverSide.RaceTrack.RaceTrack;
import serverSide.Stable.Stable;

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
     */
    public Broker(String name) {
        super(name);
        this.name = name;
        this.bstate = BrokerStates.OTE;

        this.bettingCenter = new BettingCenter();
        this.controlCenter = new ControlCenter();
        this.raceTrack = new RaceTrack();
        this.stable = new Stable();
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

        // send shutdown
        /*bettingCenter.endServer();
        controlCenter.endServer();
        raceTrack.endServer();
        stable.endServer();*/
    }
}
