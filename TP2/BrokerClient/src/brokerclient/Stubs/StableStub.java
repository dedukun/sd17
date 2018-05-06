package brokerclient.Stubs;

import brokerclient.Communication.Message;
import brokerclient.Communication.MessageType;

import brokerclient.Auxiliar.Configurations;

public class StableStub {

    private ClientCom connectServer() {
        ClientCom connection = new ClientCom(Configurations.HOST, Configurations.ST_PORT);
        while (!connection.open()) {
            try {
                Thread.sleep(Configurations.SLEEP_TIME);
            } catch (InterruptedException ie) {}
        }
        return connection;
    }

    public void proceedToStable(int hId, int hRaceNumber, int horseAgl){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.STABLE_PROCEED_TO_STABLE, hId, hRaceNumber, horseAgl);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    public double[] summonHorsesToPaddock(int raceNumber){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.STABLE_SUMMON_HORSES_TO_PADDOCK, raceNumber);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();

        connection.close();

        return messageReceived.getHorsesChances();
    }

    public void entertainTheGuests(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.STABLE_ENTERTAIN_THE_GUESTS);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }
}
