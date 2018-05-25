package interfaces;

import auxiliary.ReturnStruct;
import auxiliary.SimulPar;
import auxiliary.TimeVector;
import serverSide.RaceTrack.RaceTrack;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * GenRepos interface
 */
public interface RaceTrackInterface extends Remote{

    /**
     * Broker wakes up one horse.
     *
     *   @param clk Clock
     *   @return Clock
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct startTheRace(TimeVector clk) throws RemoteException;

    /**
     * Horse/Jockey pair is waiting at the start line of the race track.
     *
     *   @param clk Clock
     *   @return Clock
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct proceedToStartLine(int hId, TimeVector clk) throws RemoteException;

    /**
     * Horse/Jockey pair makes a move, wakes up next Horse/Jockey pair and checks if the race has ended.
     *
     *   @param hId
     *   @param hAgl
     *   @param clk Clock
     *   @return Clock and a boolean representing if the race has ended
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct makeAMove(int hId, int hAgl, TimeVector clk) throws RemoteException;

    /**
     * Horse/Jockey pair is waiting to make another move and returns the a boolean representing if the race has finished.
     *
     *   @param hId
     *   @param clk Clock
     *   @return Clock and a boolean representing if the race has finished
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct hasRaceFinished(int hId, TimeVector clk) throws RemoteException;

    /**
     * Broker is getting the list winner(s) of the race.
     *
     *   @param clk Clock
     *   @return Clock and an array of identifier of the horse(s) that have won the race
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct getResults(TimeVector clk) throws RemoteException;
}
