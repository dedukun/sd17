package clientSide.Spectators;

import auxiliary.SimulPar;
import genclass.GenericIO;
import interfaces.PaddockInterface;
import interfaces.ControlCenterInterface;
import interfaces.BettingCenterInterface;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import registry.RegistryConfiguration;


public class SpectatorClient {

    /**
     * Main from Horse/Jockey Client
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        BettingCenterInterface bettingCenter = null;
        ControlCenterInterface controlCenter = null;
        PaddockInterface paddock = null;

        //Modificar isto para ir buscar parametetros ao ficheiro de confguração
        String rmiRegHostName = RegistryConfiguration.REGISTRY_RMI_HOST;
        int rmiRegPortNumb = RegistryConfiguration.REGISTRY_RMI_PORT;

        //Vai buscar interface do Betting Center
        try {
               Registry registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
               bettingCenter = (BettingCenterInterface) registry.lookup(RegistryConfiguration.REGISTRY_RMI);
           } catch (RemoteException e) {
               System.out.println("Exception finding logger: " + e.getMessage() + "!");
               e.printStackTrace();
               System.exit(1);
           } catch (NotBoundException e) {
               System.out.println("Logger is not registed: " + e.getMessage() + "!");
               e.printStackTrace();
               System.exit(1);
        }

        //Vai buscar interface do Control Center
        try {
               Registry registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
               controlCenter = (ControlCenterInterface) registry.lookup(RegistryConfiguration.REGISTRY_RMI);
           } catch (RemoteException e) {
               System.out.println("Exception finding logger: " + e.getMessage() + "!");
               e.printStackTrace();
               System.exit(1);
           } catch (NotBoundException e) {
               System.out.println("Logger is not registed: " + e.getMessage() + "!");
               e.printStackTrace();
               System.exit(1);
        }

        //Vai buscar interface do Paddock
        try {
               Registry registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
               paddock = (PaddockInterface) registry.lookup(RegistryConfiguration.REGISTRY_RMI);
           } catch (RemoteException e) {
               System.out.println("Exception finding logger: " + e.getMessage() + "!");
               e.printStackTrace();
               System.exit(1);
           } catch (NotBoundException e) {
               System.out.println("Logger is not registed: " + e.getMessage() + "!");
               e.printStackTrace();
               System.exit(1);
        }

        Spectators [] spectator = new Spectators[SimulPar.S];
        for(int id=0;id<SimulPar.S;id++){
            String name = "Spectactor"+Integer.toString(id);
            spectator[id]=new Spectators(name, id, bettingCenter, controlCenter, paddock);
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
