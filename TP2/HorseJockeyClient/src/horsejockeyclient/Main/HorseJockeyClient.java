package horsejockeyclient.Main;

import horsejockeyclient.Auxiliar.SimulPar;

public class HorseJockeyClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        HorseJockey [] horseJockey = new HorseJockey[SimulPar.C * SimulPar.K];
        for(int race=0;race<SimulPar.K;race++){
            for(int id=0;id<SimulPar.C;id++){
                String name = "HorseJockey"+Integer.toString(race)+"_"+Integer.toString(id);
                int idx = id + (SimulPar.C * race);
                horseJockey[idx]=new HorseJockey(name, race, id);

                horseJockey[idx].start();
            }
        }
        
        for(int race=0;race<SimulPar.K;race++){
            for(int id=0;id<SimulPar.C;id++){
                int idx = id + (SimulPar.C * race);
                try{
                    horseJockey[idx].join();
                }catch(InterruptedException e){}
            }
        }
    }
}
