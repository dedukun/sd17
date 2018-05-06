package bettingcenterserver.Stubs;

import bettingcenterserver.Communication.Message;
import bettingcenterserver.Communication.MessageType;

import bettingcenterserver.Other.Configurations;

public class BettingCenterStub {

    private ClientCom connectServer() {
        ClientCom connection = new ClientCom(Configurations.HOST, Configurations.PORT);
        while (!connection.open()) {
            try {
                Thread.sleep(Configurations.SLEEP_TIME);
            } catch (InterruptedException ie) {}
        }
        return connection;
    }

    public void setHorsesWinningChances(double[] horseChances){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.BETTING_CENTER_SET_HORSES_WINNING_CHANCES, horseChances);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    public boolean acceptedAllBets(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.BETTING_CENTER_ACCEPTED_ALL_BETS);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();

        connection.close();

        return messageReceived.getAllBetsAccepted();
    }

    public void acceptTheBet(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.BETTING_CENTER_ACCEPT_THE_BET);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    public void placeABet(int horseId){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.BETTING_CENTER_PLACE_A_BET);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    public boolean areThereAnyWinners(int[] winningHorses){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.BETTING_CENTER_ARE_THERE_ANY_WINNERS);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();

        connection.close();

        return messageReceived.getAnyWinners();
    }

    public boolean honouredAllTheBets(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.BETTING_CENTER_HONOURED_ALL_THE_BETS);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();

        connection.close();

        return messageReceived.getAllBetsHonoured();
    }

    public void honourTheBet(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.BETTING_CENTER_HONOUR_THE_BET);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    public void goCollectTheGains(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.BETTING_CENTER_GO_COLLECT_THE_GAINS);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }
}
