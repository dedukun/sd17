package interfaces;

import auxiliary.SimulPar;
import serverSide.RaceTrack.RaceTrack;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * GenRepos interface
 */
public interface RaceTrackInterface extends Remote{
    
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

   Object executeTask (RaceTrack rt) throws RemoteException;
}
