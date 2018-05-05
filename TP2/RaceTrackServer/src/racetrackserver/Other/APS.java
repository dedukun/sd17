package racetrackserver.Other;

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
        switch(msg.getType()){
            case RaceTrack.START_THE_RACE:
                rt.startTheRace();
                reply = new Message("Stuff");
                break;

            case RaceTrack.PROCEED_TO_START_LINE:
                rt.proceedToStartLine();
                reply = new Message("Stuff");
                break;

            case RaceTrack.MAKE_A_MOVE:
                boolean move = rt.makeAMove();
                reply = new Message(MessageType.RaceTrack.REPLY_MAKE_A_MOVE,move);
                break;

            case RaceTrack.HAS_RACE_FINISHED:
                boolean finished = rt.hasRaceFinished();
                reply = new Message(MessageType.RaceTrack.REPLY_HAS_RACE_FINISHED,finished);
                break;

            case RaceTrack.GET_RESULTS:
                int[] results = rt.getResults();
                reply = new Message(MessageType.RaceTrack.REPLY_GET_RESULTS,results);
                break;

            default:
                break;
        }
        return reply;
    }
}
