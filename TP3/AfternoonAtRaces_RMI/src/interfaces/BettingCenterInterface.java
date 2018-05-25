package interfaces;

import auxiliary.ReturnStruct;
import auxiliary.SimulPar;
import auxiliary.TimeVector;
import serverSide.BettingCenter.BettingCenter;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Betting Center's interface
 */
public interface BettingCenterInterface extends Remote{

   public ReturnStruct setHorsesWinningChances(double[] horseChances, TimeVector clk) throws RemoteException;

   public ReturnStruct acceptedAllBets(TimeVector clk) throws RemoteException;

   public ReturnStruct acceptTheBet(TimeVector clk) throws RemoteException;

   public ReturnStruct placeABet(int horseId, int specId, double funds, TimeVector clk) throws RemoteException;

   public ReturnStruct areThereAnyWinners(int[] winningHorses, TimeVector clk) throws RemoteException;

   public ReturnStruct honouredAllTheBets(TimeVector clk) throws RemoteException;

   public ReturnStruct honourTheBet(TimeVector clk) throws RemoteException;

   public ReturnStruct goCollectTheGains(int specId, double funds, TimeVector clk) throws RemoteException;

   //Not implemented by now
   //public void shutdown() throws RemoteException;

}
