package Communication;

import genclass.GenericIO;
import java.io.*;
import java.net.*;

/**
 *   This type of data implements communication channel based on sockets over TCP.
 */

public class ClientCom
{
  /**
   *  Communication socket
   *    @serialField commSocket
   */

   private Socket commSocket = null;

  /**
   *  Name of the computer where the server is located.
   */

   private String serverHostName = null;

  /**
   *  Number of the server's port.
   */

   private int serverPortNumb;

  /**
   *  Input stream of the communication channel.
   */

   private ObjectInputStream in = null;

  /**
   *  Output stream of the communication channel.
   */

   private ObjectOutputStream out = null;

  /**
   *  Instatiation of the communication channel.
   *
   *    @param hostName name of the computacional system where the server is located
   *    @param portNumb number of the listening port
   */

   public ClientCom (String hostName, int portNumb)
   {
      serverHostName = hostName;
      serverPortNumb = portNumb;
   }

  /**
   *  Opening of the communication channel.
   *  Instatiation of a communication socket and it's association with the server.
   *  Opening of the input and output streams.
   *
   *    @return true, if channel has been open <\br>
   *            false, otherwise
   */

   public boolean open ()
   {
      boolean success = true;
      SocketAddress serverAddress = new InetSocketAddress (serverHostName, serverPortNumb);

      try
      { commSocket = new Socket();
        commSocket.connect (serverAddress);
      }
      catch (UnknownHostException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - o nome do sistema computacional onde reside o servidor é desconhecido: " +
                                 serverHostName + "!");
        e.printStackTrace ();
        System.exit (1);
      }
      catch (NoRouteToHostException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - o nome do sistema computacional onde reside o servidor é inatingível: " +
                                 serverHostName + "!");
        e.printStackTrace ();
        System.exit (1);
      }
      catch (ConnectException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - o servidor não responde em: " + serverHostName + "." + serverPortNumb + "!");
        if (e.getMessage ().equals ("Connection refused"))
           success = false;
           else { GenericIO.writelnString (e.getMessage () + "!");
                  e.printStackTrace ();
                  System.exit (1);
                }
      }
      catch (SocketTimeoutException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - ocorreu um time out no estabelecimento da ligação a: " +
                                 serverHostName + "." + serverPortNumb + "!");
        success = false;
      }
      catch (IOException e)                           // erro fatal --- outras causas
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - ocorreu um erro indeterminado no estabelecimento da ligação a: " +
                                 serverHostName + "." + serverPortNumb + "!");
        e.printStackTrace ();
        System.exit (1);
      }

      if (!success) return (success);

      try
      { out = new ObjectOutputStream (commSocket.getOutputStream ());
      }
      catch (IOException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - não foi possível abrir o canal de saída do socket!");
        e.printStackTrace ();
        System.exit (1);
      }

      try
      { in = new ObjectInputStream (commSocket.getInputStream ());
      }
      catch (IOException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - não foi possível abrir o canal de entrada do socket!");
        e.printStackTrace ();
        System.exit (1);
      }

      return (success);
   }

  /**
   *  Close the communication channel.
   *  Close of input and output streams.
   *  Close of the communication socket.
   */

   public void close ()
   {
      try
      { in.close();
      }
      catch (IOException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - não foi possível fechar o canal de entrada do socket!");
        e.printStackTrace ();
        System.exit (1);
      }

      try
      { out.close();
      }
      catch (IOException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - não foi possível fechar o canal de saída do socket!");
        e.printStackTrace ();
        System.exit (1);
      }

      try
      { commSocket.close();
      }
      catch (IOException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - não foi possível fechar o socket de comunicação!");
        e.printStackTrace ();
        System.exit (1);
      }
   }

  /**
   *  Reading object in the communication channel
   *
   *    @return Object read
   */

   public Object readObject ()
   {
      Object fromServer = null;                            // objecto

      try
      { fromServer = in.readObject ();
      }
      catch (InvalidClassException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - o objecto lido não é passível de desserialização!");
        e.printStackTrace ();
        System.exit (1);
      }
      catch (IOException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - erro na leitura de um objecto do canal de entrada do socket de comunicação!");
        e.printStackTrace ();
        System.exit (1);
      }
      catch (ClassNotFoundException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - o objecto lido corresponde a um tipo de dados desconhecido!");
        e.printStackTrace ();
        System.exit (1);
      }

      return fromServer;
   }

  /**
   *  Writing object in the communication channel
   *
   *    @param toServer object being written
   */

   public void writeObject (Object toServer)
   {
      try
      { out.writeObject (toServer);
      }
      catch (InvalidClassException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - o objecto a ser escrito não é passível de serialização!");
        e.printStackTrace ();
        System.exit (1);
      }
      catch (NotSerializableException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - o objecto a ser escrito pertence a um tipo de dados não serializável!");
        e.printStackTrace ();
        System.exit (1);
      }
      catch (IOException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - erro na escrita de um objecto do canal de saída do socket de comunicação!");
        e.printStackTrace ();
        System.exit (1);
      }
   }
}
