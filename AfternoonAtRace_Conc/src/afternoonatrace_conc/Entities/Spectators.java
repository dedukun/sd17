package afternoonatrace_conc.Entities;
import afternoonatrace_conc.SharedRegions.*;

/**
 * Spectators Thread
 */
public class Spectators extends Thread{

    /**
     * Spectator's name.
     */
    private String sname;

    /**
     * Current state of the Spectator.
     */
    private States sstate;

    /**
     * Spectator's current funds.
     */
    private double money;

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
     * Spectators Initialization.
     *
     *   @param sname Spectator Name
     */
    public Spectators(String sname) {
        this.sname = sname;
        this.money = 1 + 100 * Math.random();
    }

    /**
     * Get Spectator's current state.
     *
     *   @return
     */
    public States getSState() {
        return sstate;
    }

    /**
     * Set Spectator's new state.
     *
     *   @param
     */
    public void setState(States sstate) {
        this.sstate = sstate;
    }

    /**
     * Get Spectator's name.
     *
     *   @return
     */
    public String getSName() {
        return sname;
    }

    /**
     * Get Spectator current funds.
     *
     *   @return
     */
    public double getMoney() {
        return money;
    }

    /**
     *  Update Spectator's funds.
     *
     *   @param money Transaction amount
     */
    public void setMoney(double money) {
        this.money += money;
    }

    /**
     * Spectators life cycle
     */
    @Override
    public void run(){
        //Blocked
        while(ControlCenter.waitForNextRace()){
            //Unblocked by proceedToPaddock()
            if(ControlCenter.lastCheckHorses())
                Paddock.unblockGoCheckHorses();
            Paddock.goCheckHorses();
            //Unblocked by proceedToStartLine()
            BettingCenter.placeABet();//Blocked
            //Unblocked by acceptTheBet()
            ControlCenter.goWatchTheRace();//Blocked
            //Unblocked by reportResults()
            if(ControlCenter.haveIWon())
                BettingCenter.goCollectTheGains();//Blocked
            //Unblocked by honourTheBets()
        }
        ControlCenter.relaxABit();
    }
}
