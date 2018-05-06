package paddockserver.Main;
import paddockserver.Communication.*;

public class APS {

    private final Paddock pd;
    private boolean isAlive = true;

    public APS(){
        pd = new Paddock();
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
            case PADDOCK_PROCEED_TO_PADDOCK:
                pd.proceedToPaddock(msg.getHorseId(), msg.getHorseAgl());
                reply = new Message(MessageType.OK);
                break;

            case PADDOCK_LAST_ARRIVED_TO_PADDOCK:
                boolean lastPaddock = pd.lastArrivedToPaddock(msg.getHorseId());
                reply = new Message(MessageType.PADDOCK_REPLY_LAST_ARRIVED_TO_PADDOCK,lastPaddock);
                break;

            case PADDOCK_GO_CHECK_HORSES:
                int horseToBet = pd.goCheckHorses(msg.getSpecId());
                reply = new Message(MessageType.PADDOCK_REPLY_GO_CHECK_HORSES,horseToBet);
                break;

            case PADDOCK_LAST_CHECK_HORSES:
                boolean lastHorses = pd.lastCheckHorses(msg.getSpecId());
                reply = new Message(MessageType.PADDOCK_REPLY_LAST_CHECK_HORSES,lastHorses);
                break;

            case PADDOCK_UNBLOCK_GO_CHECK_HORSES:
                pd.unblockGoCheckHorses();
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
