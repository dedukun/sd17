package bettingcenterserver.Stubs;

public class GenReposStub {

    private ClientCom connectServer() {
        ClientCom connection = new ClientCom(BETTING_CENTRE_HOST, BETTING_CENTRE_PORT);
        while (!connection.open()) {
            try {
                Thread.sleep(BETTING_CENTRE_TIME_TO_SLEEP);
            } catch (InterruptedException ie) {}
        }
        return connection;
    }

    public void initLog(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GeneralRepository.INIT_LOG);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }

    public void updateLog(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GeneralRepository.UPDATE_LOG);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }

    public void setBetS(int specId, int horseId){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GeneralRepository.SET_BET_S);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }

    public void setBetA(int specId, int betamount){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GeneralRepository.SET_BET_A);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }

    public void setOdds(int horseId, double odd){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GeneralRepository.SET_ODDS);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }

    public void setHorseIteration(int horseId){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GeneralRepository.SET_HORSE_ITERATION);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }

    public void setHorsePosition(int horseId, int pos){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GeneralRepository.SET_HORSE_POSITION);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }

    public void setHorseEnd(int horseId, int place){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GeneralRepository.SET_HORSE_END);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }

    public void setTrackSize(int size){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GeneralRepository.SET_TRACK_SIZE);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }

    public void setRaceNumber(int num){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GeneralRepository.SET_RACE_NUMBER);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }

    public void setBrokerState(BrokerStates state){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GeneralRepository.SET_BROKER_STATE);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }

    public void setSpectatorState(int specId,SpectatorsStates state){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GeneralRepository.SET_SPECTATOR_STATE);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }

    public void setSpectatorMoney(int specId, int funds){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GeneralRepository.SET_SPECTATOR_MONEY);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }

    public void setHorseState(int horseId,HorseJockeyStates state){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GeneralRepository.SET_HORSE_STATE);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }

    public void setHorseAgility(int horseId,int horseAgl){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GeneralRepository.SET_HORSE_AGILITY);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {}

        connection.close();
    }
}
