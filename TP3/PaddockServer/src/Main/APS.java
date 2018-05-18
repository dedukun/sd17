package Main;

import Communication.ServerCom;
import Communication.Message;
import Communication.MessageException;

public class APS extends Thread{

    private ServerCom connection;
    private Interface server;

    public APS(ServerCom connection, Interface server) {
        this.connection = connection;
        this.server = server;
    }

    @Override
    public void run() {
        Message reply = null;
        Message message = (Message) connection.readObject();

        try {
            reply = server.compute(message);
        } catch(MessageException e) {}

        connection.writeObject(reply);
        connection.close();
    }
}
