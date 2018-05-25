package interfaces;

import auxiliary.SimulPar;
import serverSide.Stable.Stable;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * GenRepos interface
 */
public interface StableInterface extends Remote{

    public ReturnStruct proceedToStable(int hId, int hRaceNumber, int horseAgl, TimeVector clk);

    public ReturnStruct[] summonHorsesToPaddock(int raceNumber, TimeVector clk);

    public ReturnStruct entertainTheGuests(TimeVector clk);
}
