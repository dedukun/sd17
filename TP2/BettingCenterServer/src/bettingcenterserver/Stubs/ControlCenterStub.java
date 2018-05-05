package bettingcenterserver.Stubs;

public class ControlCenterStub {

    private ClientCom connectServer() {
        ClientCom connection = new ClientCom(BETTING_CENTRE_HOST, BETTING_CENTRE_PORT);
        while (!connection.open()) {
            try {
                Thread.sleep(BETTING_CENTRE_TIME_TO_SLEEP);
            } catch (InterruptedException ie) {}
        }
        return connection;
    }

    public boolean waitForNextRace(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.ControlCenter.WAIT_FOR_NEXT_RACE);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();

        connection.close();

        return messageReceived
    }

    public void  summonHorsesToPaddock(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.ControlCenter.SUMMON_HORSES_TO_PADDOCK);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }

    public void unblockGoCheckHorses(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.ControlCenter.UNBLOCK_GO_CHECK_HORSES);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }

    public void unblockProceedToPaddock(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.ControlCenter.UNBLOCK_PROCEED_TO_PADDOCK);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }

    public void startTheRace(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.ControlCenter.START_THE_RACE);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }

    public void unblockMakeAMove(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.ControlCenter.UNBLOCK_MAKE_A_MOVE);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }

    public void goWatchTheRace(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.ControlCenter.GO_WATCH_THE_RACE);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }

    public void reportResults(int[] winners){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.ControlCenter.REPORT_RESULTS);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();

        connection.close();
    }

    public boolean haveIWon(int hjid){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.ControlCenter.HAVE_I_WON);
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

    public void relaxABit(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.ControlCenter.RELAX_A_BIT);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }
}
