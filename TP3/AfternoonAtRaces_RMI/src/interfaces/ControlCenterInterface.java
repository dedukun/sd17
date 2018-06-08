package interfaces;

import auxiliary.ReturnStruct;
import extras.SimulPar;
import auxiliary.TimeVector;
import serverSide.ControlCenter.ControlCenter;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Control Center's interface
 */
public interface ControlCenterInterface extends Remote{

    /**
     * The Spectator is waiting in the Control Center for the next race to start.
     *
     *   @param specId
     *   @param clk Clock
     *   @return Clock and a bolean that tells if there's at least a race left
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct waitForNextRace(int specId, TimeVector clk) throws RemoteException;

    /**
     * Broker is waiting for the spectators finishing seeing the horses.
     *
     *   @param clk Clock
     *   @return Clock
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct summonHorsesToPaddock(TimeVector clk) throws RemoteException;

    /**
     * Last Spectator that finished watching the horses wakes up Broker.
     *
     *   @param clk Clock
     *   @return Clock
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct unblockGoCheckHorses(TimeVector clk) throws RemoteException;

    /**
     * The last Horse/Jockey pair to arrive at the Paddock wakes up Spectators to go see the parade.
     *
     *   @param clk Clock
     *   @return Clock
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct unblockProceedToPaddock(TimeVector clk) throws RemoteException;

    /**
     * Broker is starting the race.
     *
     *   @param clk Clock
     *   @return Clock
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct startTheRace(TimeVector clk) throws RemoteException;

    /**
     * Last Horse/Jockey pair to make a move wakes up the Broker.
     *
     *   @param clk Clock
     *   @return Clock
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct unblockMakeAMove(TimeVector clk) throws RemoteException;

    /**
     * Spectator is watching the race.
     *
     *   @param specId
     *   @param clk Clock
     *   @return Clock
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct goWatchTheRace(int specId, TimeVector clk) throws RemoteException;

    /**
     * Broker is reporting the results to the Spectators.
     *
     *   @param winners Array with the identifier of the winning Horse/Jockey pair(s)
     *   @param clk Clock
     *   @return Clock
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct reportResults(int[] winners, TimeVector clk) throws RemoteException;

    /**
     * Spectator if checking if that horse that he betted has won.
     *
     *   @param hjid Horse/Jockey pair identifier
     *   @param clk Clock
     *   @return Clock and a bolean that is true if the pair has won, false if not.
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct haveIWon(int hjid, TimeVector clk) throws RemoteException;

    /**
    * Broker is entertaining the guests.
    *
    *   @param clk Clock
    *   @return Clock
    *   @throws RemoteException may throw during a execution of a remote method call
    */
    public ReturnStruct entertainTheGuests(TimeVector clk) throws RemoteException;


    /**
    * Spectator is relaxing.
    *
    *   @param specId
    *   @param clk Clock
    *   @return Clock
    *   @throws RemoteException may throw during a execution of a remote method call
    */
    public ReturnStruct relaxABit(int specId, TimeVector clk) throws RemoteException;

   /**
    * Disconnects client from server
    */
   public ReturnStruct disconnect(TimeVector clk) throws RemoteException;
}
