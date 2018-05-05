package controlcenterserver;

public class APS {

    private final ControlCenter cc;

    public APS(){
        cc = new ControlCenter();
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
            case ControlCenter.WAIT_FOR_NEXT_RACE:
                boolean wait = cc.waitForNextRace();
                reply = new Message(MessageType.ControlCenter.REPLY_WAIT_FOR_NEXT_RACE);
                break;

            case ControlCenter.SUMMON_HORSES_TO_PADDOCK:
                cc.summonHorsesToPaddock();
                reply = new Message("Stuff");
                break;

            case ControlCenter.UNBLOCK_GO_CHECK_HORSES:
                cc.unblockGoCheckHorses();
                reply = new Message("Stuff");
                break;

             case ControlCenter.UNBLOCK_PROCEED_TO_PADDOCK:
                cc.unblockProceedToPaddock();
                reply = new Message("Stuff");
                break;

            case ControlCenter.START_THE_RACE:
                cc.startTheRace();
                reply = new Message("Stuff");
                break;

            case ControlCenter.UNBLOCK_MAKE_A_MOVE:
                cc.unblockMakeAMove();
                reply = new Message("Stuff");
                break;

            case ControlCenter.GO_WATCH_THE_RACE:
                cc.goWatchTheRace();
                reply = new Message("Stuff");
                break;

            case ControlCenter.REPORT_RESULTS:
                cc.reportResults(msg.getWinners());
                reply = new Message("Stuff");
                break;

            case ControlCenter.HAVE_I_WON:
                boolean winner = cc.haveIWon(msg.getHJID());
                reply = new Message(MessageType.ControlCenter.REPLY_HAVE_I_WON,winner);
                break;

            case ControlCenter.ENTERTAIN_THE_GUESTS:
                cc.entertainTheGuests();
                reply = new Message("Stuff");
                break;

            case ControlCenter.RELAX_A_BIT:
                cc.relaxABit();
                reply = new Message("Stuff");
                break;

            default:
                break;
        }
        return reply;
    }
}
