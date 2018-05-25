package serverSide.BettingCenter;

import auxiliary.SimulPar;
import auxiliary.BrokerStates;
import auxiliary.ReturnStruct;
import auxiliary.SpectatorStates;
import auxiliary.TimeVector;
import interfaces.BettingCenterInterface;
import interfaces.GenReposInterface;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * Betting Center.<br>
 * Where all the operations related with bets are done.
 */
public class BettingCenter implements BettingCenterInterface{

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
     * Number of finished accepted bets.
     */
    private int numberFinishedBets;

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
     * Number of finished accepted winner.
     */
    private int numberFinishedWinners;

    /**
     * Reference to General Repository.
     */
    private final GenReposInterface genRepos;
    
    /**
     * Reference to Time Vector.
     */
    private TimeVector clk;

    /**
     * Betting Center initialization.
     */
    public BettingCenter(GenReposInterface genRepos){
        this.genRepos = genRepos;

        raceBets = new ArrayList<>();
        numberAcceptedBets = 0;
        numberFinishedBets = 0;
        acceptedSpectatorsBets = new boolean[SimulPar.S];

        spectatorsGains = new double[SimulPar.S];
        spectatorWaitingGains = new ArrayList<>();
        numberOfWinningBets = 0;
        numberAcceptedWinners = 0;
        numberFinishedWinners = 0;

        // sync vars
        waitForBet = true;
        waitForWinningSpectator = true;
        waitForGains = new boolean[SimulPar.S];
        clk = new TimeVector();
    }

    /**
     * Broker is announcing the winning chances of the horses in the current race.
     *
     *   @param horseChances List of Horses/Jockey pairs winning chances
     */
    @Override
    public synchronized ReturnStruct setHorsesWinningChances(double[] horseChances, TimeVector clk){
        this.clk.updateTime(clk.getTime());
        currentHorsesWinningChances = horseChances;
        return new ReturnStruct(clk);
    }

    /**
     * Verifying if all bets were accepted in the current race.
     *
     *   @return true if all the bets were accepted or false if not.
     */
    @Override
    public synchronized ReturnStruct acceptedAllBets(TimeVector clk){
        this.clk.updateTime(clk.getTime());
        if(numberFinishedBets == SimulPar.S){

            // reset vars for race
            numberOfWinningBets = 0;
            numberFinishedBets = 0;
            numberAcceptedWinners = 0;
            spectatorWaitingGains.clear();
            Arrays.fill(waitForGains, Boolean.FALSE);
            Arrays.fill(spectatorsGains, 0);

            return new ReturnStruct(clk, true);
        }
        return new ReturnStruct(clk, false);
    }

    /**
     * Broker is waiting for a bet placed by a Spectator and accepts it.
     */
   
    @Override
    public synchronized ReturnStruct acceptTheBet(TimeVector clk) throws RemoteException {
        this.clk.updateTime(clk.getTime());
        genRepos.setBrokerState(BrokerStates.WFB, clk);

        while(waitForBet){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        waitForBet = true;

        if(raceBets.size() > 0 && numberAcceptedBets != SimulPar.S){
            int lastSpectatorAccepted = raceBets.get(numberAcceptedBets).getSpectatorId();

            acceptedSpectatorsBets[lastSpectatorAccepted] = true;
            numberAcceptedBets++;

            notifyAll();
        }
        
        return new ReturnStruct(clk);
    }

    /**
     * Spectator places a bet and is waiting for the broker to accept it.
     *
     *   @param horseId Identifier of the Horse/Jockey pair that the Spectator is betting
     *   @param specId Spectator's Id
     *   @param funds Spectator's wallet
     *   @return Bet Size
     */
    @Override
    public synchronized ReturnStruct placeABet(int horseId, int specId, double funds, TimeVector clk) throws RemoteException {
        
        //((Spectators) Thread.currentThread()).setState(SpectatorsStates.PAB);
        //Mudar aqui para RMI, não usar currentThread
        
        this.clk.updateTime(clk.getTime());
        int spectatorId = specId;
        double spectatorWallet = funds;
        double betSize;

        // Chose bet size
        if(spectatorId < 3){
            betSize = spectatorWallet * spectatorsBetSizes[spectatorId];
        }
        else{ // Default bet size is 30% of the wallet
            betSize = spectatorWallet * 0.3;
        }

        raceBets.add(new Bet(spectatorId, betSize, horseId));

        genRepos.setBetS(spectatorId, horseId, clk);
        genRepos.setBetA(spectatorId, (int)betSize, clk);
        genRepos.setSpectatorMoney(spectatorId, (int) (spectatorWallet-betSize), clk);
        genRepos.setSpectatorState(spectatorId, SpectatorStates.PAB, clk);

        while(!acceptedSpectatorsBets[spectatorId]){
            waitForBet = false;
            notifyAll();
            try{
                wait();
            }catch(InterruptedException e){}
        }

        numberFinishedBets++;
        if(numberFinishedBets == SimulPar.S){
            waitForBet = false;

            notifyAll();
        }
        
        return new ReturnStruct(clk, betSize);
    }

    /**
     * Broker is checking if any Spectator betted on any of the winning horses.
     *
     *   @param winningHorses List of winning horses
     *   @return true if there is a winner, false if there's not.
     */
    @Override
    public synchronized ReturnStruct areThereAnyWinners(int[] winningHorses, TimeVector clk) throws RemoteException {
        this.clk.updateTime(clk.getTime());
        
        //((Broker) Thread.currentThread()).setState(BrokerStates.SA);
        genRepos.setBrokerState(BrokerStates.SA, clk);

        numberOfWinningHorses = winningHorses.length;

        for(int winner : winningHorses){
            for(int i = 0; i < raceBets.size(); i++){
                if(raceBets.get(i).getHorseId() == winner){
                    raceBets.get(i).betWon();
                    numberOfWinningBets++;
                }
            }
        }

        if( numberOfWinningBets == 0){
            // Reset variables for next race
            raceBets.clear();
            numberAcceptedBets = 0;
            numberFinishedWinners = 0;
            Arrays.fill(acceptedSpectatorsBets, Boolean.FALSE);

            return new ReturnStruct(clk, false);
        }
        return new ReturnStruct(clk, true);
    }

    /**
     * Verifying if all winners already collected their money.
     *
     *   @return true if all the bets were honoured, false if not.
     */
    @Override
    public synchronized ReturnStruct honouredAllTheBets(TimeVector clk) throws RemoteException {
        this.clk.updateTime(clk.getTime());
        boolean to_ret;
        if(numberFinishedWinners == numberOfWinningBets){

            // Reset variables for next race
            raceBets.clear();
            numberAcceptedBets = 0;
            numberFinishedWinners = 0;
            Arrays.fill(acceptedSpectatorsBets, Boolean.FALSE);

            to_ret = true;
        }
        to_ret = false;
        
        return new ReturnStruct(clk, to_ret);
    }

    /**
     * Broker is waiting for a Spectator to come collect their gains and is paying back the Spectator
     */
    @Override
    public synchronized ReturnStruct honourTheBet(TimeVector clk) throws RemoteException {
        this.clk.updateTime(clk.getTime());
        while(waitForWinningSpectator){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        waitForWinningSpectator = true;

        if(spectatorWaitingGains.size() > 0){
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

            notifyAll();
        }   
        return new ReturnStruct(clk);
    }

    /**
     * Spectator is waiting for the Broker to pay him back and collects the money that he has won.
     * 
     *   @param specId Spectator's Id
     *   @param funds Spectator's wallet
     *   @return Eearnings
     */
    
    @Override
    public synchronized ReturnStruct goCollectTheGains(int specId, double funds, TimeVector clk) throws RemoteException {
        //((Spectators) Thread.currentThread()).setState(SpectatorsStates.CTG);
        this.clk.updateTime(clk.getTime());
        int spectatorId = specId;
        double spectatorWallet = funds;

        spectatorWaitingGains.add(spectatorId);

        genRepos.setSpectatorState(spectatorId, SpectatorStates.CTG,clk);

        while(!waitForGains[spectatorId]){
            waitForWinningSpectator = false;
            notifyAll();
            try{
                wait();
            }catch(InterruptedException e){}
        }

        double earnings = spectatorsGains[spectatorId];

        //genRepos.setSpectatorMoney(spectatorId, (int) (spectatorWallet + earnings));

        numberFinishedWinners++;

        if(numberFinishedWinners == numberOfWinningBets){
            waitForWinningSpectator = false;

            notifyAll();
        }
        
        return new ReturnStruct(clk, earnings);
    }
    
    /**
     * Send a message to the General Reposutory telling that this server is shutting down 
     */
    public synchronized void shutdownGenRepo(){
        //Não modificado por agora
        //genRepos.endServer();
    }


    /**
     * Class representing a bet by a Spectator.
     */
    private class Bet{

        /**
         * Bet Amount.
         */
        private double amount;

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
         *   @param amount Bet's amount
         *   @param hid Horse/Jockey pair id
         */
        private Bet(int sid, double amount, int hid){
            this.sid = sid;
            this.amount = amount;
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
         * Returns bet amount.
         *
         *   @return The amount
         */
        private double getAmmount(){
            return amount;
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
            return "Spectator" + this.sid + " betted " + this.amount + " in horse" + this.hid;
        }
    }
}
