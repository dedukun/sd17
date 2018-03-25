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
        GeneralRepository generalRepository = new GeneralRepository();
        Stable stable= new Stable(generalRepository);
        Paddock paddock= new Paddock(generalRepository);
        RaceTrack raceTrack= new RaceTrack(generalRepository);
        BettingCenter bettingCenter= new BettingCenter(generalRepository);
        ControlCenter controlCenter= new ControlCenter(generalRepository);


        //Entities Initialization
        //HorseJockey
        HorseJockey [] horseJockey = new HorseJockey[SimulPar.C * SimulPar.K];
        for(int race=0;race<SimulPar.K;race++){
            for(int id=0;id<SimulPar.C;id++){
                String name = "HorseJockey"+Integer.toString(race)+"_"+Integer.toString(id);
                int idx = id + (SimulPar.C * race);
                horseJockey[idx]=new HorseJockey(name, race, id, controlCenter, paddock, raceTrack, stable);

                horseJockey[idx].start();
            }
        }

        //Spectators
        Spectators [] spectator = new Spectators[SimulPar.S];
        for(int id=0;id<SimulPar.S;id++){
            String name = "Spectactor"+Integer.toString(id);
            spectator[id]=new Spectators(name, id, bettingCenter, controlCenter, paddock);

            spectator[id].start();
        }

        //Broker
        Broker broker = new Broker("Broker", bettingCenter, controlCenter, raceTrack, stable);
        broker.start();


        // End Threads

        //HorseJockey
        for(int race=0;race<SimulPar.K;race++){
            for(int id=0;id<SimulPar.C;id++){
                int idx = id + (SimulPar.C * race);
                try{
                    horseJockey[idx].join();
                }catch(InterruptedException e){}
            }
        }

        //Spectators
        for(int id=0;id<SimulPar.S;id++){
            try{
                spectator[id].join();
            }catch(InterruptedException e){}
        }

        //Broker
        try{
            broker.join();
        }catch(InterruptedException e){}
    }
}
