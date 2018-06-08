package clientSide.HorseJockey;

import auxiliary.HorseJockeyStates;
import auxiliary.TimeVector;
import interfaces.PaddockInterface;
import interfaces.ControlCenterInterface;
import interfaces.RaceTrackInterface;
import interfaces.StableInterface;
import java.rmi.RemoteException;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Horse/Jockey Entity.<br>
 * Definition of a horse/jockey pair.
 */
public class HorseJockey extends Thread{

    /**
     * Horse/Jocker pair name.
     */
    private String name;

    /**
     * Number of the race the Horse/Jockey pair is participating.
     */
    private int raceNumber;

    /**
     * Identifier of the Horse/Jockey pair.
     */
    private int hjid;

    /**
     * Current state of the Horse/Jockey pair.
     */
    private HorseJockeyStates hjstate;

    /**
     * Agility of the Horse/Jockey pair.
     */
    private int agility;

    /**
     * Reference to Control Center Interface.
     */
    private ControlCenterInterface controlCenter;

    /**
     * Reference to Paddock Interface.
     */
    private PaddockInterface paddock;

    /**
     * Reference to Race Track Interface.
     */
    private RaceTrackInterface raceTrack;

    /**
     * Reference to Stable Interface.
     */
    private StableInterface stable;

    /**
     * Reference to Time Vector.
     */
    private TimeVector clk;

    /**
     * Horse/Jockey initialization.
     *
     *   @param name Horse/Jockey's name
     *   @param raceNumber Number of the race
     *   @param hjid Horse/Jockey pair ID
     *   @param pd Paddock reference
     *   @param cc Control Center reference
     *   @param rt Race Track reference
     *   @param st Stable reference
     */
    public HorseJockey(String name, int raceNumber, int hjid, PaddockInterface pd, ControlCenterInterface cc, RaceTrackInterface rt, StableInterface st) {
        super (name);
        this.name = name;
        this.raceNumber = raceNumber;

        this.hjid = hjid;
        this.hjstate = null;
        this.agility = new Random().nextInt(6) + 2;

        this.controlCenter = cc;
        this.paddock = pd;
        this.raceTrack = rt;
        this.stable = st;

        clk = new TimeVector();
    }

    /**
     * Get the race number.
     *
     *   @return The number
     */
    public int getRaceNumber() {
        return raceNumber;
    }

    /**
     * Get Horse/Jockey pair identifier.
     *
     *   @return The identifier
     */
    public int getHJId() {
        return hjid;
    }

    /**
     * Get Horse/Jockey pair current state.
     *
     *   @return The state
     */
    public HorseJockeyStates getHJState() {
        return hjstate;
    }

    /**
     * Get Horse/Jockey pair agility.
     *
     *   @return The agility
     */
    public int getAgility() {
        return agility;
    }

    /**
     * Set Horse/Jockey pair state.
     *
     *   @param hjstate New state
     */
    public void setState(HorseJockeyStates hjstate) {
        this.hjstate = hjstate;
    }

    /**
     * Horse/Jockey life cycle.
     */
    @Override
    public void run(){
        try {
            stable.proceedToStable(hjid, raceNumber, agility, clk); //Blocked
            //unblocked by sumHorsesToPaddock()
            if(paddock.lastArrivedToPaddock(hjid, clk).isRet_bool()) //Blocked
                controlCenter.unblockProceedToPaddock(clk);
            //unblocked by lastCheckHorses()
            paddock.proceedToPaddock(hjid, agility, clk); //Blocked
            raceTrack.proceedToStartLine(hjid, clk); //Blocked
            do{
                boolean last = raceTrack.makeAMove(hjid, agility, clk).isRet_bool();
                if(last){
                    controlCenter.unblockMakeAMove(clk);
                }
            }while(!raceTrack.hasRaceFinished(hjid, clk).isRet_bool()); // Blocked
            //unblocked by startTheRace() or makeAMove()
            stable.proceedToStable(hjid, raceNumber, agility, clk);

            //send shutdown
            controlCenter.disconnect(clk);
            paddock.disconnect(clk);
            raceTrack.disconnect(clk);
            stable.disconnect(clk);
        } catch (RemoteException ex) {
             Logger.getLogger(HorseJockey.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
