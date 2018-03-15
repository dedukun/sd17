/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afternoonatrace_conc.Entities;
import afternoonatrace_conc.Main.SimulPar;
import afternoonatrace_conc.SharedRegions.*;

/**
 *
 * 
 */
//extends Thread para puder iniciazliar threads
public class Broker extends Thread{
    
    private States bstate;

    public Broker() {
        this.bstate = States.OPENING_THE_EVENT;
    }

    public void setState(States bstate) {
        this.bstate = bstate;
    }

    public States getBState() {
        return bstate;
    }
    
    @Override
    public void run(){
        for(int k=0; k<SimulPar.K; k++){
            ControlCenter.summonHorsesToPaddock(); //Blocked
            //unblocked by lastCheckHorses() or placeABet()
            while(BettingCenter.acceptAllBets()){
                BettingCenter.acceptTheBet(); //Blocked
            }
            //Unblocked by unblockMakeAMove()
            ControlCenter.startTheRace();//Blocked
            ControlCenter.reportResults();
            if(ControlCenter.areThereAnyWinners()){
                while(BettingCenter.honourAllTheBets())
                    //Queue Unblock
                    BettingCenter.hounourTheBet();//Blocked
            }
            ControlCenter.entertainTheGuests();
        }
    }
}
