package stableserver.Other;
import stableserver.Communication.*;

public class APS {

    private final Stable st;

    public APS(){
        st = new Stable();
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
            case PROCEED_TO_STABLE:
                st.proceedToStable();
                reply = new Message(MessageType.Stable.OK);
                break;

            case SUMMON_HORSES_TO_PADDOCK:
                double[] horse = st.summonHorsesToPaddock(msg.getRaceNumber());
                reply = new Message(MessageType.Stable.REPLY_SUMMON_HORSES_TO_PADDOCK, horse);
                break;

            case ENTERTAIN_THE_GUESTS:
                st.entertainTheGuests();
                reply = new Message(MessageType.Stable.OK);
                break;

            default:
                break;
        }
        return reply;
    }
}
