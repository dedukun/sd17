package interfaces;

import auxiliary.SimulPar;
import serverSide.ControlCenter.ControlCenter;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Control Center's interface
 */
public interface ControlCenterInterface extends Remote{

    public boolean waitForNextRace(int specId);

    public void  summonHorsesToPaddock();

    public void unblockGoCheckHorses();

    public void unblockProceedToPaddock();

    public void startTheRace();

    public void unblockMakeAMove();

    public void goWatchTheRace(int specId);

    public void reportResults(int[] winners);

    public boolean haveIWon(int hjid);

    public void entertainTheGuests();

    public void relaxABit(int specId);

}
