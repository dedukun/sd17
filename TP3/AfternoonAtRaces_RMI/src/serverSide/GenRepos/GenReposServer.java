package serverSide.GenRepos;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import genclass.GenericIO;
import interfaces.GenReposInterface;
import interfaces.Register;
import java.rmi.Remote;
import registry.RegistryConfiguration;

/**
 *  This data type instantiates and registers a remote object that will run mobile code.
 *  Communication is based in Java RMI.
 */

public class GenReposServer{
  /**
   *  Main task.
   */

   public static void main(String[] args)
   {
    /* get location of the registry service */

     String rmiRegHostName;
     int rmiRegPortNumb;

     rmiRegHostName = RegistryConfiguration.REGISTRY_RMI_HOST;
     rmiRegPortNumb = RegistryConfiguration.REGISTRY_RMI_PORT;

    /* create and install the security manager */

     if (System.getSecurityManager () == null)
        System.setSecurityManager (new SecurityManager ());
     GenericIO.writelnString ("Security manager was installed!");

    /* instantiate a remote object that runs mobile code and generate a stub for it */

	//Fazer equivalente para servers
     GeneralRepository gr = new GeneralRepository ();
     GenReposInterface grStub = null;

	 //Endicar porto de escuta
     int listeningPort = RegistryConfiguration.REGISTRY_GEN_REPOS_PORT;                /* it should be set accordingly in each case */


     try
     {
         //TODO - Implementar interfaces e arranjar bc
         grStub = (GenReposInterface) UnicastRemoteObject.exportObject ((Remote) gr, listeningPort);
     }
     catch (RemoteException e)
     { GenericIO.writelnString ("GeneRepos stub generation exception: " + e.getMessage ());
       e.printStackTrace ();
       System.exit (1);
     }
     GenericIO.writelnString ("Stub was generated!");

    /* register it with the general registry service */

     String nameEntryBase = RegistryConfiguration.REGISTRY_RMI;
     String nameEntryObject = RegistryConfiguration.REGISTRY_GEN_REPOS;
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
     { reg.bind (nameEntryObject, grStub);
     }

     catch (RemoteException e)
     { GenericIO.writelnString ("GenRepos registration exception: " + e.getMessage ());
       e.printStackTrace ();
       System.exit (1);
     }
     catch (AlreadyBoundException e)
     { GenericIO.writelnString ("GenRepos already bound exception: " + e.getMessage ());
       e.printStackTrace ();
       System.exit (1);
     }
     GenericIO.writelnString ("GenRepos object was registered!");

     // Block
     gr.waitForShutdown();

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
             succ = UnicastRemoteObject.unexportObject ((Remote) gr, true);
         }while(!succ);
     }
     catch (RemoteException e)
     { GenericIO.writelnString ("GeneRepos stub remove exception: " + e.getMessage ());
       e.printStackTrace ();
       System.exit (1);
     }

     GenericIO.writelnString ("GenRepos shutdown!");
 }
}
