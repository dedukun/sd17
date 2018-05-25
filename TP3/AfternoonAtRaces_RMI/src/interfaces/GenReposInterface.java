package interfaces;

import auxiliary.SimulPar;
import serverSide.GenRepos.GeneralRepository;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * GenRepos interface
 */
public interface GenReposInterface extends Remote{

    public void initLog();

    public void updateLog();

    public void setBetS(int specId, int horseId);

    public void setBetA(int specId, int betamount);

    public void setOdds(int horseId, double odd);

    public void setHorseIteration(int horseId);

    public void setHorsePosition(int horseId, int pos);

    public void setHorseEnd(int horseId, int place);

    public void setTrackSize(int size);

    public void setRaceNumber(int num);

    public void setBrokerState(BrokerStates state);

    public void setSpectatorState(int specId,SpectatorsStates state);

    public void setSpectatorMoney(int specId, int funds);

    public void setHorseState(int horseId,HorseJockeyStates state);

    public void setHorseAgility(int horseId,int horseAgl);
}
