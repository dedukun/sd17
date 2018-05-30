package interfaces;

import auxiliary.*;
import auxiliary.ReturnStruct;
import auxiliary.SimulPar;
import auxiliary.TimeVector;
import serverSide.GenRepos.GeneralRepository;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * GenRepos interface
 */
public interface GenReposInterface extends Remote{

    /**
    * Prints header of the logger file.
    *
    *   @param clk Clock
    *   @return Clock
    *   @throws RemoteException may throw during a execution of a remote method call
    */
    public ReturnStruct initLog(TimeVector clk) throws RemoteException;

    /**
    * Updates the log when a change happens.
    *
    *   @param clk Clock
    *   @return Clock
    *   @throws RemoteException may throw during a execution of a remote method call
    */
    public ReturnStruct updateLog(TimeVector clk) throws RemoteException;

    /**
     * Set the Horse/Jockey pair selected by the Spectator to bet on.
     *
     *   @param specId Identifier of the Spectator
     *   @param horseId Identifier of the Horse/Jockey pair
     *
     *   @param clk Clock
     *   @return Clock
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct setBetS(int specId, int horseId, TimeVector clk) throws RemoteException;

    /**
     * Set the ammount betted by a specified Spectator in the current race.
     *
     *   @param specId Identifier of the Spectator
     *   @param betamount Amount betted
     *   @param clk Clock
     *   @return Clock
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct setBetA(int specId, int betamount, TimeVector clk) throws RemoteException;

    /**
     * Set the probability of winning for the given horse.
     *
     *   @param horseId Identifier of the Horse/Jockey pair
     *   @param odd Chance of winning in percentage
     *   @param clk Clock
     *   @return Clock
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct setOdds(int horseId, double odd, TimeVector clk) throws RemoteException;

    /**
     * Increment interation number of the Horse/Jockey pair.
     *
     *   @param horseId Identifier of the Horse/Jockey pair
     *   @param clk Clock
     *   @return Clock
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct setHorseIteration(int horseId, TimeVector clk) throws RemoteException;

    /**
     * Set the specified Horse/Jockey pair current position in the ocurring race.
     *
     *   @param horseId Identifier of the Horse/Jockey pair
     *   @param pos Current position
     *   @param clk Clock
     *   @return Clock
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct setHorsePosition(int horseId, int pos, TimeVector clk) throws RemoteException;

    /**
     * Set the Horse/Jockey pairs that have already finished the race.
     *
     *   @param horseId Identifier of the Horse/Jockey pair
     *   @param place Place that the given Horse/Jockey pair finished the race
     *   @param clk Clock
     *   @return Clock
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct setHorseEnd(int horseId, int place, TimeVector clk) throws RemoteException;

    /**
     * Set the current race track size.
     *
     *   @param size size of the track
     *   @param clk Clock
     *   @return Clock
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct setTrackSize(int size, TimeVector clk) throws RemoteException;

    /**
     * Set the current race number.
     *
     *   @param num number race being run
     *   @param clk Clock
     *   @return Clock
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct setRaceNumber(int num, TimeVector clk) throws RemoteException;

    /**
     * Set the current state of the Broker.
     *
     *   @param state state of the Broker
     *   @param clk Clock
     *   @return Clock
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct setBrokerState(BrokerStates state, TimeVector clk) throws RemoteException;

    /**
     * Set the current state of the specified Spectator.
     *
     *   @param specId ID of the Spectator
     *   @param state state of the Spectator
     *   @param clk Clock
     *   @return Clock
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct setSpectatorState(int specId,SpectatorStates state, TimeVector clk) throws RemoteException;

    /**
     * Set the current funds of the specified Spectator.
     *
     *   @param specId ID of the Spectator
     *   @param funds funds of the Spectator
     *   @param clk Clock
     *   @return Clock
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct setSpectatorMoney(int specId, int funds, TimeVector clk) throws RemoteException;

    /**
     * Set the current state of the specified Horse/Jockey pair.
     *
     *   @param horseId ID of the Horse
     *   @param state state of the Horse/Jockey pair.
     *   @param clk Clock
     *   @return Clock
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct setHorseState(int horseId,HorseJockeyStates state, TimeVector clk) throws RemoteException;

    /**
     * Set the agility of the specified Horse/Jockey pair.
     *
     *   @param horseId ID of the Horse
     *   @param horseAgl agility of the Horse/Jockey pair.
     *   @param clk Clock
     *   @return Clock
     *   @throws RemoteException may throw during a execution of a remote method call
     */
    public ReturnStruct setHorseAgility(int horseId,int horseAgl, TimeVector clk) throws RemoteException;

}
