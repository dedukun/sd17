package afternoonatrace_conc.Main;
import afternoonatrace_conc.Entities.*;

/**
 *
 */
public class MainDatatype {
    public static Broker b = new Broker();
    public static HorseJockey [] hj = new HorseJockey[SimulPar.C];
    public static Spectators [] specs = new Spectators[SimulPar.S];

    /**
     * Main program
     *
     *   @param args the command line arguments
     */
    public static void main(String[] args) {

        //Broker
        b.start();

        //Spectators
        for(int s=0;s<SimulPar.S-1;s++){
            String name = "Spectactor"+Integer.toString(s);
            specs[s]=new Spectators(name);
            //launching thread
            specs[s].start();
        }

        //Races
        for(int r=0;r<SimulPar.K-1;r++){
            //HorseJockey
            for(int c=0;c<SimulPar.C-1;c++){
                String name = "HorseJockey"+Integer.toString(c);
                hj[c]=new HorseJockey(r,c);
                //launching thread
                hj[c].start();
            }
        }
    }
}
