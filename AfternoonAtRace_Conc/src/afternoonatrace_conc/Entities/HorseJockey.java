package afternoonatrace_conc.Entities;
import afternoonatrace_conc.SharedRegions.*;

/**
 * Horse/Jockey Thread.
 */
public class HorseJockey extends Thread{

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
     * Max speed of the Horse/Jockey pair.
     */
    private double maxSpeed;

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
     * Horse/Jockey initialization.
     *
     * @param raceNumber Number of the race
     * @param hjid Horse/Jockey pair ID
     */
    public HorseJockey(int raceNumber, int hjid) {
        this.raceNumber = raceNumber;
        this.hjid = hjid;
        this.hjstate = null;
        this.maxSpeed = 1 + 5 * Math.random(); // Random speed
    }

    /**
     * Get the race number.
     *
     *   @return Race Number
     */
    public int getRaceNumber() {
        return raceNumber;
    }

    /**
     * Get Horse/Jockey pair identifier.
     *
     *   @return
     */
    public int getHJId() {
        return hjid;
    }

    /**
     * Get Horse/Jockey pair current state.
     *
     *   @return
     */
    public States getHJState() {
        return hjstate;
    }

    /**
     * Get Horse/Jockey pair max speed.
     *
     *   @return
     */
    public double getMaxSpeed() {
        return maxSpeed;
    }

    /**
     * Set Horse/Jockey pair state.
     *
     *   @param hjstate
     */
    public void setState(States hjstate) {
        this.hjstate = hjstate;
    }

    /**
     * Horse/Jockey life cycle.
     */
    @Override
    public void run(){
        Stable.proceedToStable();
        //unblocked by sumHorsesToPaddock()
        if(Stable.lastProoceedToPaddock()) //Blocked
            ControlCenter.unblockProceedToPaddock();
        //unblocked by lastCheckHorses()
        Paddock.proceedToPaddock(); //Blocked
        if(Paddock.lastProoceedToStartLine())
            Paddock.unblockProoceedToStartLine();
        RaceTrack.proceedToStartLine(); //Blocked
        do{
            //unblocked by startTheRace() or makeAMove()
            RaceTrack.unblockMakeAMove();
            if(!RaceTrack.hasFinishLineBeenCrossed()){
                RaceTrack.makeAMove();
                if(RaceTrack.lastMakeAMove())
                    RaceTrack.unblockMakeAMove(); //Blocked
            }
        }while(!RaceTrack.hasRaceFinished());
        Stable.proceedToStable();
    }
}
