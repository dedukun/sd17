package genreposserver.Communication;

import genreposserver.Other.BrokerStates;
import genreposserver.Other.HorseJockeyStates;
import genreposserver.Other.SpectatorStates;
import java.io.*;

/**
 *
 * Defines the exchanged messages between General Repository Server and clients.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1032;

    private MessageType msgType;
    private BrokerStates bstate;
    private HorseJockeyStates hjstate;
    private SpectatorStates sstate;
    private int param1;
    private int param2;
    private int horseId;
    private int pecId;

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
    *  @param param1 Parameter of fucntion(num/size)
    */
    public Message(MessageType type, int param1){
        msgType = type;
        this.param1 = param1;
    }

    /*
    *
    *  @param type Enumerate indicating the type of the message
    *  @param param1 First parameter of fucntion(specId/horseId/specId)
    *  @param param2 Second parameter of function (betamount/horseId/horseAgl/place/pos/funds)
    */
    public Message(MessageType type, int param1, int param2){
        msgType = type;
        this.param1=param1;
        this.param2=param2;
    }

    /*
    *
    *  @param type Enumerate indicating the type of the message
    *  @param bstateParam Broker state
    */
    public Message(MessageType type, BrokerStates bstateParam){
        msgType = type;
        bstate = bstateParam;
    }

    /*
    *
    *  @param type Enumerate indicating the type of the message
    *  @param horseidParam Horse ID
    *  @param bstateParam Broker state
    */
    public Message(MessageType type, int horseidParam, HorseJockeyStates hjstateParam){
        msgType = type;
        horseId = horseidParam;
        hjstate = hjstateParam;
    }

    /*
    *
    *  @param type Enumerate indicating the type of the message
    *  @param specidParam Horse ID
    *  @param sstateParam Specator state
    */
    public Message(MessageType type, int specidParam, SpectatorStates sstateParam){
        msgType = type;
        specId = specidParam;
        sstate = sstateParam;
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
