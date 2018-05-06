package stableserver.Stubs;

import stableserver.Communication.Message;
import stableserver.Communication.MessageType;

import stableserver.Auxiliar.Configurations;

public class PaddockStub {

    private ClientCom connectServer() {
        ClientCom connection = new ClientCom(Configurations.HOST, Configurations.PD_PORT);
        while (!connection.open()) {
            try {
                Thread.sleep(Configurations.SLEEP_TIME);
            } catch (InterruptedException ie) {}
        }
        return connection;
    }

    public void proceedToPaddock(int hId, int hAgl){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.PADDOCK_PROCEED_TO_PADDOCK, hId, hAgl);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    public boolean lastArrivedToPaddock(int hId){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.PADDOCK_LAST_ARRIVED_TO_PADDOCK, hId);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();

        connection.close();

        return messageReceived.getLastToPaddock();
    }

    public int goCheckHorses(int specId){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.PADDOCK_GO_CHECK_HORSES, specId);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();

        connection.close();

        return messageReceived.getHorseToBet();
    }

    public boolean lastCheckHorses(int specId){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.PADDOCK_LAST_CHECK_HORSES, specId);
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
