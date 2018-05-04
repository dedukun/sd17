package controlcenterserver.Communication;

import java.io.*;

/**
 *
 * Defines the exchanged messages between Control Center Server and clients.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1032;

    private MessageType msgType;
    private int[] winners;
    private int hjid;

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
    *  @param winnersParam number of winning horses
    */
    public Message(MessageType type, int[] winnersParam){
        msgType = type;
        winners = winnersParam;
    }

    /*
    *
    *  @param type Enumerate indicating the type of the message
    *  @param hjidParam ID of the desired horse/jockey pair
    */
    public Message(MessageType type, int hjidParam){
        msgType = type;
        hjid=hjidParam;
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
    public int[] getWinners(){
        return winners;
    }

    /**
     *
     */
    public int getHorseJockeyId(){
        return hjid;
    }
}
