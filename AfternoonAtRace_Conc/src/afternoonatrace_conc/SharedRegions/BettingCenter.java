package afternoonatrace_conc.SharedRegions;
import afternoonatrace_conc.Entities.*;
import afternoonatrace_conc.Main.SimulPar;
import java.util.ArrayList;

/**
 *
 */
public class BettingCenter {

    /**
     * Broker is waiting for a bet - synchronization point
     */
    private boolean waitForBet;

    /**
     * Spectator is waiting for Broker to accept the bet - synchronization point
     */
    private boolean waitForFinishedBet;

    /**
     * List of all bets of the current race.
     */
    private ArrayList<Bet> bets;

    /**
     * Name of the Spectator's that had is bet accepted.
     */
    private int lastSpectatorAccepted;

    /**
     * Number of accepted bets.
     */
    private int numberAcceptedBets;

    /**
     * Reference to General Repository
     */
    private final GeneralRepository genRepos;

    /**
     * Betting Center initialization
     *
     *  @param genRepos Reference to General Repository
     */
    public BettingCenter(GeneralRepository genRepos){
        this.genRepos = genRepos;

        bets = new ArrayList<>();
        lastSpectatorAccepted = -1;
        numberAcceptedBets = 0;

        waitForBet = true;
        waitForFinishedBet = true;
    }

    /**
     * Verifying if all bets were accepted in the current race.
     *
     *   @return
     */
    public synchronized boolean acceptedAllBets(){
        return numberAcceptedBets == SimulPar.S;
    }

    /**
     * Broker is accepting a bet from a Spectator.
     */
    public synchronized void acceptTheBet(){
        ((Broker) Thread.currentThread()).setState(Broker.States.WAITING_FOR_BETS);

        System.out.println(((Broker) Thread.currentThread()).getName() + " is waiting for a bet");

        while(waitForBet){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        lastSpectatorAccepted = bets.get(numberAcceptedBets).getSpectatorID();

        System.out.println(bets.get(numberAcceptedBets).toString());

        numberAcceptedBets++;
        waitForFinishedBet = false;
        waitForBet = true;

        notifyAll();

        System.out.println(((Broker) Thread.currentThread()).getName() + " accepted a bet");
    }

    /**
     * Spectator places a bet and is waiting for the broker to accept it.
     *
     *   @param money Ammount of the bet being made
     *   @param horseID Identifier of the Horse/Jockey pair that the Spectator is betting
     */
    public synchronized void placeABet(int money, int horseID){
        ((Spectators) Thread.currentThread()).setState(Spectators.States.PLACING_A_BET);

        System.out.println(((Spectators) Thread.currentThread()).getName() + " is placing a bet in Horse " + horseID + " with ammount " + money);

        int spectatorID = ((Spectators) Thread.currentThread()).getSID();

        bets.add(new Bet(spectatorID, money, horseID));

        while(waitForFinishedBet || spectatorID != lastSpectatorAccepted){
            waitForBet = false;
            notifyAll();
            try{
                wait();
            }catch(InterruptedException e){}
        }

        waitForFinishedBet = true;

        System.out.println(((Spectators) Thread.currentThread()).getName() + " finished placing a bet");
    }

    /**
     * Verifying if all winners already collected their money.
     *
     *   @return
     */
    public synchronized boolean honouredAllTheBets(){
        if(true){
            // Reset variables for next race
            bets.clear();
            lastSpectatorAccepted = -1;
            numberAcceptedBets = 0;

            return true;
        }
        return false;
    }

    public synchronized void hounourTheBet(){

    }

    /**
     * Spectator collects the money that he has won.
     *
     *   @return Winnings ammount
     */
    public synchronized int goCollectTheGains(){
        ((Spectators) Thread.currentThread()).setState(Spectators.States.COLLECTING_THE_GAINS);

        System.out.println(((Spectators) Thread.currentThread()).getName() + " is collecting the gains");

        int spectatorID = ((Spectators) Thread.currentThread()).getSID();

        while(waitForFinishedBet || spectatorID != lastSpectatorAccepted){
            waitForBet = false;
            notifyAll();
            try{
                wait();
            }catch(InterruptedException e){}
        }

        waitForFinishedBet = true;

        System.out.println(((Spectators) Thread.currentThread()).getName() + " fineshed collecting the gains");

        return 1;
    }


    /**
     * Class representing a bet by a Spectator.
     */
    private class Bet{

        /**
         * Bet Ammount.
         */
        private int ammount;

        /**
         * Horse/Jockey pair identifier.
         */
        private int hid;

        /**
         * Spectator's id.
         */
        private int sid;

        /**
         * Bet initialization.
         *
         *   @param sid Spectator's id
         *   @param ammount Bet's ammount
         *   @param hid Horse/Jockey pair id
         */
        private Bet(int sid, int ammount, int hid){
            this.sid = sid;
            this.ammount = ammount;
            this.hid = hid;
        }

        /**
         * Get Spectator's identifier.
         *
         *   @return The id
         */
        private int getSpectatorID(){
            return sid;
        }

        /**
         * Get Horse/Jockey pair that the Spectator's betted on.
         *
         *   @return The id
         */
        private int getHorseID(){
            return hid;
        }

        /**
         * Returns bet ammount.
         *
         *   @return The ammount
         */
        private int getAmmount(){
            return ammount;
        }

        @Override
        public String toString() {
            return "Spectator" + this.sid + " betted " + this.ammount + " in horse" + this.hid;
        }
    }
}
