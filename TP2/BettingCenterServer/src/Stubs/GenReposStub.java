package Stubs;

import Communication.ClientCom;
import Auxiliar.BrokerStates;
import Auxiliar.HorseJockeyStates;
import Auxiliar.SpectatorStates;

import Communication.Message;
import Communication.MessageType;

import Auxiliar.Configurations;

/**
 * General Repository Stub
 */
public class GenReposStub {

    private ClientCom connectServer() {
        ClientCom connection = new ClientCom(Configurations.GR_HOST, Configurations.GR_PORT);
        while (!connection.open()) {
            try {
                Thread.sleep(Configurations.SLEEP_TIME);
            } catch (InterruptedException ie) {}
        }
        return connection;
    }

    /**
     * Send and receive the message's reply implied for the initial log.
     */
    public void initLog(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GENERAL_REPO_INIT_LOG);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    /**
     * Send and receive the message's reply implied for the update log.
     */
    public void updateLog(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GENERAL_REPO_UPDATE_LOG);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    /**
     * Send and receive the message's reply implied for spectator's bet.
     * 
     *   @param specId Spectator's Id
     *   @param horseId Horse's Id
     */
    public void setBetS(int specId, int horseId){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GENERAL_REPO_SET_BET_S, specId, horseId);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    /**
     * Send and receive the message's reply implied for spectator's bet ammount.
     * 
     *   @param specId Spectator's Id
     *   @param betamount Bet Ammount
     */
    public void setBetA(int specId, int betamount){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GENERAL_REPO_SET_BET_A, specId, betamount);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    /**
     * Send and receive the message's reply implied for the horses odds of winning.
     * 
     *   @param horseId Horse's Id
     *   @param odd Horse's odds of winning
     */
    public void setOdds(int horseId, double odd){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GENERAL_REPO_SET_ODDS, horseId, odd);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    /**
     * Send and receive the message's reply implied for the horses iteration.
     * 
     *   @param horseId Horse id
     */
    public void setHorseIteration(int horseId){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GENERAL_REPO_SET_HORSE_ITERATION, horseId);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    /**
     * Send and receive the message's reply implied for the horses position in the race.
     * 
     *   @param horseId Horse Id
     *   @param pos Horse's position
     */
    public void setHorsePosition(int horseId, int pos){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GENERAL_REPO_SET_HORSE_POSITION, horseId, pos);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    /**
     * Send and receive the message's reply implied for the horses end of race message.
     * 
     *   @param horseId Horse id
     *   @param place Horses final place in race
     */
    public void setHorseEnd(int horseId, int place){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GENERAL_REPO_SET_HORSE_END, horseId, place);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    /**
     * Send and receive the message's reply implied for the set of the track size.
     * 
     *   @param size Track's size 
     */
    public void setTrackSize(int size){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GENERAL_REPO_SET_TRACK_SIZE, size);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    /**
     * Send and receive the message's reply implied for the set of the race number.
     * 
     *   @param num Race number
     */
    public void setRaceNumber(int num){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GENERAL_REPO_SET_RACE_NUMBER, num);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

     /**
     * Send and receive the message's reply implied for the set of the broker state.
     * 
     *   @param state Broker state
     */
    public void setBrokerState(BrokerStates state){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GENERAL_REPO_SET_BROKER_STATE, state);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

     /**
     * Send and receive the message's reply implied for the set of the broker state.
     * 
     *   @param specId Spectator identifier
     *   @param state Spectator state
     */
    public void setSpectatorState(int specId, SpectatorStates state){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GENERAL_REPO_SET_SPECTATOR_STATE, specId, state);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    /**
     * Send and receive the message's reply implied for the set of the broker state.
     * 
     *   @param specId Spectator Id
     *   @param funds Spectator wallet
     */
    public void setSpectatorMoney(int specId, int funds){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GENERAL_REPO_SET_SPECTATOR_MONEY, specId, funds);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    /**
     * Send and receive the message's reply implied for the set of the horse state.
     * 
     *   @param horseId Horse identifier
     *   @param state Spectator state
     */
    public void setHorseState(int horseId, HorseJockeyStates state){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GENERAL_REPO_SET_HORSE_STATE, horseId, state);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }

    /**
     * Send and receive the message's reply implied for the set of the horse agility.
     * 
     *   @param horseId Horse identifier
     *   @param horseAgl Horse agility
     */
    public void setHorseAgility(int horseId,int horseAgl){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.GENERAL_REPO_SET_HORSE_AGILITY, horseId, horseAgl);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }
    
    /**
     * Send and receive the message's reply implied for the shutting down of the server.
     */
    public void endServer(){
        ClientCom connection = connectServer();

        Message messageToSend = new Message(MessageType.END);
        connection.writeObject(messageToSend);

        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getMessageType() != MessageType.OK) {}

        connection.close();
    }
}
