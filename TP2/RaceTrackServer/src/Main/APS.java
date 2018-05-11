package Main;
import Auxiliar.SimulPar;
import Communication.MessageType;
import Communication.MessageException;
import Communication.Message;

public class APS {

    private final RaceTrack rt;
    private int totalEntities = 1 + (SimulPar.K * SimulPar.C);


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
                System.out.println("END IN "+totalEntities);
                totalEntities--;
                reply = new Message(MessageType.OK);
                break;

            default:
                break;
        }
        return reply;
    }

    /**
     * @return
     */
    public boolean isAlive(){
        if(totalEntities==0){
            rt.shutdownGenRepo();
            return false;
        }
        return true;
    }
}
