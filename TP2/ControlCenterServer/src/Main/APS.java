package Main;

import Communication.Message;
import Communication.MessageException;
import Communication.MessageType;

public class APS {

    private final ControlCenter cc;
    private boolean isAlive = true;

    public APS(){
        cc = new ControlCenter();
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
            case CONTROL_CENTER_WAIT_FOR_NEXT_RACE:
                boolean wait = cc.waitForNextRace(msg.getSpecId());
                reply = new Message(MessageType.CONTROL_CENTER_REPLY_WAIT_FOR_NEXT_RACE, wait);
                break;

            case CONTROL_CENTER_SUMMON_HORSES_TO_PADDOCK:
                cc.summonHorsesToPaddock();
                reply = new Message(MessageType.OK);
                break;

            case CONTROL_CENTER_UNBLOCK_GO_CHECK_HORSES:
                cc.unblockGoCheckHorses();
                reply = new Message(MessageType.OK);
                break;

            case CONTROL_CENTER_UNBLOCK_PROCEED_TO_PADDOCK:
                cc.unblockProceedToPaddock();
                reply = new Message(MessageType.OK);
                break;

            case CONTROL_CENTER_START_THE_RACE:
                cc.startTheRace();
                reply = new Message(MessageType.OK);
                break;

            case CONTROL_CENTER_UNBLOCK_MAKE_A_MOVE:
                cc.unblockMakeAMove();
                reply = new Message(MessageType.OK);
                break;

            case CONTROL_CENTER_GO_WATCH_THE_RACE:
                cc.goWatchTheRace(msg.getSpecId());
                reply = new Message(MessageType.OK);
                break;

            case CONTROL_CENTER_REPORT_RESULTS:
                cc.reportResults(msg.getWinners());
                reply = new Message(MessageType.OK);
                break;

            case CONTROL_CENTER_HAVE_I_WON:
                boolean winner = cc.haveIWon(msg.getHorseId());
                reply = new Message(MessageType.CONTROL_CENTER_REPLY_HAVE_I_WON, winner);
                break;

            case CONTROL_CENTER_ENTERTAIN_THE_GUESTS:
                cc.entertainTheGuests();
                reply = new Message(MessageType.OK);
                break;

            case CONTROL_CENTER_RELAX_A_BIT:
                cc.relaxABit(msg.getSpecId());
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
