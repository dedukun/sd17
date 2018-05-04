package bettingcenterserver.Communication;

import java.io.*;

/**
 *
 * Defines the exchanged messages between Betting Center Server and clients.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 947;

    private MessageType msgType;
    private int[] winningHorses;
    private int horseId;
    private double[] horseChances;

    /**
    *
    *  @param type Enumerate indicating the type of the message
    */
    public Message(MessageType type){
        msgType = type;
    }

    /**
    *
    *  @param type Enumerate indicating the type of the message
    *  @param winningHorsesParam number of winning horses
    */
    public Message(MessageType type, int[] winningHorsesParam){
        msgType = type;
        winningHorses = winningHorsesParam;
    }

    /**
    *
    *  @param type Enumerate indicating the type of the message
    *  @param horseIdParam Id of the desired horse
    */
    public Message(MessageType type, int horseIdParam){
        msgType = type;
        horseId = horseIdParam;
    }

    /**
    *
    *  @param type Enumerate indicating the type of the message
    *  @param horseChancesParam Horses winning chances
    */
    public Message(MessageType type, double[] horseChancesParam){
        msgType = type;
        horseChances = horseChancesParam;
    }

    /**
     * Returns the enumerate indicating the type of the message.
     *
     *   @return Enumerate indicating the type
     */
    public MessageType getMessageType(){
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
