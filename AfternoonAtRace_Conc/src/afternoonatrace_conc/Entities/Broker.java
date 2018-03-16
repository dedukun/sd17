package afternoonatrace_conc.Entities;
import afternoonatrace_conc.Main.SimulPar;
import afternoonatrace_conc.SharedRegions.*;

/**
 * Broker Thread.
 */
public class Broker extends Thread{

    /**
     * Current state of the Broker.
     */
    private States bstate;

    /**
     * Enumerate with Broker States.
     */
    public static enum States
    {
        OPENING_THE_EVENT,
        ANNOUNCING_NEXT_RACE,
        WAITING_FOR_BETS,
        SUPERVISING_THE_RACE,
        SETTLING_ACCOUNTS,
        PLAYING_HOST_AT_THE_BAR
    }

    /**
     * Broker initialization.
     */
    public Broker() {
        this.bstate = States.OPENING_THE_EVENT;
    }

    /**
     * Set new state.
     *
     * 	@param bstate
     */
    public void setState(States bstate) {
        this.bstate = bstate;
    }

    /**
     * Get the current state of the Broker
     *
     *  @return  Returns the current state
     */
    public States getBState() {
        return bstate;
    }

    /**
     * Broker life cycle.
     */
    @Override
    public void run(){
        for(int k=0; k<SimulPar.K; k++){
            ControlCenter.summonHorsesToPaddock(); //Blocked
            //unblocked by lastCheckHorses() or placeABet()
            while(BettingCenter.acceptAllBets()){
                BettingCenter.acceptTheBet(); //Blocked
            }
            //Unblocked by unblockMakeAMove()
            ControlCenter.startTheRace();//Blocked
            ControlCenter.reportResults();
            if(ControlCenter.areThereAnyWinners()){
                while(BettingCenter.honourAllTheBets())
                    //Queue Unblock
                    BettingCenter.hounourTheBet();//Blocked
            }
            ControlCenter.entertainTheGuests();
        }
    }
}
