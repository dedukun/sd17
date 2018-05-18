package serverSide.Stable;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import genclass.GenericIO;
import interfaces.StableInterface;
import interfaces.Register;
import java.rmi.Remote;

/**
 *  This data type instantiates and registers a remote object that will run mobile code.
 *  Communication is based in Java RMI.
 */

public class StableServer {
  /**
   *  Main task.
   */

   public static void main(String[] args)
   {
    /* get location of the registry service */

     String rmiRegHostName;
     int rmiRegPortNumb;

     GenericIO.writeString ("Nome do nó de processamento onde está localizado o serviço de registo? ");
     rmiRegHostName = GenericIO.readlnString ();
     GenericIO.writeString ("Número do port de escuta do serviço de registo? ");
     rmiRegPortNumb = GenericIO.readlnInt ();

    /* create and install the security manager */

     if (System.getSecurityManager () == null)
        System.setSecurityManager (new SecurityManager ());
     GenericIO.writelnString ("Security manager was installed!");

    /* instantiate a remote object that runs mobile code and generate a stub for it */

	//Fazer equivalente para servers
     Stable st = new Stable ();
     StableInterface stStub = null;
	 
	 //Endicar porto de escuta
     int listeningPort = 22001;                            /* it should be set accordingly in each case */

	 
     try
     { 
         //TODO - Implementar interfaces e arranjar bc
         stStub = (StableInterface) UnicastRemoteObject.exportObject ((Remote) st, listeningPort);
     }
     catch (RemoteException e)
     { GenericIO.writelnString ("ComputeEngine stub generation exception: " + e.getMessage ());
       e.printStackTrace ();
       System.exit (1);
     }
     GenericIO.writelnString ("Stub was generated!");

    /* register it with the general registry service */

     String nameEntryBase = "RegisterHandler";
     String nameEntryObject = "StableInterface";
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
     { reg.bind (nameEntryObject, stStub);
     }
     
     catch (RemoteException e)
     { GenericIO.writelnString ("ComputeEngine registration exception: " + e.getMessage ());
       e.printStackTrace ();
       System.exit (1);
     }
     catch (AlreadyBoundException e)
     { GenericIO.writelnString ("ComputeEngine already bound exception: " + e.getMessage ());
       e.printStackTrace ();
       System.exit (1);
     }
     GenericIO.writelnString ("ComputeEngine object was registered!");
	 
	 //Bloquear server atraves de mecanismos de sincroniação
	 //reg.unbind , retirar referenceia do registo
	 //matar thread base, unexportObject
	 //fazer system call que faz signal no caso de usar monitorImplicitos
 }
}
