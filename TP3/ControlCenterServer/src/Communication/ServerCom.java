package Communication;

import genclass.GenericIO;
import java.io.*;
import java.net.*;

/**
 *   Server side communication sockets.
 */

public class ServerCom
{
  /**
   *  Socket de escuta
   *    @serialField listeningSocket
   */

   private ServerSocket listeningSocket = null;

  /**
   *  Socket de comunicação
   *    @serialField commSocket
   */

   private Socket commSocket = null;

  /**
   *  Número do port de escuta do servidor
   *    @serialField serverPortNumb
   */

   private int serverPortNumb;

  /**
   *  Stream de entrada do canal de comunicação
   *    @serialField in
   */

   private ObjectInputStream in = null;

  /**
   *  Stream de saída do canal de comunicação
   *    @serialField out
   */

   private ObjectOutputStream out = null;

  /**
   *  Instatiation of a communication channel.
   *
   *    @param portNumb number of the listening port
   */

   public ServerCom (int portNumb)
   {
      serverPortNumb = portNumb;
   }

  /**
   *  Instatiation of a communication channel.
   *
   *    @param portNumb number of the listening port
   *    @param lSocket listening socket
   */

   public ServerCom (int portNumb, ServerSocket lSocket)
   {
      serverPortNumb = portNumb;
      listeningSocket = lSocket;
   }

  /**
   *  Start of the serice.
   *  Instatiation of a listening socket and it's association to the address of the local machine and the public listening ports.
   */

   public void start ()
   {
      try
      { listeningSocket = new ServerSocket (serverPortNumb);
      }
      catch (BindException e)                         // erro fatal --- port já em uso
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - não foi possível a associação do socket de escuta ao port: " +
                                 serverPortNumb + "!");
        e.printStackTrace ();
        System.exit (1);
      }
      catch (IOException e)                           // erro fatal --- outras causas
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - ocorreu um erro indeterminado na associação do socket de escuta ao port: " +
                                 serverPortNumb + "!");
        e.printStackTrace ();
        System.exit (1);
      }
   }

  /**
   *  End of service.
   *  Close the communication socket.
   */

   public void end ()
   {
      try
      { listeningSocket.close ();
      }
      catch (IOException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - não foi possível fechar o socket de escuta!");
        e.printStackTrace ();
        System.exit (1);
      }
   }

  /**
   *  Listening process.
   *  Criation of a communication channel toa  pending request.
   *  Instatiation of a communication socket and it's association to a client.
   *  Opening of the input and output socket streams.
   *
   *    @return communication channel
   */

   public ServerCom accept() throws SocketTimeoutException
   {
      ServerCom scon;                                      // canal de comunicação

      scon = new ServerCom(serverPortNumb, listeningSocket);
      try
      {
          listeningSocket.setSoTimeout(5000);
          scon.commSocket = listeningSocket.accept();
      }
      catch (SocketTimeoutException e){
            throw e;
        }
      catch (SocketException e)
      {
          GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - foi fechado o socket de escuta durante o processo de escuta!");
        e.printStackTrace ();
        System.exit (1);
      }
      catch (IOException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - não foi possível abrir um canal de comunicação para um pedido pendente!");
        e.printStackTrace ();
        System.exit (1);
      }

      try
      { scon.in = new ObjectInputStream (scon.commSocket.getInputStream ());
      }
      catch (IOException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - não foi possível abrir o canal de entrada do socket!");
        e.printStackTrace ();
        System.exit (1);
      }

      try
      { scon.out = new ObjectOutputStream (scon.commSocket.getOutputStream ());
      }
      catch (IOException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - não foi possível abrir o canal de saída do socket!");
        e.printStackTrace ();
        System.exit (1);
      }

      return scon;
   }

  /**
   *  Closing of the communication channel.
   *  Closing of the input and output socket streams.
   *  Closing of the communication socket.
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
   *  Reading of an object from the communication channel.
   *
   *    @return Read object
   */

   public Object readObject ()
   {
      Object fromClient = null;                            // objecto

      try
      { fromClient = in.readObject ();
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

      return fromClient;
   }

  /**
   *  Writing of an object in the communication channel
   *
   *    @param toClient object being written
   */

   public void writeObject (Object toClient)
   {
      try
      { out.writeObject (toClient);
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
