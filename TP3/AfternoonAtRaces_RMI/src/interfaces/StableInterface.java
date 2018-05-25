package interfaces;

import auxiliary.SimulPar;
import serverSide.Stable.Stable;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * GenRepos interface
 */
public interface StableInterface extends Remote{

    public void proceedToStable(int hId, int hRaceNumber, int horseAgl);

    public double[] summonHorsesToPaddock(int raceNumber);

    public void entertainTheGuests();
}
