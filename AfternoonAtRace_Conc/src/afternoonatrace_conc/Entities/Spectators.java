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
public class Spectators extends Thread{
    private States sstate;
    private String sname;
    private double money;

    public Spectators(States sstate, String sname, double money) {
        this.sstate = null;
        this.sname = sname;
        this.money = money;
    }

    public States getSState() {
        return sstate;
    }

    public void setState(States sstate) {
        this.sstate = sstate;
    }

    public String getSName() {
        return sname;
    }

    public void setSName(String name) {
        this.sname = sname;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
    
    @Override
    public void run(){
        //Blocked
        while(ControlCenter.waitForNextRace()){
            //Unblocked by proceedToPaddock()
            if(ControlCenter.lastCheckHorses())
                Paddock.unblockGoCheckHorses();
            Paddock.goCheckHorses();
            //Unblocked by proceedToStartLine()
            BettingCenter.placeABet();//Blocked
            //Unblocked by acceptTheBet()
            ControlCenter.goWatchTheRace();//Blocked
            //Unblocked by reportResults()
            if(ControlCenter.haveIWon())
                BettingCenter.goCollectTheGains();//Blocked
            //Unblocked by honourTheBets()
        }
        ControlCenter.relaxABit();
    }
    
    
}
