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

    public ReturnStruct startTheRace(TimeVector clk);

    public ReturnStruct proceedToStartLine(int hId, TimeVector clk);

    public ReturnStruct makeAMove(int hId, int hAgl, TimeVector clk);

    public ReturnStruct hasRaceFinished(int hId, TimeVector clk);

    public ReturnStruct[] getResults(TimeVector clk);
}
