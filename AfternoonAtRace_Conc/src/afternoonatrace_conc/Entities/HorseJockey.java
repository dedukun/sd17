/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afternoonatrace_conc.Entities;
import afternoonatrace_conc.SharedRegions.*;

/**
 * Horse/Jockey Thread
 */
public class HorseJockey extends Thread{
    private int raceNumber;
    private int hjid;
    private States hjstate;
    private double speed;

    /**
     * Horse/Jockey initialization
     *
     * @param raceNumber Number of the race
     * @param hjid Horse/Jockey pair ID
     */
    public HorseJockey(int raceNumber, int hjid) {
        this.raceNumber = raceNumber;
        this.hjid = hjid;
        this.hjstate = null;
        this.speed = 1 + 5 * Math.random(); // Random speed
    }

    public int getRaceNumber() {
        return raceNumber;
    }

    public int getHJId() {
        return hjid;
    }

    public States getHJState() {
        return hjstate;
    }

    public double getSpeed() {
        return speed;
    }

    public void setState(States hjstate) {
        this.hjstate = hjstate;
    }

    /**
     * Horse/Jockey life cycle
     */
    @Override
    public void run(){
        Stable.proceedToStable();
        //unblocked by sumHorsesToPaddock()
        if(Stable.lastProoceedToPaddock()) //Blocked
            ControlCenter.unblockProceedToPaddock();
        //unblocked by lastCheckHorses()
        Paddock.proceedToPaddock(); //Blocked
        if(Paddock.lastProoceedToStartLine())
            Paddock.unblockProoceedToStartLine();
        RaceTrack.proceedToStartLine(); //Blocked
        do{
            //unblocked by startTheRace() or makeAMove()
            RaceTrack.unblockMakeAMove();
            if(!RaceTrack.hasFinishLineBeenCrossed()){
                RaceTrack.makeAMove();
                if(RaceTrack.lastMakeAMove())
                    RaceTrack.unblockMakeAMove(); //Blocked
            }
        }while(!RaceTrack.hasRaceFinished());
        Stable.proceedToStable();
    }
}
