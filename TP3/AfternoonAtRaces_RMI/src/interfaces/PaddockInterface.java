package interfaces;

import auxiliary.SimulPar;
import serverSide.Paddock.Paddock;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * GenRepos interface
 */
public interface PaddockInterface extends Remote{

    public void proceedToPaddock(int hId, int hAgl);

    public boolean lastArrivedToPaddock(int hId);

    public int goCheckHorses(int specId);

    public boolean lastCheckHorses(int specId);

    public void unblockGoCheckHorses();
}
