package bettingcenterserver.Stubs;

public class RaceTrackStub {

    private ClientCom connectServer() {
        ClientCom connection = new ClientCom(BETTING_CENTRE_HOST, BETTING_CENTRE_PORT);
        while (!connection.open()) {
            try {
                Thread.sleep(BETTING_CENTRE_TIME_TO_SLEEP);
            } catch (InterruptedException ie) {}
        }
        return connection;
    }

    public void startTheRace(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.RaceTrack.START_THE_RACE);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }

    public void proceedToStartLine(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.RaceTrack.PROCEED_TO_START_LINE);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }

    public boolean makeAMove(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.RaceTrack.MAKE_A_MOVE);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();

        connection.close();

        return messageReceived
    }

    public boolean hasRaceFinished(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.RaceTrack.HAS_RACE_FINISHED);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();

        connection.close();

        return messageReceived
    }

    public int[] getResults(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.RaceTrack.GET_RESULTS);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();

        connection.close();

        return messageReceived
    }
}
