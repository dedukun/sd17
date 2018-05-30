package interfaces;

import auxiliary.ReturnStruct;
import auxiliary.SimulPar;
import auxiliary.TimeVector;
import serverSide.Paddock.Paddock;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * GenRepos interface
 */
public interface PaddockInterface extends Remote{

    /**
     * Horse/Jockey pair are parading at the paddock.
     *
     *   @param hId
     *   @param hAgl
     *   @param clk Clock
     *   @return Clock
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct proceedToPaddock(int hId, int hAgl, TimeVector clk) throws RemoteException;

    /**
     * Checks if current Horse/Jockey pair is the last to arrive at the paddock.
     *
     *   @param hId
     *   @param clk Clock
     *   @return Clock and a boolean that is true if the pair is the last one to arrive, false if not.
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct lastArrivedToPaddock(int hId, TimeVector clk) throws RemoteException;

    /**
     * Spectator is checking the Horse/Jockey pairs parading at the paddock and chooses the one he is betting on.
     *
     *   @param specId
     *   @param clk Clock
     *   @return Clock and the identifier of the Horse/Jockey pair to bet on
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct goCheckHorses(int specId, TimeVector clk) throws RemoteException;

    /**
     * Spectator checks if he is the last one to check the horses.
     *
     *   @param specId
     *   @param clk Clock
     *   @return Clock and a boolean that is true if it is the last horse to be checked, false if not.
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct lastCheckHorses(int specId, TimeVector clk) throws RemoteException;

    /**
     * Last Spectator to check the horses wakes them up.
     *
     *   @param clk Clock
     *   @return Clock
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct unblockGoCheckHorses(TimeVector clk) throws RemoteException;
    
    
    
}
