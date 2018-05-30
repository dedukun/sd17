package serverSide.BettingCenter;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import genclass.GenericIO;
import interfaces.BettingCenterInterface;
import interfaces.GenReposInterface;
import interfaces.Register;
import java.rmi.Remote;
import registry.RegistryConfiguration;

/**
 *  This data type instantiates and registers a remote object that will run mobile code.
 *  Communication is based in Java RMI.
 */

public class BettingCenterServer{
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
     GenericIO.writeString ("Nome do nó de processamento onde está localizado o serviço de registo? ");
     rmiRegHostName = GenericIO.readlnString ();
     GenericIO.writeString ("Número do port de escuta do serviço de registo? ");
     rmiRegPortNumb = GenericIO.readlnInt ();

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
     BettingCenter bc = new BettingCenter(genReposInterface);
     BettingCenterInterface bcStub = null;
	 
	 //Endicar porto de escuta
     int listeningPort = 22001;                            /* it should be set accordingly in each case */

	 
     try
     { 
         //TODO - Implementar interfaces e arranjar bc
         bcStub = (BettingCenterInterface) UnicastRemoteObject.exportObject ((Remote) bc, listeningPort);
     }
     catch (RemoteException e)
     { GenericIO.writelnString ("ComputeEngine stub generation exception: " + e.getMessage ());
       e.printStackTrace ();
       System.exit (1);
     }
     GenericIO.writelnString ("Stub was generated!");

    /* register it with the general registry service */

     String nameEntryBase = "RegisterHandler";
     String nameEntryObject = "BettingCenterInterface";
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
     { reg.bind (nameEntryObject, bcStub);
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
