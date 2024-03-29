package Main;

import Auxiliar.SimulPar;
import Communication.Message;
import Communication.MessageException;
import Communication.MessageType;

/**
 * Betting Center's Proxy Server.
 */
public class APS {

    private final BettingCenter bc;
    private int totalEntities = 1 + SimulPar.S;

    /**
     * Betting Center Proxy Initialization.
     */
    public APS(){
        bc = new BettingCenter();
    }

    /**
    * Processes the message and returns a reply.
    *
    *   @param msg Message to be processed
    *   @return A message Reply
    *   @throws MessageException
    */
    public Message compute(Message msg) throws MessageException{
        Message reply = null;
        switch(msg.getMessageType()){
            case BETTING_CENTER_SET_HORSES_WINNING_CHANCES:
                bc.setHorsesWinningChances(msg.getHorsesChances());
                reply = new Message(MessageType.OK);
                break;

            case BETTING_CENTER_ACCEPTED_ALL_BETS:
                boolean accepted = bc.acceptedAllBets();
                reply = new Message(MessageType.BETTING_CENTER_REPLY_ACCEPTED_ALL_BETS, accepted);
                break;

            case BETTING_CENTER_ACCEPT_THE_BET:
                bc.acceptTheBet();
                reply = new Message(MessageType.OK);
                break;

             case BETTING_CENTER_PLACE_A_BET:
                double bet = bc.placeABet(msg.getHorseId(), msg.getSpecId(), msg.getSpecFunds());
                reply = new Message(MessageType.BETTING_CENTER_REPLY_PLACE_A_BET, bet);
                break;

            case BETTING_CENTER_ARE_THERE_ANY_WINNERS:
                boolean winners = bc.areThereAnyWinners(msg.getWinningHorses());
                reply = new Message(MessageType.BETTING_CENTER_REPLY_ARE_THERE_ANY_WINNERS, winners);
                break;

            case BETTING_CENTER_HONOURED_ALL_THE_BETS:
                boolean honoured = bc.honouredAllTheBets();
                reply = new Message(MessageType.BETTING_CENTER_REPLY_HONOURED_ALL_THE_BETS,honoured);
                break;

            case BETTING_CENTER_HONOUR_THE_BET:
                bc.honourTheBet();
                reply = new Message(MessageType.OK);
                break;

            case BETTING_CENTER_GO_COLLECT_THE_GAINS:
                double earnings = bc.goCollectTheGains(msg.getSpecId(), msg.getSpecFunds());
                reply = new Message(MessageType.BETTING_CENTER_REPLY_GO_COLLECT_THE_GAINS, earnings);
                break;
                
            case END:
                System.out.println("END IN "+totalEntities);
                totalEntities--;
                reply = new Message(MessageType.OK);
                break;

            default:
                break;
        }
        return reply;
    }
    
    /**
     * Checks if BettingCenter is still alive.
     * 
     * @return true if it is alive
     */
    public boolean isAlive(){
        if(totalEntities==0){
            bc.shutdownGenRepo();
            return false;
        }
        return true;
    }
}
