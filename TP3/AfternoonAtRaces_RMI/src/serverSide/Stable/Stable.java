package serverSide.Stable;

import extras.SimulPar;
import auxiliary.BrokerStates;
import auxiliary.HorseJockeyStates;
import auxiliary.ReturnStruct;
import auxiliary.TimeVector;
import interfaces.GenReposInterface;
import interfaces.StableInterface;
import java.rmi.RemoteException;

/**
 * Stable.<br>
 * Where horses stay before a race.
 */
public class Stable implements StableInterface{

    /**
     * Current race number.
     */
    private int currentRace;

    /**
     * List of Horses winning chances.
     */
    private int[] horsesAgilities;

    /**
     * End of event.
     */
    private boolean endEvent;

    /**
     * Reference to General Repository
     */
    private GenReposInterface genRepos;

    /**
     * Reference to Time Vector.
     */
    private TimeVector clk;

    /**
     * Number of horses that left the stable.
     */
    private int horsesThatLeftStable;

    /**
     * Horse/Jockey waiting at the stable for the start of the parade - synchronization condition.
     */
    private boolean waitAtStable;

    /**
     * Shutdown signal
     */
    private boolean waitShut;

    /**
     * Connected clients
     */
    private int numClients;

    /**
     * Stable initialization.
     */
    public Stable(GenReposInterface genRepos) throws RemoteException{
        this.genRepos = genRepos;
        clk = new TimeVector();
        currentRace = -1;
        int totalHorses = SimulPar.C * SimulPar.K;
        horsesAgilities = new int[totalHorses];
        horsesThatLeftStable = 0;

        // Sync conditions
        waitAtStable = true;
        endEvent = false;

        waitShut = true;
        numClients = 2;
    }

    /**
     * Horse/Jockey pair waits at the stable.
     *
     *   @param hId
     *   @param hRaceNumber
     *   @param horseAgl
     */
    @Override
    public synchronized ReturnStruct proceedToStable(int hId, int hRaceNumber, int horseAgl, TimeVector clk) throws RemoteException{
        this.clk.updateTime(clk.getTime());
        //((HorseJockey) Thread.currentThread()).setState(HorseJockeyStates.ATS);
        int horseRaceNumber = hRaceNumber;
        int horseId = hId;
        int horseAgility = horseAgl;

        int index = horseId + (SimulPar.C * horseRaceNumber);
        horsesAgilities[index] = horseAgility;

        if(hRaceNumber == currentRace || (currentRace == -1 && hRaceNumber == 0)){
            genRepos.setHorseState(horseId, HorseJockeyStates.ATS, this.clk);
        }

        while( !endEvent && horseRaceNumber != currentRace){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        if(endEvent){
            genRepos.setHorseState(horseId, HorseJockeyStates.ATS, this.clk);
        }

        horsesThatLeftStable++;
        if(horsesThatLeftStable == SimulPar.C){
            //Reset vars
            horsesThatLeftStable = 0;
            currentRace = -1;
        }

        return new ReturnStruct(this.clk);
    }

    /**
     * Broker awakes Horse/Jockey pairs in the stable.
     *
     *   @param raceNumber Number of the race that is going to occur
     *   @return List of the winning chances of the horses in the current race
     */
    @Override
    public synchronized ReturnStruct summonHorsesToPaddock(int raceNumber, TimeVector clk) throws RemoteException{
        this.clk.updateTime(clk.getTime());
        //((Broker) Thread.currentThread()).setState(BrokerStates.ANR);

        if(raceNumber == 0)
            genRepos.setBrokerState(BrokerStates.OTE, this.clk);
        genRepos.setRaceNumber(raceNumber,this.clk);
        genRepos.setBrokerState(BrokerStates.ANR,this.clk);

        // Wake up horses
        currentRace = raceNumber;
        waitAtStable = false;

        notifyAll(); // Wakes up Horses

        double [] horsesChances = new double[SimulPar.C];
        int sumAgilities = 0;
        for(int i = 0; i < SimulPar.C; i++){
            int idx = i + (SimulPar.C * raceNumber);
            sumAgilities += horsesAgilities[ idx ];
        }

        for(int i = 0; i < SimulPar.C; i++){
            int idx = i + (SimulPar.C * raceNumber);
            double horseAgility = horsesAgilities[ idx ];
            horsesChances[i] =  horseAgility / sumAgilities;

            genRepos.setHorseAgility(i, (int)horseAgility,this.clk);
            genRepos.setOdds(i, horsesChances[i]*100,this.clk);
        }

        return new ReturnStruct(this.clk, horsesChances);
    }

    /**
     * Broker is closing the event and is waking up horses from stable.
     */
    @Override
    public synchronized ReturnStruct entertainTheGuests(TimeVector clk) throws RemoteException{
        this.clk.updateTime(clk.getTime());
        //((Broker) Thread.currentThread()).setState(BrokerStates.PHAB);
        genRepos.setBrokerState(BrokerStates.PHAB,this.clk);

        endEvent = true;

        notifyAll();

        return new ReturnStruct(this.clk);
    }

    /**
     * Server is waiting for a shutdown signal
     */
    public synchronized void waitForShutdown() {
        try{
            while(waitShut){
                this.wait();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }


    /**
     * Disconnect client from server.
     *
     *   @return Clk
     */
    @Override
    public synchronized ReturnStruct disconnect(TimeVector clk) throws RemoteException{
        this.clk.updateTime(clk.getTime());
        numClients--;

        if(numClients == 0){
            genRepos.disconnect(clk);
            waitShut = false;
            notifyAll();
        }

        return new ReturnStruct(this.clk);
    }
}
