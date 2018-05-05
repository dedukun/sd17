package stableserver.Other;

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
        switch(msg.getType()){
            case Stable.PROCEED_TO_STABLE:
                st.proceedToStable();
                reply = new Message("Stuff");
                break;

            case Stable.SUMMON_HORSES_TO_PADDOCK:
                double[] hosrse = st.summonHorsesToPaddock(msg.getRaceNumber());
                reply = new Message(MessageType.Stable.REPLY_SUMMON_HORSES_TO_PADDOCK);
                break;

            case Stable.ENTERTAIN_THE_GUESTS:
                st.entertainTheGuests();
                reply = new Message("Stuff");
                break;

            default:
                break;
        }
        return reply;
    }
}
