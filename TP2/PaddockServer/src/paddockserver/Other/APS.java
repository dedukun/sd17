package paddockserver.Other;

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
        switch(msg.getType()){
            case Paddock.PROCEED_TO_PADDOCK:
                pd.proceedToPaddock();
                reply = new Message("Stuff");
                break;

            case Paddock.LAST_ARRIVED_TO_PADDOCK:
                boolean last = pd.lastArrivedToPaddock();
                reply = new Message(MessageType.Paddock.REPLY_LAST_ARRIVED_TO_PADDOCK,last);
                break;

            case Paddock.GO_CHECK_HORSES:
                int horseToBet = pd.goCheckHorses();
                reply = new Message(MessageType.Paddock.REPLY_GO_CHECK_HORSES,horseToBet);
                break;

            case Paddock.LAST_CHECK_HORSES:
                boolean last = pd.lastCheckHorses();
                reply = new Message(MessageType.Paddock.REPLY_LAST_CHECK_HORSES,last);
                break;

            case Paddock.UNBLOCK_GO_CHECK_HORSES:
                pd.unblockGoCheckHorses();
                reply = new Message("Stuff");
                break;

            default:
                break;
        }
        return reply;
    }
}
