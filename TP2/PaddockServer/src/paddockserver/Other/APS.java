package paddockserver.Other;
import paddockserver.Communication.*;

public class APS {

    private final Paddock pd;

    public APS(){
        pd = new Paddock();
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
            case PROCEED_TO_PADDOCK:
                pd.proceedToPaddock();
                reply = new Message(MessageType.Paddock.OK);
                break;

            case LAST_ARRIVED_TO_PADDOCK:
                boolean last1 = pd.lastArrivedToPaddock();
                reply = new Message(MessageType.Paddock.REPLY_LAST_ARRIVED_TO_PADDOCK,last1);
                break;

            case GO_CHECK_HORSES:
                int horseToBet = pd.goCheckHorses();
                reply = new Message(MessageType.Paddock.REPLY_GO_CHECK_HORSES,horseToBet);
                break;

            case LAST_CHECK_HORSES:
                boolean last2 = pd.lastCheckHorses();
                reply = new Message(MessageType.Paddock.REPLY_LAST_CHECK_HORSES,last2);
                break;

            case UNBLOCK_GO_CHECK_HORSES:
                pd.unblockGoCheckHorses();
                reply = new Message(MessageType.Paddock.OK);
                break;

            default:
                break;
        }
        return reply;
    }
}
