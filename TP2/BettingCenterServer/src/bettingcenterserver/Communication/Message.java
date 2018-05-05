package bettingcenterserver.Communication;

import java.io.*;

/**
 *
 * Defines the exchanged messages between Betting Center Server and clients.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 947;

    private MessageType.BettingCenter msgType;
    private int[] winningHorses;
    private int horseId;
    private double[] horseChances;
    private boolean accepted;
    private boolean winners;
    private boolean honoured;
    
    /**
    *
    *  @param type Enumerate indicating the type of the message
    */
    public Message(MessageType.BettingCenter type){
        msgType = type;
    }

    /**
    *
    *  @param type Enumerate indicating the type of the message
    *  @param winningHorsesParam number of winning horses
    */
    public Message(MessageType.BettingCenter type, int[] winningHorsesParam){
        msgType = type;
        winningHorses = winningHorsesParam;
    }

    /**
    *
    *  @param type Enumerate indicating the type of the message
    *  @param horseIdParam Id of the desired horse
    */
    public Message(MessageType.BettingCenter type, int horseIdParam){
        msgType = type;
        horseId = horseIdParam;
    }

    /**
    *
    *  @param type Enumerate indicating the type of the message
    *  @param horseChancesParam Horses winning chances
    */
    public Message(MessageType.BettingCenter type, double[] horseChancesParam){
        msgType = type;
        horseChances = horseChancesParam;
    }
    
    /**
    *
    *  @param type Enumerate indicating the type of the message
    *  @param param Boolean accepted/winners/honoured
    */
    public Message(MessageType.BettingCenter type, boolean param){
        msgType = type;
        switch(type){
            case REPLY_ACCEPTED_ALL_BETS :
                accepted = param;
                break;
            case REPLY_ARE_THERE_ANY_WINNERS:
                winners = param;
                break;
            case REPLY_HONOURED_ALL_THE_BETS:
                honoured = param;
                break;
        }
    }

    /**
     * Returns the enumerate indicating the type of the message.
     *
     *   @return Enumerate indicating the type
     */
    public MessageType.BettingCenter getMessageType(){
        return msgType;
    }

    /**
     *
     */
    public int[] getWinningHorses(){
        return winningHorses;
    }

    /**
     *
     */
    public int getHorseId(){
        return horseId;
    }

    /**
     *
     */
    public double[] getHorsesChances(){
        return horseChances;
    }
}
