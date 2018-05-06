package bettingcenterserver.Main;

import bettingcenterserver.Communication.Message;
import bettingcenterserver.Communication.MessageException;

public class ServiceProvider extends Thread{

    private ServerCom connection;
    private APS server;

    public ServiceProvider(ServerCom connection, APS server) {
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
