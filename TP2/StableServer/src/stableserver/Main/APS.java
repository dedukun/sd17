package stableserver.Main;
import stableserver.Communication.*;

public class APS {

    private final Stable st;
    private boolean isAlive = true;

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