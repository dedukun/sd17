package bettingcenterserver.Stubs;

public class PaddockStub {

    private ClientCom connectServer() {
        ClientCom connection = new ClientCom(BETTING_CENTRE_HOST, BETTING_CENTRE_PORT);
        while (!connection.open()) {
            try {
                Thread.sleep(BETTING_CENTRE_TIME_TO_SLEEP);
            } catch (InterruptedException ie) {}
        }
        return connection;
    }

    public void proceedToPaddock(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.Paddock.PROCEED_TO_PADDOCK);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }

    public boolean lastArrivedToPaddock(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.Paddock.LAST_ARRIVED_TO_PADDOCK);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();

        connection.close();

        return messageReceived
    }

    public int goCheckHorses(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.Paddock.GO_CHECK_HORSES);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();

        connection.close();

        return messageReceived
    }

    public boolean lastCheckHorses(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.Paddock.LAST_CHECK_HORSES);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();

        connection.close();

        return messageReceived
    }

    public void unblockGoCheckHorses(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.Paddock.UNBLOCK_GO_CHECK_HORSES);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }
}
