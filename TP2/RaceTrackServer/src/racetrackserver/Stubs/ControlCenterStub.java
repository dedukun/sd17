package racetrackserver.Stubs;

import racetrackserver.Communication.Message;
import racetrackserver.Communication.MessageType;

import racetrackserver.Auxiliar.Configurations;

public class ControlCenterStub {

    private ClientCom connectServer() {
        ClientCom connection = new ClientCom(Configurations.HOST, Configurations.CC_PORT);
        while (!connection.open()) {
            try {
                Thread.sleep(Configurations.SLEEP_TIME);
            } catch (InterruptedException ie) {}
        }
        return connection;
    }

    public boolean waitForNextRace(int specId){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.CONTROL_CENTER_WAIT_FOR_NEXT_RACE, specId);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();

        connection.close();

        return messageReceived.getWaitNextRace();
    }

    public void  summonHorsesToPaddock(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.CONTROL_CENTER_SUMMON_HORSES_TO_PADDOCK);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    public void unblockGoCheckHorses(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.CONTROL_CENTER_UNBLOCK_GO_CHECK_HORSES);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    public void unblockProceedToPaddock(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.CONTROL_CENTER_UNBLOCK_PROCEED_TO_PADDOCK);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    public void startTheRace(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.CONTROL_CENTER_START_THE_RACE);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    public void unblockMakeAMove(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.CONTROL_CENTER_UNBLOCK_MAKE_A_MOVE);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    public void goWatchTheRace(int specId){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.CONTROL_CENTER_GO_WATCH_THE_RACE, specId);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    public void reportResults(int[] winners){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.CONTROL_CENTER_REPORT_RESULTS);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();

        connection.close();
    }

    public boolean haveIWon(int hjid){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.CONTROL_CENTER_HAVE_I_WON);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();

        connection.close();

        return messageReceived.getHaveIWon();
    }

    public void entertainTheGuests(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.CONTROL_CENTER_ENTERTAIN_THE_GUESTS);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    public void relaxABit(int specId){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.CONTROL_CENTER_RELAX_A_BIT,specId);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }
}
