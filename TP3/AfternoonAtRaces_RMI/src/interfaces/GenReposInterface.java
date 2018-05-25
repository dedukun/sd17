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

    public ReturnStruct initLog(TimeVector clk);

    public ReturnStruct updateLog(TimeVector clk);

    public ReturnStruct setBetS(int specId, int horseId, TimeVector clk);

    public ReturnStruct setBetA(int specId, int betamount, TimeVector clk);

    public ReturnStruct setOdds(int horseId, double odd, TimeVector clk);

    public ReturnStruct setHorseIteration(int horseId, TimeVector clk);

    public ReturnStruct setHorsePosition(int horseId, int pos, TimeVector clk);

    public ReturnStruct setHorseEnd(int horseId, int place, TimeVector clk);

    public ReturnStruct setTrackSize(int size, TimeVector clk);

    public ReturnStruct setRaceNumber(int num, TimeVector clk);

    public ReturnStruct setBrokerState(BrokerStates state, TimeVector clk);

    public ReturnStruct setSpectatorState(int specId,SpectatorStates state, TimeVector clk);

    public ReturnStruct setSpectatorMoney(int specId, int funds, TimeVector clk);

    public ReturnStruct setHorseState(int horseId,HorseJockeyStates state, TimeVector clk);

    public ReturnStruct setHorseAgility(int horseId,int horseAgl, TimeVector clk);
}
