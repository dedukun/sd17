package Main;
import Auxiliar.SimulPar;
import Communication.MessageType;
import Communication.MessageException;
import Communication.Message;

public class APS {

    private final Stable st;
    private int totalEntities = 1 + (SimulPar.K * SimulPar.C);


    public APS(){
        st = new Stable();
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
            case STABLE_PROCEED_TO_STABLE:
                st.proceedToStable(msg.getHorseId(), msg.getRaceNumber(), msg.getHorseAgl());
                reply = new Message(MessageType.OK);
                break;

            case STABLE_SUMMON_HORSES_TO_PADDOCK:
                double[] horse = st.summonHorsesToPaddock(msg.getRaceNumber());
                reply = new Message(MessageType.STABLE_REPLY_SUMMON_HORSES_TO_PADDOCK, horse);
                break;

            case STABLE_ENTERTAIN_THE_GUESTS:
                st.entertainTheGuests();
                reply = new Message(MessageType.OK);
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
            st.shutdownGenRepo();
            return false;
        }
        return true;
    }
}
