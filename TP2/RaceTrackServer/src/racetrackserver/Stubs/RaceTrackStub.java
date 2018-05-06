package racetrackserver.Stubs;

import racetrackserver.Communication.Message;
import racetrackserver.Communication.MessageType;

import racetrackserver.Auxiliar.Configurations;

public class RaceTrackStub {

    private ClientCom connectServer() {
        ClientCom connection = new ClientCom(Configurations.HOST, Configurations.PORT);
        while (!connection.open()) {
            try {
                Thread.sleep(Configurations.SLEEP_TIME);
            } catch (InterruptedException ie) {}
        }
        return connection;
    }

    public void startTheRace(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.RACE_TRACK_START_THE_RACE);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    public void proceedToStartLine(int hId){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.RACE_TRACK_PROCEED_TO_START_LINE, hId);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    public boolean makeAMove(int hId, int hAgl){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.RACE_TRACK_MAKE_A_MOVE, hId, hAgl);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();

        connection.close();

        return messageReceived.getMakeMove();
    }

    public boolean hasRaceFinished(int hId){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.RACE_TRACK_HAS_RACE_FINISHED, hId);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();

        connection.close();

        return messageReceived.getRaceFinished();
    }

    public int[] getResults(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.RACE_TRACK_GET_RESULTS);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();

        connection.close();

        return messageReceived.getResults();
    }
}
