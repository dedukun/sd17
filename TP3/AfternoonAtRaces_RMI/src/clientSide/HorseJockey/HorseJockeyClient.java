package clientSide.HorseJockey;

import auxiliary.SimulPar;
import genclass.GenericIO;
import interfaces.PaddockInterface;
import interfaces.ControlCenterInterface;
import interfaces.RaceTrackInterface;
import interfaces.StableInterface;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import registry.RegistryConfiguration;

public class HorseJockeyClient {

    /**
     * Main from Horse/Jockey Client
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        PaddockInterface paddock = null;
        ControlCenterInterface controlCenter = null;
        RaceTrackInterface raceTrack = null;
        StableInterface stable = null;

        //Modificar isto para ir buscar parametetros ao ficheiro de confguração
        String rmiRegHostName = RegistryConfiguration.REGISTRY_RMI_HOST;
        int rmiRegPortNumb = RegistryConfiguration.REGISTRY_RMI_PORT;

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

        //Vai buscar interface do Race Track
        try {
               Registry registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
               raceTrack = (RaceTrackInterface) registry.lookup(RegistryConfiguration.REGISTRY_RMI);
           } catch (RemoteException e) {
               System.out.println("Exception finding logger: " + e.getMessage() + "!");
               e.printStackTrace();
               System.exit(1);
           } catch (NotBoundException e) {
               System.out.println("Logger is not registed: " + e.getMessage() + "!");
               e.printStackTrace();
               System.exit(1);
        }

        //Vai buscar interface do  Stable
        try {
               Registry registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
               stable = (StableInterface) registry.lookup(RegistryConfiguration.REGISTRY_RMI);
           } catch (RemoteException e) {
               System.out.println("Exception finding logger: " + e.getMessage() + "!");
               e.printStackTrace();
               System.exit(1);
           } catch (NotBoundException e) {
               System.out.println("Logger is not registed: " + e.getMessage() + "!");
               e.printStackTrace();
               System.exit(1);
        }

        HorseJockey [] horseJockey = new HorseJockey[SimulPar.C * SimulPar.K];
        for(int race=0;race<SimulPar.K;race++){
            for(int id=0;id<SimulPar.C;id++){
                String name = "HorseJockey"+Integer.toString(race)+"_"+Integer.toString(id);
                int idx = id + (SimulPar.C * race);
                horseJockey[idx]=new HorseJockey(name, race, id, paddock, controlCenter, raceTrack, stable);

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
