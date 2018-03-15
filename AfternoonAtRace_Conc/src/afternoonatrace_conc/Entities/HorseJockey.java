/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afternoonatrace_conc.Entities;
import afternoonatrace_conc.SharedRegions.*;

/**
 *
 * 
 */
public class HorseJockey extends Thread{
    private int raceNumber;
    private int hjid;
    private States hjstate;
    private double speed;

    public HorseJockey(int raceNumber, int hjid, States hjstate, double speed) {
        this.raceNumber = raceNumber;
        this.hjid = hjid;
        this.hjstate = null;
        this.speed = speed;
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

    public void setRaceNumber(int raceNumber) {
        this.raceNumber = raceNumber;
    }

    public void setId(int hjid) {
        this.hjid = hjid;
    }

    public void setState(States hjstate) {
        this.hjstate = hjstate;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
    
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
