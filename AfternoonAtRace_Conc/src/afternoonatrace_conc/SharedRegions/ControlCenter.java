/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afternoonatrace_conc.SharedRegions;
import afternoonatrace_conc.Entities.*;
import afternoonatrace_conc.Main.*;
import java.lang.*;

/**
 *
 * 
 */
public class ControlCenter{
    
    public static boolean unblockProceedToPaddock(){
        return true;
    }
    
    public static void unblockMakeAMove(){
        
    }
    
    public static synchronized void  summonHorsesToPaddock(){
        System.out.println("Just testing");
        try{
            while(!lastCheckHorses()){
                MainDatatype.b.wait();
            }
        }catch(InterruptedException e){   
        }   
        
        MainDatatype.b.setState(States.ANNOUNCING_NEXT_RACE);
        MainDatatype.b.notifyAll();
    }
    
    public static void startTheRace(){
        
    }
    
    public static void reportResults(){
        
    }
    
    public static boolean areThereAnyWinners(){
        return true;
    }
    
    public static void entertainTheGuests(){
        
    }
    
    public static boolean waitForNextRace(){
        return true;
    }
    
    public static boolean lastCheckHorses(){
        return true;
    }
    
    public static void goWatchTheRace(){
        
    }
    
    public static boolean haveIWon(){
        return true;
    }
    
    public static void relaxABit(){
        
    }
}
