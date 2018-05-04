package racetrackserver.Communication;

import java.io.*;

/**
 *
 * Defines the exchanged messages between Race Track Server and clients.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1040;

    private MessageType msgType;

    /*
    *
    *@param type Nome da função
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
