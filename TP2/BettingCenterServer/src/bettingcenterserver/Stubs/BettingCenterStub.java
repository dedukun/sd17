package bettingcenterserver.Stubs;


public class BettingCenterStub {

    private ClientCom connectServer() {
        ClientCom connection = new ClientCom(BETTING_CENTRE_HOST, BETTING_CENTRE_PORT);
        while (!connection.open()) {
            try {
                Thread.sleep(BETTING_CENTRE_TIME_TO_SLEEP);
            } catch (InterruptedException ie) {}
        }
        return connection;
    }

    public void setHorsesWinningChances(double[] horseChances){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.BettingCenter.SET_HORSES_WINNING_CHANCES, horseChances);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }

    public boolean acceptedAllBets(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.BettingCenter.ACCEPTED_ALL_BETS);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();

        connection.close();

        return messageReceived
    }

    public void acceptTheBet(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.BettingCenter.ACCEPT_THE_BET);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }

    public void placeABet(int horseId){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.BettingCenter.PLACE_A_BET);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }

    public boolean areThereAnyWinners(int[] winningHorses){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.BettingCenter.ARE_THERE_ANY_WINNERS);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();

        connection.close();

        return messageReceived
    }

    public boolean honouredAllTheBets(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.BettingCenter.HONOURED_ALL_THE_BETS);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();

        connection.close();

        return messageReceived
    }

    public void honourTheBet(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.BettingCenter.HONOUR_THE_BET);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }

    public void goCollectTheGains(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.BettingCenter.GO_COLLECT_THE_GAINS);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }
}
