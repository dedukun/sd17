package clientSide.Broker;

import genclass.GenericIO;
import interfaces.BettingCenterInterface;
import interfaces.ControlCenterInterface;
import interfaces.RaceTrackInterface;
import interfaces.StableInterface;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import registry.RegistryConfiguration;

public class BrokerClient {



    /**
     * Main from Horse/Jockey Client
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        BettingCenterInterface bettingCenter = null;
        ControlCenterInterface controlCenter = null;
        RaceTrackInterface raceTrack = null;
        StableInterface stable = null;

        //Modificar isto para ir buscar parametetros ao ficheiro de confguração
        String rmiRegHostName = RegistryConfiguration.REGISTRY_RMI_HOST;
        int rmiRegPortNumb = RegistryConfiguration.REGISTRY_RMI_PORT;

        //Vai buscar interface do Betting Center
        try {
               Registry registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
               bettingCenter = (BettingCenterInterface) registry.lookup(RegistryConfiguration.REGISTRY_BETTING_CENTER);
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
               controlCenter = (ControlCenterInterface) registry.lookup(RegistryConfiguration.REGISTRY_CONTROL_CENTER);
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
               raceTrack = (RaceTrackInterface) registry.lookup(RegistryConfiguration.REGISTRY_RACE_TRACK);
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
               stable = (StableInterface) registry.lookup(RegistryConfiguration.REGISTRY_STABLE);
           } catch (RemoteException e) {
               System.out.println("Exception finding logger: " + e.getMessage() + "!");
               e.printStackTrace();
               System.exit(1);
           } catch (NotBoundException e) {
               System.out.println("Logger is not registed: " + e.getMessage() + "!");
               e.printStackTrace();
               System.exit(1);
        }

        Broker broker = new Broker("Broker", bettingCenter, controlCenter, raceTrack, stable);
        broker.start();

        try{
            broker.join();
        }catch(InterruptedException e){}
    }
}
