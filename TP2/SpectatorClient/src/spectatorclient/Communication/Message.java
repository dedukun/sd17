package spectatorclient.Communication;

import java.io.*;
import spectatorclient.Other.SpectatorStates;

/**
 *
 * Defines the exchanged messages between Spectator Client and servers
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1107;

    private MessageType msgType;
    private double money;
    private SpectatorStates sstate;

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
    *  @param moneyParam Spectator funds
    */
    public Message(MessageType type, double moneyParam){
        msgType = type;
        money = moneyParam;
    }

    /*
    *
    *  @param type Enumerate indicating the type of the message
    *  @param sstateParam Estado do espectador
    */
    public Message(MessageType type, SpectatorStates sstateParam){
        msgType = type;
        sstate = sstateParam;
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
     *
     */
    public double getMoney(){
        return money;
    }

    /**
     *  @return
     */
    public SpectatorStates getSpectatorState(){
        return sstate;
    }
}
