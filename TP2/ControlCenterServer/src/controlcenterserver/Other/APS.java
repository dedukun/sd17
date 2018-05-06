package controlcenterserver.Other;

import controlcenterserver.Entities.ControlCenter;
import controlcenterserver.Communication.*;

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
        switch(msg.getMessageType()){
            case CONTROL_CENTER_WAIT_FOR_NEXT_RACE:
                boolean wait = cc.waitForNextRace();
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
                cc.goWatchTheRace();
                reply = new Message(MessageType.OK);
                break;

            case CONTROL_CENTER_REPORT_RESULTS:
                cc.reportResults(msg.getWinners());
                reply = new Message(MessageType.OK);
                break;

            case CONTROL_CENTER_HAVE_I_WON:
                boolean winner = cc.haveIWon(msg.getHorseJockeyId());
                reply = new Message(MessageType.CONTROL_CENTER_REPLY_HAVE_I_WON, winner);
                break;

            case CONTROL_CENTER_ENTERTAIN_THE_GUESTS:
                cc.entertainTheGuests();
                reply = new Message(MessageType.OK);
                break;

            case CONTROL_CENTER_RELAX_A_BIT:
                cc.relaxABit();
                reply = new Message(MessageType.OK);
                break;

            default:
                break;
        }
        return reply;
    }
}
