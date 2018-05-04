package horsejockeyclient.Communication;

import horsejockeyclient.Other.HorseJockeyStates;
import java.io.*;

/**
 *
 * Defines the exchanged messages between Spectator Client and servers
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1107;

    private MessageType msgType;
    private HorseJockeyStates hjstate;

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
    *  @param hjstateParam Estado do horsejockey
    */
    public Message(MessageType type, HorseJockeyStates hjstateParam){
        msgType = type;
        hjstate = hjstateParam;
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
    public HorseJockeyStates getHorseJockeyState(){
        return hjstate;
    }
}
