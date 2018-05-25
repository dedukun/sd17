package interfaces;

import auxiliary.SimulPar;
import serverSide.RaceTrack.RaceTrack;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * GenRepos interface
 */
public interface RaceTrackInterface extends Remote{

    public void startTheRace();

    public void proceedToStartLine(int hId);

    public boolean makeAMove(int hId, int hAgl);

    public boolean hasRaceFinished(int hId);

    public int[] getResults();
}
