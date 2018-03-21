package afternoonatrace_conc.Main;
import afternoonatrace_conc.Entities.*;
import afternoonatrace_conc.SharedRegions.*;

/**
 *
 */
public class MainDatatype {
    /**
     * Main program.
     *
     *   @param args the command line arguments
     */
    public static void main(String[] args) {

        // Shared Regions Initialization
        GeneralRepository gr = new GeneralRepository();
        Stable s = new Stable(gr);
        Paddock p = new Paddock(gr);
        RaceTrack rt = new RaceTrack(gr);
        BettingCenter bc = new BettingCenter(gr);
        ControlCenter cc = new ControlCenter(gr);


        //Entities Initialization
        //Races
        HorseJockey [] hj = new HorseJockey[SimulPar.C];
        for(int r=0;r<SimulPar.K;r++){
            //HorseJockey
            for(int c=0;c<SimulPar.C;c++){
                String name = "HorseJockey"+Integer.toString(r)+"_"+Integer.toString(c);
                hj[c]=new HorseJockey(name,r,c, cc, p, rt, s);
                //launching thread
                hj[c].start();
            }
        }

        //Spectators
        Spectators [] specs = new Spectators[SimulPar.S];
        for(int ns=0;ns<SimulPar.S;ns++){
            String name = "Spectactor"+Integer.toString(ns);
            specs[ns]=new Spectators(name, ns, bc, cc, p);
            //launching thread
            specs[ns].start();
        }

        //Broker
        Broker b = new Broker("Broker", bc, cc, rt, s);
        b.start();
    }
}
