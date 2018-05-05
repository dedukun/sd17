package bettingcenterserver.Stubs;

public class StableStub {

    private ClientCom connectServer() {
        ClientCom connection = new ClientCom(BETTING_CENTRE_HOST, BETTING_CENTRE_PORT);
        while (!connection.open()) {
            try {
                Thread.sleep(BETTING_CENTRE_TIME_TO_SLEEP);
            } catch (InterruptedException ie) {}
        }
        return connection;
    }

    public void proceedToStable(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.ControlCenter.PROCEED_TO_STABLE);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }

    public double[] summonHorsesToPaddock(int raceNumber){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.ControlCenter.SUMMON_HORSES_TO_PADDOCK);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();

        connection.close();

        return messageReceived
    }

    public void entertainTheGuests(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.ControlCenter.ENTERTAIN_THE_GUESTS);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }
}
