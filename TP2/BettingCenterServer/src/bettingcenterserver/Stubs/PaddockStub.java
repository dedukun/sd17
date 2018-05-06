package bettingcenterserver.Stubs;

import bettingcenterserver.Communication.Message;
import bettingcenterserver.Communication.MessageType;

import bettingcenterserver.Other.Configurations;

public class PaddockStub {

    private ClientCom connectServer() {
        ClientCom connection = new ClientCom(Configurations.HOST, Configurations.PORT);
        while (!connection.open()) {
            try {
                Thread.sleep(Configurations.SLEEP_TIME);
            } catch (InterruptedException ie) {}
        }
        return connection;
    }

    public void proceedToPaddock(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.PADDOCK_PROCEED_TO_PADDOCK);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    public boolean lastArrivedToPaddock(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.PADDOCK_LAST_ARRIVED_TO_PADDOCK);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();

        connection.close();

        return messageReceived.getLastToPaddock();
    }

    public int goCheckHorses(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.PADDOCK_GO_CHECK_HORSES);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();

        connection.close();

        return messageReceived.getHorseToBet();
    }

    public boolean lastCheckHorses(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.PADDOCK_LAST_CHECK_HORSES);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();

        connection.close();

        return messageReceived.getLastCheckHorses();
    }

    public void unblockGoCheckHorses(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.PADDOCK_UNBLOCK_GO_CHECK_HORSES);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }
}
