package clientSide.Spectators;

import auxiliary.SimulPar;

public class SpectatorClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Spectators [] spectator = new Spectators[SimulPar.S];
        for(int id=0;id<SimulPar.S;id++){
            String name = "Spectactor"+Integer.toString(id);
            spectator[id]=new Spectators(name, id);
            //generalRepository.setSpectatorMoney(id, (int)spectator[id].getFunds());

            spectator[id].start();
        }
        
       for(int id=0;id<SimulPar.S;id++){
            try{
                spectator[id].join();
            }catch(InterruptedException e){}
        }
    }
}
