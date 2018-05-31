package clientSide.Broker;

import auxiliary.BrokerStates;

import auxiliary.SimulPar;
import auxiliary.TimeVector;
import interfaces.BettingCenterInterface;
import interfaces.ControlCenterInterface;
import interfaces.RaceTrackInterface;
import interfaces.StableInterface;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;


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
    private BettingCenterInterface bettingCenter;

    /**
     * Reference to Control Center.
     */
    private ControlCenterInterface controlCenter;

    /**
     * Reference to Race Track;
     */
    private RaceTrackInterface raceTrack;

    /**
     * Reference to Stable
     */
    private StableInterface stable;

    /**
     * Reference to Time Vector.
     */
    private TimeVector clk;

    /**
     * Broker initialization.
     *
     *   @param name Broker's name
     *   @param bc Betting Center reference
     *   @param cc Control Center reference
     *   @param rt Race Track reference
     *   @param st Stable reference
     */
    public Broker(String name, BettingCenterInterface bc, ControlCenterInterface cc, RaceTrackInterface rt, StableInterface st) {
        super(name);
        this.name = name;
        this.bstate = BrokerStates.OTE;

        this.bettingCenter = bc;
        this.controlCenter = cc;
        this.raceTrack = rt;
        this.stable = st;

        clk = new TimeVector();
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
    public void run() {

        for(int k=0; k < SimulPar.K; k++){
         try {
             double [] horsesWinningProbabilities = stable.summonHorsesToPaddock(k, clk).getRet_dou_arr();
             bettingCenter.setHorsesWinningChances(horsesWinningProbabilities,clk);
             controlCenter.summonHorsesToPaddock(clk); //Blocked
             //unblocked by lastCheckHorses() or placeABet()
             while(!bettingCenter.acceptedAllBets(clk).isRet_bool()){
                 bettingCenter.acceptTheBet(clk); //Blocked
             }
             //Unblocked by unblockMakeAMove()
             raceTrack.startTheRace(clk);
             controlCenter.startTheRace(clk);//Blocked
             int[] winnerHorses = raceTrack.getResults(clk).getRet_int_arr();
             controlCenter.reportResults(winnerHorses, clk);
             if(bettingCenter.areThereAnyWinners(winnerHorses, clk).isRet_bool()){
                 while(!bettingCenter.honouredAllTheBets(clk).isRet_bool())
                     bettingCenter.honourTheBet(clk);//Blocked
             }
             stable.entertainTheGuests(clk); // Unblock Horses
             controlCenter.entertainTheGuests(clk);
         } catch (RemoteException ex) {
             Logger.getLogger(Broker.class.getName()).log(Level.SEVERE, null, ex);
         }
        }

        // send shutdown
        /*bettingCenter.endServer();
        controlCenter.endServer();
        raceTrack.endServer();
        stable.endServer();*/
    }
}
