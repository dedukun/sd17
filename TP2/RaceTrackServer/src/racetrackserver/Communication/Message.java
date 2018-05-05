package racetrackserver.Communication;

import java.io.*;

/**
 *
 * Defines the exchanged messages between Race Track Server and clients.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1040;

    private MessageType.RaceTrack msgType;
    private boolean move;
    private boolean finished;
    private int[] param;

    /*
    *
    *@param type Nome da função
    */
    public Message(MessageType.RaceTrack type){
        msgType = type;
    }
    
    /*
    *
    *@param type Nome da função
    *@param param Boolean move/finished
    */
    public Message(MessageType.RaceTrack type, boolean param){
        msgType = type;
        
        switch(type){
            case MAKE_A_MOVE:
                move = param;
                break;
            case HAS_RACE_FINISHED:
                finished = param;
                break;
        }
    }
    
    /*
    *
    *@param type Nome da função
    *@param resultsParam results 
    */
    public Message(MessageType.RaceTrack type, int[] resultsParam){
        msgType = type;
        param = resultsParam;
    }

    /**
     * Returns the enumerate indicating the type of the message.
     *
     *   @return Enumerate indicating the type
     */
    public MessageType.RaceTrack getMessageType(){
        return msgType;
    }
}
