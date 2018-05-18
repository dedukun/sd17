package interfaces;

import auxiliary.SimulPar;
import serverSide.Stable.Stable;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * GenRepos interface
 */
public interface StableInterface extends Remote{
    
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

   Object executeTask (Stable st) throws RemoteException;
}
