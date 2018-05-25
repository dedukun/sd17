package interfaces;

import auxiliary.SimulPar;
import auxiliary.TimeVector;
import serverSide.BettingCenter.BettingCenter;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Betting Center's interface
 */
public interface BettingCenterInterface extends Remote{

   public TimeVector setHorsesWinningChances(double[] horseChances, TimeVector clk) throws RemoteException;

   public TimeVector acceptedAllBets(TimeVector clk) throws RemoteException;

   public TimeVector acceptTheBet(TimeVector clk) throws RemoteException;

   public TimeVector placeABet(int horseId, int specId, double funds, TimeVector clk) throws RemoteException;

   public TimeVector areThereAnyWinners(int[] winningHorses, TimeVector clk) throws RemoteException;

   public TimeVector honouredAllTheBets(TimeVector clk) throws RemoteException;

   public TimeVector honourTheBet(TimeVector clk) throws RemoteException;

   public TimeVector goCollectTheGains(int specId, double funds, TimeVector clk) throws RemoteException;

   //Not implemented by now
   public void shutdown() throws RemoteException;

}
