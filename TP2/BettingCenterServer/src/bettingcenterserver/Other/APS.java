package bettingcenterserver.Other;

import bettingcenterserver.Communication;

public class APS {

    private final BettingCenter bc;

    /**
     * Betting Center Proxy Initialization.
     */
    public APS(){
        bc = new BettingCenter();
    }

    /**
    * Processes the message and returns a reply.
    *
    *   @param msg Mesage to be processed
    */
    public Message compute(Message msg) throws Exception{
        Message reply = null;
        switch(msg.getType()){
            case BettingCenter.SET_HORSES_WINNING_CHANCES:
                bc.setHorsesWinningChances(msg.getHorseChances());
                reply = new Message("Stuff");
                break;

            case BettingCenter.ACCEPTED_ALL_BETS:
                boolean accepted = bc.acceptedAllBets();
                reply = new Message(MessageType.BettingCenter.REPLY_ACCEPTED_ALL_BETS, accepted);
                break;

            case BettingCenter.ACCEPT_THE_BET:
                bc.acceptTheBet();
                reply = new Message("Stuff");
                break;

             case BettingCenter.PLACE_A_BET:
                bc.placeABet(msg.getHorseId());
                reply = new Message("Stuff");
                break;

            case BettingCenter.ARE_THERE_ANY_WINNERS:
                boolean winners = bc.areThereAnyWinners(msg.getWinningHorses());
                reply = new Message(MessageType.BettingCenter.REPLY_ARE_THERE_ANY_WINNERS, winners);
                break;

            case BettingCenter.HONOURED_ALL_THE_BETS:
                boolean honoured = bc.honouredAllBets();
                reply = new Message(MessageType.BettingCenter.REPLY_HONOURED_ALL_THE_BETS,honoured);
                break;

            case BettingCenter.HONOUR_THE_BET:
                bc.honourTheBet();
                reply = new Message("Stuff");
                break;

            case BettingCenter.GO_COLLECT_THE_GAINS:
                bc.goCollectTheGains();
                reply = new Message("Stuff");
                break;

            default:
                break;
        }
        return reply;
    }
}
