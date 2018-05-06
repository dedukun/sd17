package racetrackserver.Other;
import racetrackserver.Entities.RaceTrack;
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
            case RACE_TRACK_START_THE_RACE:
                rt.startTheRace();
                reply = new Message(MessageType.OK);
                break;

            case RACE_TRACK_PROCEED_TO_START_LINE:
                rt.proceedToStartLine();
                reply = new Message(MessageType.OK);
                break;

            case RACE_TRACK_MAKE_A_MOVE:
                boolean move = rt.makeAMove();
                reply = new Message(MessageType.RACE_TRACK_REPLY_MAKE_A_MOVE,move);
                break;

            case RACE_TRACK_HAS_RACE_FINISHED:
                boolean finished = rt.hasRaceFinished();
                reply = new Message(MessageType.RACE_TRACK_REPLY_HAS_RACE_FINISHED,finished);
                break;

            case RACE_TRACK_GET_RESULTS:
                int[] results = rt.getResults();
                reply = new Message(MessageType.RACE_TRACK_REPLY_GET_RESULTS,results);
                break;

            default:
                break;
        }
        return reply;
    }
}
