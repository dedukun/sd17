package interfaces;

import auxiliary.SimulPar;
import serverSide.Paddock.Paddock;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * GenRepos interface
 */
public interface PaddockInterface extends Remote{

    public ReturnStruct proceedToPaddock(int hId, int hAgl, TimeVector clk);

    public ReturnStruct lastArrivedToPaddock(int hId, TimeVector clk);

    public ReturnStruct goCheckHorses(int specId, TimeVector clk);

    public ReturnStruct lastCheckHorses(int specId, TimeVector clk);

    public ReturnStruct unblockGoCheckHorses(TimeVector clk);
}
