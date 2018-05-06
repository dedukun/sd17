package paddockserver.Other;
import paddockserver.Entities.Paddock;
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
            case PADDOCK_PROCEED_TO_PADDOCK:
                pd.proceedToPaddock();
                reply = new Message(MessageType.OK);
                break;

            case PADDOCK_LAST_ARRIVED_TO_PADDOCK:
                boolean lastPaddock = pd.lastArrivedToPaddock();
                reply = new Message(MessageType.PADDOCK_REPLY_LAST_ARRIVED_TO_PADDOCK,lastPaddock);
                break;

            case PADDOCK_GO_CHECK_HORSES:
                int horseToBet = pd.goCheckHorses();
                reply = new Message(MessageType.PADDOCK_REPLY_GO_CHECK_HORSES,horseToBet);
                break;

            case PADDOCK_LAST_CHECK_HORSES:
                boolean lastHorses = pd.lastCheckHorses();
                reply = new Message(MessageType.PADDOCK_REPLY_LAST_CHECK_HORSES,lastHorses);
                break;

            case PADDOCK_UNBLOCK_GO_CHECK_HORSES:
                pd.unblockGoCheckHorses();
                reply = new Message(MessageType.OK);
                break;

            default:
                break;
        }
        return reply;
    }
}
