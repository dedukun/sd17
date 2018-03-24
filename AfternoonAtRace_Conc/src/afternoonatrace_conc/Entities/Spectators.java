package afternoonatrace_conc.Entities;
import afternoonatrace_conc.SharedRegions.*;
import java.util.Random;

/**
 * Spectators Thread
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
    private States sstate;

    /**
     * Spectator's current funds.
     */
    private double wallet;

    /**
     * Enumerate with the Spectator States.
     */
    public static enum States
    {
        WAITING_FOR_A_RACE_TO_START,
        APPRAISING_THE_HORSES,
        PLACING_A_BET,
        WATCHING_A_RACE,
        COLLECTING_THE_GAINS,
        CELEBRATING
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
     * Reference to Paddock.
     */
    private Paddock paddock;

    /**
     * Spectators Initialization.
     *
     *   @param name Spectator's Name
     *   @param sid Spectator's Identifier
     *   @param bettingCenter Reference to Betting Center
     *   @param controlCenter Reference to Control Center
     *   @param paddock Reference to Paddock
     */
    public Spectators(String name, int sid, BettingCenter bettingCenter, ControlCenter controlCenter, Paddock paddock) {
        super(name);
        this.sname = name;
        this.sid = sid;

        this.wallet = (double) new Random().nextInt(101) + 27;

        this.paddock = paddock;
        this.bettingCenter = bettingCenter;
        this.controlCenter = controlCenter;
    }

    /**
     * Get Spectator's current state.
     *
     *   @return The current state
     */
    public States getSState() {
        return sstate;
    }

    /**
     * Set Spectator's state.
     *
     *   @param sstate The new state
     */
    public void setState(States sstate) {
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
        while(controlCenter.waitForNextRace()){
            //Unblocked by proceedToPaddock()
            if(paddock.lastCheckHorses()){
                paddock.unblockGoCheckHorses();
                controlCenter.unblockGoCheckHorses();
            }
            int horseID = paddock.goCheckHorses();
            //Unblocked by proceedToStartLine()
            bettingCenter.placeABet(horseID);//Blocked
            //Unblocked by acceptTheBet()
            controlCenter.goWatchTheRace();//Blocked
            //Unblocked by reportResults()
            if(controlCenter.haveIWon(horseID))
                bettingCenter.goCollectTheGains();//Blocked
            //Unblocked by honourTheBets()
        }
        controlCenter.relaxABit();
    }
}
