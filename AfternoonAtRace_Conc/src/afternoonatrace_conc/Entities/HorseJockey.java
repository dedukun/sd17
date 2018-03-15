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
public class HorseJockey {
    private int raceNumber;
    private int id;
    private States state;
    private double speed;

    public HorseJockey(int raceNumber, int id, States state, double speed) {
        this.raceNumber = raceNumber;
        this.id = id;
        this.state = null;
        this.speed = speed;
    }
 
    public int getRaceNumber() {
        return raceNumber;
    }

    public int getId() {
        return id;
    }

    public States getState() {
        return state;
    }

    public double getSpeed() {
        return speed;
    }

    public void setRaceNumber(int raceNumber) {
        this.raceNumber = raceNumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setState(States state) {
        this.state = state;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
    
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
