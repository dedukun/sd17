package interfaces;

import auxiliary.SimulPar;
import serverSide.BettingCenter.BettingCenter;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Betting Center's interface
 */
public interface BettingCenterInterface extends Remote{
    
   //TODO
   /**
   *  Execution of remote code.
   *  
   *    @param t code to be executed remotely
   *
   *    @return return value of the invocation of method execute of t
   *
   *    @throws RemoteException if the invocation of the remote method fails
   */

   Object executeTask (BettingCenter bc) throws RemoteException;
   
}
