package paddockserver.Communication;

import java.io.*;

/**
 *
 * Defines the exchanged messages between Paddock Server and clients.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1038;

    private MessageType msgType;

    /*
    *
    *  @param type Enumerate indicating the type of the message
    */
    public Message(MessageType type){
        msgType = type;
    }

    /**
     * Returns the enumerate indicating the type of the message.
     *
     *   @return Enumerate indicating the type
     */
    public MessageType getMessageType(){
        return msgType;
    }
}
