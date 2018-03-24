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
     * List of Spectators bet size.
     */
    private final double[] spectatorsBetSizes = { 0.5, 0.25, 0.25};

    /**
     * Winning chances of the horses in the current race.
     */
    private double[] currentHorsesWinningChances;

    /**
     * Number of winning horses;
     */
    private int numberOfWinningHorses;

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
    private double[] spectatorsGains;

    /**
     * List of spectators waiting for collecting gains
     */
    private ArrayList<Integer> spectatorWaitingGains;

    /**
     * Total number of winners.
     */
    private int numberOfWinningBets;

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

        spectatorsGains = new double[SimulPar.S];
        spectatorWaitingGains = new ArrayList<>();
        numberOfWinningBets = 0;
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
    public synchronized void setHorsesWinningChances(double[] horseChances){
        currentHorsesWinningChances = horseChances;
    }

    /**
     * Verifying if all bets were accepted in the current race.
     *
     *   @return
     */
    public synchronized boolean acceptedAllBets(){
        if(numberAcceptedBets == SimulPar.S){
            // reset vars for race
            numberOfWinningBets = 0;
            numberAcceptedWinners = 0;
            spectatorWaitingGains.clear();
            Arrays.fill(waitForGains, Boolean.FALSE);
            Arrays.fill(spectatorsGains, 0);

            return true;
        }
        return false;
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
        System.out.println("Broker accepted -> " + raceBets.get(numberAcceptedBets).toString());

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
        double spectatorWallet = ((Spectators) Thread.currentThread()).getFunds();
        double betSize;

        // Chose bet size
        if(spectatorId < 3){
            betSize = spectatorWallet * spectatorsBetSizes[spectatorId];
        }
        else{ // Default bet size is 30% of the wallet
            betSize = spectatorWallet * 0.3;
        }

        ((Spectators) Thread.currentThread()).setTransaction(-betSize);

        System.out.println(Thread.currentThread().getName() + " is placing a bet in Horse " + horseId + " with ammount " + betSize + " (Wallet:" + spectatorWallet + ")");

        raceBets.add(new Bet(spectatorId, betSize, horseId));

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


        numberOfWinningHorses = winningHorses.length;

        for(int winner : winningHorses){
            for(int i = 0; i < raceBets.size(); i++){
                if(raceBets.get(i).getHorseId() == winner){
                    raceBets.get(i).betWon();
                    numberOfWinningBets++;
                }
            }
        }
        System.out.println(Thread.currentThread().getName() + " says that there are winners -> " + numberOfWinningBets);
        return numberOfWinningBets != 0;
    }

    /**
     * Verifying if all winners already collected their money.
     *
     *   @return
     */
    public synchronized boolean honouredAllTheBets(){
        if(numberAcceptedWinners == numberOfWinningBets){
            System.out.println("ALL BETS HONOURED");

            // Reset variables for next race
            raceBets.clear();
            numberAcceptedBets = 0;
            Arrays.fill(acceptedSpectatorsBets, Boolean.FALSE);

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

        // Get Spectator in queue
        int spectatorId = spectatorWaitingGains.remove(0);

        for(Bet bet: raceBets){
            if(bet.getSpectatorId() == spectatorId){
                // Check if this spectator is a winner
                if(bet.isWinner()){
                    numberAcceptedWinners++;
                    spectatorsGains[bet.getSpectatorId()] = bet.getAmmount() / (currentHorsesWinningChances[bet.getHorseId()] * numberOfWinningHorses) ;
                    System.out.println("Payed Spectator -> " + spectatorId + " (" +  spectatorsGains[bet.getSpectatorId()] + ")");
                }

                break;
            }
        }

        waitForGains[spectatorId] = true;
        waitForWinningSpectator = true;

        notifyAll();

        System.out.println(Thread.currentThread().getName() + " payed a Spectator");
    }

    /**
     * Spectator collects the money that he has won.
     */
    public synchronized void goCollectTheGains(){
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

        double earnings = spectatorsGains[spectatorId];

        System.out.println(((Spectators) Thread.currentThread()).getName() + " finished collecting the gains -> " + earnings);

        ((Spectators) Thread.currentThread()).setTransaction(earnings);
    }

    /**
     * Class representing a bet by a Spectator.
     */
    private class Bet{

        /**
         * Bet Ammount.
         */
        private double ammount;

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
        private Bet(int sid, double ammount, int hid){
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
        private double getAmmount(){
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
