package interfaces;

import auxiliary.ReturnStruct;
import auxiliary.SimulPar;
import auxiliary.TimeVector;
import serverSide.BettingCenter.BettingCenter;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Betting Center's interface.
 */
public interface BettingCenterInterface extends Remote{

    /**
     * Broker is announcing the winning chances of the horses in the current race.
     *
     *   @param horseChances List of Horses/Jockey pairs winning chances
     *   @param clk Clock
     *   @return Clock
     *   @throws RemoteException may throw during a execution of a remote method call
     */
   public ReturnStruct setHorsesWinningChances(double[] horseChances, TimeVector clk) throws RemoteException;

    /**
     * Verifying if all bets were accepted in the current race.
     *
     *   @param clk Clock
     *   @return Clock and a bolean that is true if all the bets were accepted or false if not.
     *   @throws RemoteException may throw during a execution of a remote method call
     */
   public ReturnStruct acceptedAllBets(TimeVector clk) throws RemoteException;

    /**
     * Broker is waiting for a bet placed by a Spectator and accepts it.
     *
     *   @param clk Clock
     *   @return Clock
     *   @throws RemoteException may throw during a execution of a remote method call
     */
   public ReturnStruct acceptTheBet(TimeVector clk) throws RemoteException;

    /**
     * Spectator places a bet and is waiting for the broker to accept it.
     *
     *   @param horseId Identifier of the Horse/Jockey pair that the Spectator is betting
     *   @param specId Spectator's Id
     *   @param funds Spectator's wallet
     *   @param clk Clock
     *   @return Clock and the bet Size
     *   @throws RemoteException may throw during a execution of a remote method call
     */
   public ReturnStruct placeABet(int horseId, int specId, double funds, TimeVector clk) throws RemoteException;

    /**
     * Broker is checking if any Spectator betted on any of the winning horses.
     *
     *   @param winningHorses List of winning horses
     *   @param clk Clock
     *   @return Clock and a bolean that is true if there is a winner, false if there's not.
     *   @throws RemoteException may throw during a execution of a remote method call
     */
   public ReturnStruct areThereAnyWinners(int[] winningHorses, TimeVector clk) throws RemoteException;

    /**
     * Verifying if all winners already collected their money.
     *
     *   @param clk Clock
     *   @return Clock and a bolean that is true if all the bets were honoured, false if not.
     *   @throws RemoteException may throw during a execution of a remote method call
     */
   public ReturnStruct honouredAllTheBets(TimeVector clk) throws RemoteException;

    /**
     * Broker is waiting for a Spectator to come collect their gains and is paying back the Spectator
     *
     *   @param clk Clock
     *   @return Clock
     *   @throws RemoteException may throw during a execution of a remote method call
     */
   public ReturnStruct honourTheBet(TimeVector clk) throws RemoteException;

    /**
     * Spectator is waiting for the Broker to pay him back and collects the money that he has won.
     *
     *   @param specId Spectator's Id
     *   @param funds Spectator's wallet
     *   @param clk Clock
     *   @return Clock and the earnings
     *   @throws RemoteException may throw during a execution of a remote method call
     */
   public ReturnStruct goCollectTheGains(int specId, double funds, TimeVector clk) throws RemoteException;

   //Not implemented by now
   //public void shutdown() throws RemoteException;

}
