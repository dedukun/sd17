package brokerclient.Communication;

import brokerclient.Other.BrokerStates;
import java.io.*;

/**
 *
 * Defines the exchanged messages between Spectator Client and servers
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1108;

    private MessageType msgType;
    private BrokerStates bstate;

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
    *  @param sstateParam Estado do espectador
    */
    public Message(MessageType type, BrokerStates bstateParam){
        msgType = type;
        bstate = bstateParam;
    }

    /**
     *Returns the enumerate indicating the type of the message.
     *
     *  @return Enumerate indicating the type
     */
    public MessageType getMessageType(){
        return msgType;
    }

    /**
     *  @return
     */
    public BrokerStates getBrokerState(){
        return bstate;
    }
}
