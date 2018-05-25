package interfaces;

import auxiliary.ReturnStruct;
import auxiliary.SimulPar;
import auxiliary.TimeVector;
import serverSide.ControlCenter.ControlCenter;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Control Center's interface
 */
public interface ControlCenterInterface extends Remote{

    public ReturnStruct waitForNextRace(int specId, TimeVector clk);

    public ReturnStruct summonHorsesToPaddock(TimeVector clk);

    public ReturnStruct unblockGoCheckHorses(TimeVector clk);

    public ReturnStruct unblockProceedToPaddock(TimeVector clk);

    public ReturnStruct startTheRace(TimeVector clk);

    public ReturnStruct unblockMakeAMove(TimeVector clk);

    public ReturnStruct goWatchTheRace(int specId, TimeVector clk);

    public ReturnStruct reportResults(int[] winners, TimeVector clk);

    public ReturnStruct haveIWon(int hjid, TimeVector clk);

    public ReturnStruct entertainTheGuests(TimeVector clk);

    public ReturnStruct relaxABit(int specId, TimeVector clk);

}
