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
        Registry registry = null;
        
        //Modificar isto para ir buscar parametetros ao ficheiro de confguração
        String rmiRegHostName = RegistryConfiguration.REGISTRY_RMI_HOST;
        int rmiRegPortNumb = RegistryConfiguration.REGISTRY_RMI_PORT;

        try{
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        } catch (RemoteException ex) {
            Logger.getLogger(BrokerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Vai buscar interface do Betting Center
        try {
               bettingCenter = (BettingCenterInterface) registry.lookup(RegistryConfiguration.REGISTRY_BETTING_CENTER);
               controlCenter = (ControlCenterInterface) registry.lookup(RegistryConfiguration.REGISTRY_CONTROL_CENTER);
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
