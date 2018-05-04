package stableserver.Communication;

import java.io.*;

/**
 *
 * Defines the exchanged messages between Stable Server and clients.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1114;

    private MessageType msgType;
    private int raceNumber;

    /*
    *
    *  @param type Enumerate indicating the type of the message
    */
    public Message(MessageType type){
        msgType = type;
    }

    /*
    *
    *  @param type Enumerate indicating the type of the message
    *  @param raceNumberParam Number of the actual race
    */
    public Message(MessageType type, int raceNumberParam){
        msgType = type;
        raceNumber=raceNumberParam;
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
     *  @return Race Number
     */
    public int getRaceNumber(){
        return raceNumber;
    }
}
