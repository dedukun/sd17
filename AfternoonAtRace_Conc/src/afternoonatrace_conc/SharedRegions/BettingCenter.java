package afternoonatrace_conc.SharedRegions;
import afternoonatrace_conc.Entities.*;
import afternoonatrace_conc.Main.SimulPar;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 */
public class BettingCenter {

    /**
     * Winning chances of the horses in the current race.
     */
    private int[] currentHorsesWinningChances;

    /**
     * Broker is waiting for a bet - synchronization point.
     */
    private boolean waitForBet;

    /**
     * Array with boolean of accepted Spectator's bets - synchronization point.
     */
    private boolean[] acceptedSpectatorsBets;

    /**
     * List of all bets of the current race.
     */
    private ArrayList<Bet> raceBets;

    /**
     * Number of accepted bets.
     */
    private int numberAcceptedBets;

    /**
     * List of Spectators waiting for Broker give gains - synchronization point.
     */
    private boolean[] waitForGains;

    /**
     * Broker is waiting for a Spectator to collect the gains - synchronization point.
     */
    private boolean waitForWinningSpectator;

    /**
     * List of the gains the winning Spectators earned.
     */
    private int[] spectatorsGains;

    /**
     * List of spectators waiting for collecting gains
     */
    private ArrayList<Integer> spectatorWaitingGains;

    /**
     * Total number of winners.
     */
    private int numberOfWinners;

    /**
     * Number of Spectators that already collected their gains.
     */
    private int numberOfCollectedGains;

    /**
     * Number of Accepted winning bets.
     */
    private int numberAcceptedWinners;

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

        raceBets = new ArrayList<>();
        numberAcceptedBets = 0;
        acceptedSpectatorsBets = new boolean[SimulPar.S];

        spectatorsGains = new int[SimulPar.S];
        spectatorWaitingGains = new ArrayList<>();
        numberOfWinners = 0;
        numberOfCollectedGains = 0;
        numberAcceptedWinners = 0;

        // sync vars
        waitForBet = true;
        waitForWinningSpectator = true;
        waitForGains = new boolean[SimulPar.S];
    }

    /**
     * Broker is announcing the winning chances of the horses in the current race.
     *
     *   @param horseChances List of Horses/Jockey pairs winning chances
     */
    public synchronized void setHorsesWinningChances(int[] horseChances){
        currentHorsesWinningChances = horseChances;
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

        int lastSpectatorAccepted = raceBets.get(numberAcceptedBets).getSpectatorId();
        System.out.println(((Broker) Thread.currentThread()).getName() + " accepted a bet");
        System.out.println(raceBets.get(numberAcceptedBets).toString());

        acceptedSpectatorsBets[lastSpectatorAccepted] = true;
        numberAcceptedBets++;
        waitForBet = true;

        notifyAll();
    }

    /**
     * Spectator places a bet and is waiting for the broker to accept it.
     *
     *   @param horseId Identifier of the Horse/Jockey pair that the Spectator is betting
     */
    public synchronized void placeABet(int horseId){
        ((Spectators) Thread.currentThread()).setState(Spectators.States.PLACING_A_BET);

        int spectatorId = ((Spectators) Thread.currentThread()).getSID();
        int spectatorWallet = ((Spectators) Thread.currentThread()).getFunds();
        int money = ThreadLocalRandom.current().nextInt(1,spectatorWallet);

        System.out.println(Thread.currentThread().getName() + " is placing a bet in Horse " + horseId + " with ammount " + money + " (Wallet:" + spectatorWallet + ")");

        raceBets.add(new Bet(spectatorId, money, horseId));

        while(!acceptedSpectatorsBets[spectatorId]){
            waitForBet = false;
            notifyAll();
            try{
                wait();
            }catch(InterruptedException e){}
        }

        System.out.println(((Spectators) Thread.currentThread()).getName() + " finished placing a bet");
    }

    /**
     * Broker is checking if any Spectator betted in the winning horses.
     *
     *   @param winningHorses List of winning horses
     *   @return
     */
    public synchronized boolean areThereAnyWinners(int[] winningHorses){
        ((Broker) Thread.currentThread()).setState(Broker.States.SETTLING_ACCOUNTS);

        boolean isThereAnyWinner = false;

        for(int winner : winningHorses){
            for(int i = 0; i < raceBets.size(); i++){
                if(raceBets.get(i).getHorseId() == winner){
                    raceBets.get(i).betWon();
                    numberOfWinners++;
                    isThereAnyWinner = true;
                }
            }
        }
        System.out.println(Thread.currentThread().getName() + " says that there are winners -> " + numberOfWinners);
        return isThereAnyWinner;
    }

    /**
     * Verifying if all winners already collected their money.
     *
     *   @return
     */
    public synchronized boolean honouredAllTheBets(){
        if(numberAcceptedWinners == numberOfWinners){

            System.out.println("ALL BETS HONOURED");
            // Reset variables for next race
            raceBets.clear();
            numberAcceptedBets = 0;
            Arrays.fill(acceptedSpectatorsBets, Boolean.FALSE);

            numberOfWinners = 0;
            numberOfCollectedGains = 0;
            Arrays.fill(waitForGains, Boolean.FALSE);

            return true;
        }
        return false;
    }

    /**
     * Broker is honouring the bets.
     */
    public synchronized void honourTheBet(){

        System.out.println(Thread.currentThread().getName() + "  waiting to pay a Spectator");

        while(waitForWinningSpectator){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        int spectatorId = spectatorWaitingGains.get(numberOfCollectedGains);

        for(Bet bet: raceBets){
            if(bet.getSpectatorId() == spectatorId){
                // Check if this spectator is a winner
                if(bet.isWinner()){
                    numberAcceptedWinners++;
                    spectatorsGains[bet.getSpectatorId()] = bet.getAmmount() * 2;
                }

                break;
            }
        }

        numberOfCollectedGains++;
        waitForGains[spectatorId] = true;
        waitForWinningSpectator = true;

        notifyAll();

        System.out.println(Thread.currentThread().getName() + " payed a Spectator");
    }

    /**
     * Spectator collects the money that he has won.
     *
     *   @return Winnings ammount
     */
    public synchronized int goCollectTheGains(){
        ((Spectators) Thread.currentThread()).setState(Spectators.States.COLLECTING_THE_GAINS);

        System.out.println(((Spectators) Thread.currentThread()).getName() + " is collecting the gains");

        int spectatorId = ((Spectators) Thread.currentThread()).getSID();

        spectatorWaitingGains.add(spectatorId);

        while(!waitForGains[spectatorId]){
            waitForWinningSpectator = false;
            notifyAll();
            try{
                wait();
            }catch(InterruptedException e){}
        }

        int earnings = spectatorsGains[spectatorId];
        System.out.println(((Spectators) Thread.currentThread()).getName() + " finished collecting the gains -> " + earnings);

        return earnings;
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
         * This is a winning bet.
         */
        private boolean isWinner;

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
            isWinner = false;
        }

        /**
         * Get Spectator's identifier.
         *
         *   @return The id
         */
        private int getSpectatorId(){
            return sid;
        }

        /**
         * Get Horse/Jockey pair that the Spectator's betted on.
         *
         *   @return The id
         */
        private int getHorseId(){
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

        /**
         * Checks this bet as a winner one.
         */
        private void betWon(){
            isWinner = true;
        }

        /**
         * Check if this bet is a winner.
         */
        private boolean isWinner(){
            return isWinner;
        }

        @Override
        public String toString() {
            return "Spectator" + this.sid + " betted " + this.ammount + " in horse" + this.hid;
        }
    }
}
