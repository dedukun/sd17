package Main;

import Auxiliar.HorseJockeyStates;

import Stubs.ControlCenterStub;
import Stubs.PaddockStub;
import Stubs.RaceTrackStub;
import Stubs.StableStub;

import java.util.Random;

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
     * Reference to Control Center.
     */
    private ControlCenterStub controlCenter;

    /**
     * Reference to Paddock.
     */
    private PaddockStub paddock;

    /**
     * Reference to Race Track;
     */
    private RaceTrackStub raceTrack;

    /**
     * Reference to Stable
     */
    private StableStub stable;

    /**
     * Horse/Jockey initialization.
     *
     *   @param name Horse/Jockey's name
     *   @param raceNumber Number of the race
     *   @param hjid Horse/Jockey pair ID
     */
    public HorseJockey(String name, int raceNumber, int hjid) {
        super (name);
        this.name = name;
        this.raceNumber = raceNumber;

        this.hjid = hjid;
        this.hjstate = null;
        this.agility = new Random().nextInt(6) + 2;

        this.controlCenter = new ControlCenterStub();
        this.paddock = new PaddockStub();
        this.raceTrack = new RaceTrackStub();
        this.stable = new StableStub();
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
        stable.proceedToStable(hjid, raceNumber, agility); //Blocked
        //unblocked by sumHorsesToPaddock()
        if(paddock.lastArrivedToPaddock(hjid)) //Blocked
            controlCenter.unblockProceedToPaddock();
        //unblocked by lastCheckHorses()
        paddock.proceedToPaddock(hjid, agility); //Blocked
        raceTrack.proceedToStartLine(hjid); //Blocked
        do{
            boolean last = raceTrack.makeAMove(hjid, agility);
            if(last){
                controlCenter.unblockMakeAMove();
            }
        }while(!raceTrack.hasRaceFinished(hjid)); // Blocked
        //unblocked by startTheRace() or makeAMove()
        stable.proceedToStable(hjid, raceNumber, agility);

        //send shutdown
        controlCenter.endServer();
        paddock.endServer();
        raceTrack.endServer();
        stable.endServer();
    }
}
