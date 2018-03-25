package afternoonatrace_conc.SharedRegions;
import afternoonatrace_conc.Entities.*;
import afternoonatrace_conc.Main.SimulPar;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * Betting Center.<br>
 * Where all the operations related with bets are done.
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
     *   @return true if all the bets were accepted or false if not.
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
        ((Broker) Thread.currentThread()).setState(Broker.States.WFB);

        while(waitForBet){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        int lastSpectatorAccepted = raceBets.get(numberAcceptedBets).getSpectatorId();

        acceptedSpectatorsBets[lastSpectatorAccepted] = true;
        numberAcceptedBets++;
        waitForBet = true;

        genRepos.setBrokerState(Broker.States.WFB);

        notifyAll();
    }

    /**
     * Spectator places a bet and is waiting for the broker to accept it.
     *
     *   @param horseId Identifier of the Horse/Jockey pair that the Spectator is betting
     */
    public synchronized void placeABet(int horseId){
        ((Spectators) Thread.currentThread()).setState(Spectators.States.PAB);

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

        raceBets.add(new Bet(spectatorId, betSize, horseId));

        while(!acceptedSpectatorsBets[spectatorId]){
            waitForBet = false;
            notifyAll();
            try{
                wait();
            }catch(InterruptedException e){}
        }

        genRepos.setSpectatorState(spectatorId, Spectators.States.PAB);
        genRepos.setBetS(spectatorId, horseId);
        genRepos.setBetA(spectatorId, (int)betSize);
        genRepos.setSpectatorMoney(spectatorId, ((Spectators) Thread.currentThread()).getFunds());
    }

    /**
     * Broker is checking if any Spectator betted in the winning horses.
     *
     *   @param winningHorses List of winning horses
     *   @return true if there is a winner, false if there's not.
     */
    public synchronized boolean areThereAnyWinners(int[] winningHorses){
        ((Broker) Thread.currentThread()).setState(Broker.States.SA);

        numberOfWinningHorses = winningHorses.length;

        for(int winner : winningHorses){
            for(int i = 0; i < raceBets.size(); i++){
                if(raceBets.get(i).getHorseId() == winner){
                    raceBets.get(i).betWon();
                    numberOfWinningBets++;
                }
            }
        }

        genRepos.setBrokerState(Broker.States.SA);
        if( numberOfWinningBets == 0){
            // Reset variables for next race
            raceBets.clear();
            numberAcceptedBets = 0;
            Arrays.fill(acceptedSpectatorsBets, Boolean.FALSE);

            return false;
        }
        return true;
    }

    /**
     * Verifying if all winners already collected their money.
     *
     *   @return true if all the bets were honoured, false if not.
     */
    public synchronized boolean honouredAllTheBets(){
        if(numberAcceptedWinners == numberOfWinningBets){

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
                }

                break;
            }
        }

        waitForGains[spectatorId] = true;
        waitForWinningSpectator = true;

        notifyAll();
    }

    /**
     * Spectator collects the money that he has won.
     */
    public synchronized void goCollectTheGains(){
        ((Spectators) Thread.currentThread()).setState(Spectators.States.CTG);

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

        ((Spectators) Thread.currentThread()).setTransaction(earnings);

        genRepos.setSpectatorState(spectatorId, Spectators.States.CTG);
        genRepos.setSpectatorMoney(spectatorId, ((Spectators) Thread.currentThread()).getFunds());
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
