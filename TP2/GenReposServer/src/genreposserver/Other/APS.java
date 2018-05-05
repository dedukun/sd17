package genreposserver.Other;
import genreposserver.Communication.*;

public class APS {

    private final GeneralRepository gr;

    public APS(){
        gr = new GeneralRepository();
    }

    /**
    * Processes the message and returns a reply.
    *
    *   @param msg Mesage to be processed
    */
    public Message compute(Message msg) throws Exception{
        Message reply = null;
        switch(msg.getMessageType()){
            case INIT_LOG:
                gr.initLog();
                reply = new Message(MessageType.GeneralRepository.OK);
                break;

            case UPDATE_LOG:
                gr.updateLog();
                reply = new Message(MessageType.GeneralRepository.OK);
                break;

            case SET_BET_S:
                gr.setBetS(msg.getSpecId(),msg.getHorseId());
                reply = new Message(MessageType.GeneralRepository.OK);
                break;

            case SET_BET_A:
                gr.setBetA(msg.getSpecId(),msg.getBetAmount());
                reply = new Message(MessageType.GeneralRepository.OK);
                break;

            case SET_ODDS:
                gr.setOdds(msg.getHorseId(),msg.getOdd());
                reply = new Message(MessageType.GeneralRepository.OK);
                break;

            case SET_HORSE_ITERATION:
                gr.setHorseIteration(msg.getHorseId());
                reply = new Message(MessageType.GeneralRepository.OK);
                break;

            case SET_HORSE_POSITION:
                gr.setHorsePosition(msg.getHorseId(),msg.getPos());
                reply = new Message(MessageType.GeneralRepository.OK);
                break;

            case SET_HORSE_END:
                gr.setHorseEnd(msg.getHorseId(),msg.getPlace());
                reply = new Message(MessageType.GeneralRepository.OK);
                break;

            case SET_TRACK_SIZE:
                gr.setTrackSize(msg.getSize());
                reply = new Message(MessageType.GeneralRepository.OK);
                break;

            case SET_RACE_NUMBER:
                gr.setRaceNumber(msg.getNum());
                reply = new Message(MessageType.GeneralRepository.OK);
                break;

            case SET_BROKER_STATE:
                gr.setBrokerState(msg.getBstate());
                reply = new Message(MessageType.GeneralRepository.OK);
                break;

            case SET_SPECTATOR_STATE:
                gr.setSpectatorState(msg.getSpecId(),msg.getSstate());
                reply = new Message(MessageType.GeneralRepository.OK);
                break;

            case SET_SPECTATOR_MONEY:
                gr.setSpectatorMoney(msg.getSpecId(),msg.getFunds());
                reply = new Message(MessageType.GeneralRepository.OK);
                break;

            case SET_HORSE_STATE:
                gr.setHorseState(msg.getHorseId(),msg.getHjstate());
                reply = new Message(MessageType.GeneralRepository.OK);
                break;

            case SET_HORSE_AGILITY:
                gr.setHorseAgility(msg.getHorseId(),msg.getHorseAgl());
                reply = new Message(MessageType.GeneralRepository.OK);
                break;

            default:
                break;
        }
        return reply;
    }
}
