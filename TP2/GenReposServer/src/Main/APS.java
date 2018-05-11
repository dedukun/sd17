package Main;

import Communication.MessageException;
import Communication.MessageType;
import Communication.Message;

public class APS {

    private GeneralRepository gr;
    private int totalRegions = 5;

    /**
     * Instantiation of the proxy provider.
     */
    public APS(){
        gr = new GeneralRepository();
    }

    /**
    * Processes the message and returns a reply.
    *
    *   @param msg Mesage to be processed
    *   @return
    *   @throws MessageException
    */
    public Message compute(Message msg) throws MessageException{
        Message reply = null;
        switch(msg.getMessageType()){
            case GENERAL_REPO_INIT_LOG:
                gr.initLog();
                reply = new Message(MessageType.OK);
                break;

            case GENERAL_REPO_UPDATE_LOG:
                gr.updateLog();
                reply = new Message(MessageType.OK);
                break;

            case GENERAL_REPO_SET_BET_S:
                gr.setBetS(msg.getSpecId(),msg.getHorseId());
                reply = new Message(MessageType.OK);
                break;

            case GENERAL_REPO_SET_BET_A:
                gr.setBetA(msg.getSpecId(),msg.getBetAmount());
                reply = new Message(MessageType.OK);
                break;

            case GENERAL_REPO_SET_ODDS:
                gr.setOdds(msg.getHorseId(),msg.getOdd());
                reply = new Message(MessageType.OK);
                break;

            case GENERAL_REPO_SET_HORSE_ITERATION:
                gr.setHorseIteration(msg.getHorseId());
                reply = new Message(MessageType.OK);
                break;

            case GENERAL_REPO_SET_HORSE_POSITION:
                gr.setHorsePosition(msg.getHorseId(),msg.getPos());
                reply = new Message(MessageType.OK);
                break;

            case GENERAL_REPO_SET_HORSE_END:
                gr.setHorseEnd(msg.getHorseId(),msg.getPlace());
                reply = new Message(MessageType.OK);
                break;

            case GENERAL_REPO_SET_TRACK_SIZE:
                gr.setTrackSize(msg.getTrackSize());
                reply = new Message(MessageType.OK);
                break;

            case GENERAL_REPO_SET_RACE_NUMBER:
                gr.setRaceNumber(msg.getRaceNumber());
                reply = new Message(MessageType.OK);
                break;

            case GENERAL_REPO_SET_BROKER_STATE:
                gr.setBrokerState(msg.getBstate());
                reply = new Message(MessageType.OK);
                break;

            case GENERAL_REPO_SET_SPECTATOR_STATE:
                gr.setSpectatorState(msg.getSpecId(),msg.getSstate());
                reply = new Message(MessageType.OK);
                break;

            case GENERAL_REPO_SET_SPECTATOR_MONEY:
                gr.setSpectatorMoney(msg.getSpecId(),msg.getFunds());
                reply = new Message(MessageType.OK);
                break;

            case GENERAL_REPO_SET_HORSE_STATE:
                gr.setHorseState(msg.getHorseId(),msg.getHjstate());
                reply = new Message(MessageType.OK);
                break;

            case GENERAL_REPO_SET_HORSE_AGILITY:
                gr.setHorseAgility(msg.getHorseId(),msg.getHorseAgl());
                reply = new Message(MessageType.OK);
                break;

            case END:
                System.out.println("Regions "+totalRegions);
                totalRegions--;
                reply = new Message(MessageType.OK);
                break;

            default:
                break;
        }
        return reply;
    }

    /**
     *   @return
     */
    public boolean isAlive(){
        return totalRegions!=0;
    }
}
