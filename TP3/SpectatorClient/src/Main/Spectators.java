package Main;

import java.util.Random;
import Stubs.BettingCenterStub;
import Stubs.ControlCenterStub;
import Stubs.PaddockStub;
import Auxiliar.SpectatorStates;

/**
 * Spectators Thread.<br>
 * Definition of a spectator.
 */
public class Spectators extends Thread{

    /**
     * Spectator's name.
     */
    private String sname;

    /**
     * Spectator's identifier.
     */
    private int sid;

    /**
     * Current state of the Spectator.
     */
    private SpectatorStates sstate;

    /**
     * Spectator's current funds.
     */
    private double wallet;

    /**
     * Reference to Betting Center.
     */
    private BettingCenterStub bettingCenter;

    /**
     * Reference to Control Center.
     */
    private ControlCenterStub controlCenter;

    /**
     * Reference to Paddock.
     */
    private PaddockStub paddock;

    /**
     * Spectators Initialization.
     *
     *   @param name Spectator's Name
     *   @param sid Spectator's Identifier
     */
    public Spectators(String name, int sid) {
        super(name);
        this.sname = name;
        this.sid = sid;

        this.wallet = (double) new Random().nextInt(101) + 27;

        this.bettingCenter = new BettingCenterStub();
        this.controlCenter = new ControlCenterStub();
        this.paddock = new PaddockStub();
    }

    /**
     * Get Spectator's current state.
     *
     *   @return The current state
     */
    public SpectatorStates getSState() {
        return sstate;
    }

    /**
     * Set Spectator's state.
     *
     *   @param sstate The new state
     */
    public void setState(SpectatorStates sstate) {
        this.sstate = sstate;
    }

    /**
     * Get Spectator's name.
     *
     *   @return The name
     */
    public String getSName() {
        return sname;
    }

    /**
     * Get Spectator's identifier.
     *
     *   @return The id
     */
    public int getSID() {
        return sid;
    }


    /**
     * Get Spectator current funds.
     *
     *   @return The funds
     */
    public double getFunds() {
        return wallet;
    }

    /**
     * Updates the Spectator's funds.
     *
     *   @param money The transaction amount
     */
    public void setTransaction(double money) {
        this.wallet += money;
    }

    /**
     * Spectators life cycle
     */
    @Override
    public void run(){
        //Blocked
        while(controlCenter.waitForNextRace(sid)){
            //Unblocked by proceedToPaddock()
            if(paddock.lastCheckHorses(sid)){
                paddock.unblockGoCheckHorses();
                controlCenter.unblockGoCheckHorses();
            }
            int horseID = paddock.goCheckHorses(sid);
            //Unblocked by proceedToStartLine()
            wallet -= bettingCenter.placeABet(horseID,sid,wallet);//Blocked
            //Unblocked by acceptTheBet()
            controlCenter.goWatchTheRace(sid);//Blocked
            //Unblocked by reportResults()
            if(controlCenter.haveIWon(horseID))
                wallet += bettingCenter.goCollectTheGains(sid,wallet);//Blocked
                //Unblocked by honourTheBets()
        }
        controlCenter.relaxABit(sid);

        // send shutdown
        bettingCenter.endServer();
        controlCenter.endServer();
        paddock.endServer();
    }
}
