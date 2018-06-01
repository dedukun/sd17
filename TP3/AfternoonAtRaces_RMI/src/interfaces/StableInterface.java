package interfaces;

import auxiliary.ReturnStruct;
import auxiliary.SimulPar;
import auxiliary.TimeVector;
import serverSide.Stable.Stable;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * GenRepos interface
 */
public interface StableInterface extends Remote{

    /**
     * Horse/Jockey pair waits at the stable.
     *
     *   @param hId
     *   @param hRaceNumber
     *   @param horseAgl
     *   @param clk Clock
     *   @return Clock
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct proceedToStable(int hId, int hRaceNumber, int horseAgl, TimeVector clk) throws RemoteException;

    /**
     * Broker awakes Horse/Jockey pairs in the stable.
     *
     *   @param raceNumber Number of the race that is going to occur
     *   @param clk Clock
     *   @return Clock and a list of the winning chances of the horses in the current race
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct summonHorsesToPaddock(int raceNumber, TimeVector clk) throws RemoteException;

    /**
     * Broker is closing the event and is waking up horses from stable.
     *
     *   @param clk Clock
     *   @return Clock
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct entertainTheGuests(TimeVector clk) throws RemoteException;
    
    //Not tested yet
    public void shutdown() throws RemoteException;
}
