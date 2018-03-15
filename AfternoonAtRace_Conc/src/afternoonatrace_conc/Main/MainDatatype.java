/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afternoonatrace_conc.Main;
import afternoonatrace_conc.Entities.*;

/**
 *
 * 
 */
public class MainDatatype {
    /**
     * @param args the command line arguments
     */
        public static Spectators [] specs = new Spectators[SimulPar.S];
        public static HorseJockey [] hj = new HorseJockey[SimulPar.C];
        
        //Broker
        public static Broker b = new Broker();

        public static void main(String[] args) {
            
            //Broker 
            b.start();
            
            //Spectators
                for(int s=0;s<SimulPar.S-1;s++){
                    String name = "Spectactor"+Integer.toString(s);
                    double money = 100 * Math.random();
                    specs[s]=new Spectators(null,name,money);
                    //launching thread
                    specs[s].start();
                }
                
            //Races
            for(int i=0;i<SimulPar.K-1;i++){
                //HorseJockey
                for(int c=0;c<SimulPar.C-1;c++){
                    String name = "HorseJockey"+Integer.toString(c);
                    double speed = 10 * Math.random();
                    hj[c]=new HorseJockey(i,c,null,speed);
                    //launching thread
                    hj[c].start();
                }
            }
            
            
        
        
        
    } 
}
