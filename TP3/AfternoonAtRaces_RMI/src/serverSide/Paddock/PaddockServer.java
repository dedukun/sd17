package serverSide.Paddock;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import genclass.GenericIO;
import interfaces.GenReposInterface;
import interfaces.PaddockInterface;
import interfaces.Register;
import java.rmi.Remote;
import registry.RegistryConfiguration;

/**
 *  This data type instantiates and registers a remote object that will run mobile code.
 *  Communication is based in Java RMI.
 */

public class PaddockServer{
  /**
   *  Main task.
   */

   public static void main(String[] args) throws RemoteException
   {
    /* get location of the registry service */
     GenReposInterface genReposInterface = null;
     String rmiRegHostName;
     int rmiRegPortNumb;

     //Modificar isto para ir buscar parametetros ao ficheiro de confguração
     rmiRegHostName = RegistryConfiguration.REGISTRY_RMI_HOST;
     rmiRegPortNumb = RegistryConfiguration.REGISTRY_RMI_PORT;

     //Vai buscar interface do Genereal Repository
     try {
            Registry registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
            genReposInterface = (GenReposInterface) registry.lookup(RegistryConfiguration.REGISTRY_GEN_REPOS);
        } catch (RemoteException e) {
            System.out.println("Exception finding logger: " + e.getMessage() + "!");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("Logger is not registed: " + e.getMessage() + "!");
            e.printStackTrace();
            System.exit(1);
        }
    /* create and install the security manager */

     if (System.getSecurityManager () == null)
        System.setSecurityManager (new SecurityManager ());
     GenericIO.writelnString ("Security manager was installed!");

    /* instantiate a remote object that runs mobile code and generate a stub for it */

	//Fazer equivalente para servers
     Paddock pd = new Paddock (genReposInterface);
     PaddockInterface pdStub = null;

	 //Endicar porto de escuta
     int listeningPort = RegistryConfiguration.REGISTRY_PADDOCK_PORT;           /* it should be set accordingly in each case */


     try
     {
         //TODO - Implementar interfaces e arranjar bc
         pdStub = (PaddockInterface) UnicastRemoteObject.exportObject ((Remote) pd, listeningPort);
     }
     catch (RemoteException e)
     { GenericIO.writelnString ("Paddock stub generation exception: " + e.getMessage ());
       e.printStackTrace ();
       System.exit (1);
     }
     GenericIO.writelnString ("Stub was generated!");

    /* register it with the general registry service */

     String nameEntryBase = RegistryConfiguration.REGISTRY_RMI;
     String nameEntryObject = RegistryConfiguration.REGISTRY_PADDOCK;
     Registry registry = null;
     Register reg = null;

     try
     { registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
     }
     catch (RemoteException e)
     { GenericIO.writelnString ("RMI registry creation exception: " + e.getMessage ());
       e.printStackTrace ();
       System.exit (1);
     }
     GenericIO.writelnString ("RMI registry was created!");

     try
     { reg = (Register) registry.lookup (nameEntryBase);
     }
     catch (RemoteException e)
     { GenericIO.writelnString ("RegisterRemoteObject lookup exception: " + e.getMessage ());
       e.printStackTrace ();
       System.exit (1);
     }
     catch (NotBoundException e)
     { GenericIO.writelnString ("RegisterRemoteObject not bound exception: " + e.getMessage ());
       e.printStackTrace ();
       System.exit (1);
     }

     try
     { reg.bind (nameEntryObject, pdStub);
     }

     catch (RemoteException e)
     { GenericIO.writelnString ("Paddock registration exception: " + e.getMessage ());
       e.printStackTrace ();
       System.exit (1);
     }
     catch (AlreadyBoundException e)
     { GenericIO.writelnString ("Paddock already bound exception: " + e.getMessage ());
       e.printStackTrace ();
       System.exit (1);
     }
     GenericIO.writelnString ("Paddock object was registered!");

     // Block
     pd.waitForShutdown();

     try{
         reg.unbind(nameEntryObject);
     } catch (RemoteException e)
     { GenericIO.writelnString ("RegisterRemoteObject unbind exception: " + e.getMessage ());
       e.printStackTrace ();
       System.exit (1);
     }
     catch (NotBoundException e)
     { GenericIO.writelnString ("RegisterRemoteObject not bound exception: " + e.getMessage ());
       e.printStackTrace ();
       System.exit (1);
     }

    try
     {
         boolean succ = false;

         do{
             succ = UnicastRemoteObject.unexportObject ((Remote) pd, true);
         }while(!succ);
     }
     catch (RemoteException e)
     { GenericIO.writelnString ("Paddock stub remove exception: " + e.getMessage ());
       e.printStackTrace ();
       System.exit (1);
     }

     GenericIO.writelnString ("Paddock shutdown!");
 }
}
