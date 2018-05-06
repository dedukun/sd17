package paddockserver.Stubs;

import paddockserver.Auxiliar.BrokerStates;
import paddockserver.Auxiliar.HorseJockeyStates;
import paddockserver.Auxiliar.SpectatorStates;

import paddockserver.Communication.Message;
import paddockserver.Communication.MessageType;

import paddockserver.Auxiliar.Configurations;

public class GenReposStub {

    private ClientCom connectServer() {
        ClientCom connection = new ClientCom(Configurations.HOST, Configurations.PORT);
        while (!connection.open()) {
            try {
                Thread.sleep(Configurations.SLEEP_TIME);
            } catch (InterruptedException ie) {}
        }
        return connection;
    }

    public void initLog(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GENERAL_REPO_INIT_LOG);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    public void updateLog(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GENERAL_REPO_UPDATE_LOG);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    public void setBetS(int specId, int horseId){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GENERAL_REPO_SET_BET_S, specId, horseId);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    public void setBetA(int specId, int betamount){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GENERAL_REPO_SET_BET_A, specId, betamount);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    public void setOdds(int horseId, double odd){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GENERAL_REPO_SET_ODDS, horseId, odd);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    public void setHorseIteration(int horseId){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GENERAL_REPO_SET_HORSE_ITERATION, horseId);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    public void setHorsePosition(int horseId, int pos){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GENERAL_REPO_SET_HORSE_POSITION, horseId, pos);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    public void setHorseEnd(int horseId, int place){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GENERAL_REPO_SET_HORSE_END, horseId, place);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    public void setTrackSize(int size){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GENERAL_REPO_SET_TRACK_SIZE, size);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    public void setRaceNumber(int num){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GENERAL_REPO_SET_RACE_NUMBER, num);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    public void setBrokerState(BrokerStates state){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GENERAL_REPO_SET_BROKER_STATE, state);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    public void setSpectatorState(int specId, SpectatorStates state){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GENERAL_REPO_SET_SPECTATOR_STATE, specId, state);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    public void setSpectatorMoney(int specId, int funds){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GENERAL_REPO_SET_SPECTATOR_MONEY, specId, funds);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    public void setHorseState(int horseId, HorseJockeyStates state){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GENERAL_REPO_SET_HORSE_STATE, horseId, state);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    public void setHorseAgility(int horseId,int horseAgl){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GENERAL_REPO_SET_HORSE_AGILITY, horseId, horseAgl);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }
}
