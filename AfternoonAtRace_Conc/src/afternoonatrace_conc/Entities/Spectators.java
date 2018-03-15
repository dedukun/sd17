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
public class Spectators {
    private States state;
    private String name;
    private double money;

    public Spectators(States state, String name, double money) {
        this.state = null;
        this.name = name;
        this.money = money;
    }

    public States getState() {
        return state;
    }

    public void setState(States state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
    
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
