package afternoonatrace_conc.Entities;
import afternoonatrace_conc.SharedRegions.*;
import java.util.Random;

/**
 * Horse/Jockey Thread.
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
    private States hjstate;

    /**
     * Agility of the Horse/Jockey pair.
     */
    private int agility;

    /**
     * Enumerate with Horse/Jockey States.
     */
    public static enum States
    {
        AT_THE_STABLE,
        AT_THE_PADDOCK,
        AT_THE_START_LINE,
        RUNNING,
        AT_THE_FINNISH_LINE
    }

    /**
     * Reference to Control Center.
     */
    private ControlCenter controlCenter;

    /**
     * Reference to Paddock.
     */
    private Paddock paddock;

    /**
     * Reference to Race Track;
     */
    private RaceTrack raceTrack;

    /**
     * Reference to Stable
     */
    private Stable stable;

    /**
     * Horse/Jockey initialization.
     *
     *   @param name Horse/Jockey's name
     *   @param raceNumber Number of the race
     *   @param hjid Horse/Jockey pair ID
     *   @param controlCenter Reference to Control Center
     *   @param paddock Reference to Paddock
     *   @param raceTrack Reference to Race Track
     *   @param stable Reference to Stable
     */
    public HorseJockey(String name, int raceNumber, int hjid, ControlCenter controlCenter, Paddock paddock, RaceTrack raceTrack, Stable stable) {
        super (name);
        this.name = name;
        this.raceNumber = raceNumber;

        this.hjid = hjid;
        this.hjstate = null;
        this.agility = new Random().nextInt(6) + 2;

        this.controlCenter = controlCenter;
        this.paddock = paddock;
        this.raceTrack = raceTrack;
        this.stable = stable;
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
    public States getHJState() {
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
    public void setState(States hjstate) {
        this.hjstate = hjstate;
    }

    /**
     * Horse/Jockey life cycle.
     */
    @Override
    public void run(){
        stable.proceedToStable(); //Blocked
        //unblocked by sumHorsesToPaddock()
        if(paddock.lastArrivedToPaddock()) //Blocked
            controlCenter.unblockProceedToPaddock();
        //unblocked by lastCheckHorses()
        paddock.proceedToPaddock(); //Blocked
        paddock.unblockProceedToStartLine();
        raceTrack.proceedToStartLine(); //Blocked
        do{
            boolean last = raceTrack.makeAMove();
            if(last){
                controlCenter.unblockMakeAMove();
            }
        }while(!raceTrack.hasRaceFinished()); // Blocked
        //unblocked by startTheRace() or makeAMove()
        stable.proceedToStable();
    }
}
