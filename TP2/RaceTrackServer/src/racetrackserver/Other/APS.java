package racetrackserver.Other;
import racetrackserver.Communication.*;

public class APS {

    private final RaceTrack rt;

    public APS(){
        rt = new RaceTrack();
    }

    /**
    * Processes the message and returns a reply.
    *
    *   @param msg Mesage to be processed
    *
    */
    public Message compute(Message msg) throws Exception{
        Message reply = null;
        switch(msg.getMessageType()){
            case START_THE_RACE:
                rt.startTheRace();
                reply = new Message(MessageType.RaceTrack.OK);
                break;

            case PROCEED_TO_START_LINE:
                rt.proceedToStartLine();
                reply = new Message(MessageType.RaceTrack.OK);
                break;

            case MAKE_A_MOVE:
                boolean move = rt.makeAMove();
                reply = new Message(MessageType.RaceTrack.REPLY_MAKE_A_MOVE,move);
                break;

            case HAS_RACE_FINISHED:
                boolean finished = rt.hasRaceFinished();
                reply = new Message(MessageType.RaceTrack.REPLY_HAS_RACE_FINISHED,finished);
                break;

            case GET_RESULTS:
                int[] results = rt.getResults();
                reply = new Message(MessageType.RaceTrack.REPLY_GET_RESULTS,results);
                break;

            default:
                break;
        }
        return reply;
    }
}
