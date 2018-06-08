package clientSide.Spectators;

import java.util.Random;
import interfaces.PaddockInterface;
import interfaces.ControlCenterInterface;
import interfaces.BettingCenterInterface;
import auxiliary.SpectatorStates;
import auxiliary.TimeVector;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;


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
     * Reference to Betting Center Interface.
     */
    private BettingCenterInterface bettingCenter;

    /**
     * Reference to Control Center Interface.
     */
    private ControlCenterInterface controlCenter;

    /**
     * Reference to Paddock Interface.
     */
    private PaddockInterface paddock;

    /**
     * Reference to Time Vector.
     */
    private TimeVector clk;

    /**
     * Spectators Initialization.
     *
     *   @param name Spectator's Name
     *   @param sid Spectator's Identifier
     *   @param bc Betting Center reference
     *   @param cc Control Center reference
     *   @param pd Paddock reference
     */
    public Spectators(String name, int sid, BettingCenterInterface bc, ControlCenterInterface cc, PaddockInterface pd) {
        super(name);
        this.sname = name;
        this.sid = sid;

        this.wallet = (double) new Random().nextInt(101) + 27;

        this.bettingCenter = bc;
        this.controlCenter = cc;
        this.paddock = pd;

        clk = new TimeVector();
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
        try{
            //Blocked
            while(controlCenter.waitForNextRace(sid, clk).isRet_bool()){
                //Unblocked by proceedToPaddock()
                if(paddock.lastCheckHorses(sid, clk).isRet_bool()){
                    paddock.unblockGoCheckHorses(clk);
                    controlCenter.unblockGoCheckHorses(clk);
                }
                int horseID = paddock.goCheckHorses(sid, clk).getRet_int();
                //Unblocked by proceedToStartLine()
                wallet -= bettingCenter.placeABet(horseID, sid, wallet, clk).getRet_dou();//Blocked
                //Unblocked by acceptTheBet()
                controlCenter.goWatchTheRace(sid, clk);//Blocked
                //Unblocked by reportResults()
                if(controlCenter.haveIWon(horseID, clk).isRet_bool())
                    wallet += bettingCenter.goCollectTheGains(sid,wallet, clk).getRet_dou();//Blocked
                    //Unblocked by honourTheBets()
            }
            controlCenter.relaxABit(sid, clk);

            // send shutdown
            bettingCenter.disconnect(clk);
            controlCenter.disconnect(clk);
            paddock.disconnect(clk);
        } catch (RemoteException ex) {
             Logger.getLogger(Spectators.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
}
