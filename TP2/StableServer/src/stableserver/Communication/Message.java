package stableserver.Communication;

import java.io.*;

/**
 *
 * Defines the exchanged messages between Stable Server and clients.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1114;

    private MessageType.Stable msgType;
    private int raceNumber;
    private double[] horse;

    /*
    *
    *  @param type Enumerate indicating the type of the message
    */
    public Message(MessageType.Stable type){
        msgType = type;
    }

    /*
    *
    *  @param type Enumerate indicating the type of the message
    *  @param raceNumberParam Number of the actual race
    */
    public Message(MessageType.Stable type, int raceNumberParam){
        msgType = type;
        raceNumber=raceNumberParam;
    }
    
    /*
    *
    *  @param type Enumerate indicating the type of the message
    *  @param raceNumberParam Number of the actual race
    */
    public Message(MessageType.Stable type, double[] horseParam){
        msgType = type;
        horse = horseParam;
    }

    /**
     * Returns the enumerate indicating the type of the message.
     *
     *   @return Enumerate indicating the type
     */
    public MessageType.Stable getMessageType(){
        return msgType;
    }

    /**
     *
     *  @return Race Number
     */
    public int getRaceNumber(){
        return raceNumber;
    }
}
