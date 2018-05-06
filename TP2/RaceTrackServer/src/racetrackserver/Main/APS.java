package racetrackserver.Main;
import racetrackserver.Communication.*;

public class APS {

    private final RaceTrack rt;
    private boolean isAlive = true;

    public APS(){
        rt = new RaceTrack();
    }

    /**
    * Processes the message and returns a reply.
    *
    *   @param msg Mesage to be processed
    *
    */
    public Message compute(Message msg) throws MessageException{
        Message reply = null;
        switch(msg.getMessageType()){
            case RACE_TRACK_START_THE_RACE:
                rt.startTheRace();
                reply = new Message(MessageType.OK);
                break;

            case RACE_TRACK_PROCEED_TO_START_LINE:
                rt.proceedToStartLine(msg.getHorseId());
                reply = new Message(MessageType.OK);
                break;

            case RACE_TRACK_MAKE_A_MOVE:
                boolean move = rt.makeAMove(msg.getHorseId(), msg.getHorseAgl());
                reply = new Message(MessageType.RACE_TRACK_REPLY_MAKE_A_MOVE,move);
                break;

            case RACE_TRACK_HAS_RACE_FINISHED:
                boolean finished = rt.hasRaceFinished(msg.getHorseId());
                reply = new Message(MessageType.RACE_TRACK_REPLY_HAS_RACE_FINISHED,finished);
                break;

            case RACE_TRACK_GET_RESULTS:
                int[] results = rt.getResults();
                reply = new Message(MessageType.RACE_TRACK_REPLY_GET_RESULTS,results);
                break;
                
            case END:
                isAlive = false;
                break;
                
            default:
                break;
        }
        return reply;
    }
    
    /**
     * 
     */
    public boolean isAlive(){
        return isAlive;
    }
}
