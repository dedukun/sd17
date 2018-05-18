package interfaces;

import auxiliary.SimulPar;
import serverSide.ControlCenter.ControlCenter;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Control Center's interface
 */
public interface ControlCenterInterface extends Remote{
    
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

   Object executeTask (ControlCenter cc) throws RemoteException;
}
