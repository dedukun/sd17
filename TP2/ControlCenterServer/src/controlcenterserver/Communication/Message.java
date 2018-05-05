package controlcenterserver.Communication;

import java.io.*;

/**
 *
 * Defines the exchanged messages between Control Center Server and clients.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1032;

    private MessageType.ControlCenter msgType;
    private int[] winners;
    private int hjid;
    private boolean wait;
    private boolean winner;

    /*
    *
    *  @param type Enumerate indicating the type of the message
    */
    public Message(MessageType.ControlCenter  type){
        msgType = type;
    }

    /*
    *
    *  @param type Enumerate indicating the type of the message
    *  @param winnersParam number of winning horses
    */
    public Message(MessageType.ControlCenter  type, int[] winnersParam){
        msgType = type;
        winners = winnersParam;
    }

    /*
    *
    *  @param type Enumerate indicating the type of the message
    *  @param hjidParam ID of the desired horse/jockey pair
    */
    public Message(MessageType.ControlCenter  type, int hjidParam){
        msgType = type;
        hjid=hjidParam;
    }
    
    /*
    *
    *  @param type Enumerate indicating the type of the message
    *  @param param boolean winner/wait
    */
    public Message(MessageType.ControlCenter  type, boolean param){
        msgType = type;
        
        switch(type){
            case HAVE_I_WON:
                winner = param;
                break;
            case WAIT_FOR_NEXT_RACE:
                wait = param;
                break;
        }
    }

    /**
     * Returns the enumerate indicating the type of the message.
     *
     *   @return Enumerate indicating the type
     */
    public MessageType.ControlCenter  getMessageType(){
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
