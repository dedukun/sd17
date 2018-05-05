package paddockserver.Communication;

import java.io.*;

/**
 *
 * Defines the exchanged messages between Paddock Server and clients.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1038;

    private MessageType.Paddock msgType;
    private boolean last;
    private int horseToBet;

    /*
    *
    *  @param type Enumerate indicating the type of the message
    */
    public Message(MessageType.Paddock type){
        msgType = type;
    }

    /*
    *
    *  @param type Enumerate indicating the type of the message
    *  @param boolean last
    */
    public Message(MessageType.Paddock type, boolean lastParam){
        msgType = type;
        last = lastParam;
    }
    
    /*
    *
    *  @param type Enumerate indicating the type of the message
    */
    public Message(MessageType.Paddock type, int horseToBetParam){
        msgType = type;
        horseToBet = horseToBetParam;
    }
    /**
     * Returns the enumerate indicating the type of the message.
     *
     *   @return Enumerate indicating the type
     */
    public MessageType.Paddock getMessageType(){
        return msgType;
    }
}
