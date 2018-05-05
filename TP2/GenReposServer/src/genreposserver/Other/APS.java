package genreposserver.Other;

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
        switch(msg.getType()){
            case GeneralRepository.INIT_LOG:
                gr.initLog();
                reply = new Message("Stuff");
                break;

            case GeneralRepository.UPDATE_LOG:
                gr.updateLog();
                reply = new Message("Stuff");
                break;

            case GeneralRepository.SET_BET_S:
                gr.setBetS(msg.getSpecID(),msg.getHorseID());
                reply = new Message("Stuff");
                break;

            case GeneralRepository.SET_BET_A:
                gr.setBetA(msg.getSpecID(),msg.getBetAmount());
                reply = new Message("Stuff");
                break;

            case GeneralRepository.SET_ODDS:
                gr.setOdds(msg.getHorseID(),msg.getOdd());
                reply = new Message("Stuff");
                break;

            case GeneralRepository.SET_HORSE_ITERATION:
                gr.setHorseIteration(msg.getHorseID());
                reply = new Message("Stuff");
                break;

            case GeneralRepository.SET_HORSE_POSITION:
                gr.setHorsePossition(msg.getHorseID(),msg.getPos());
                reply = new Message("Stuff");
                break;

            case GeneralRepository.SET_HORSE_END:
                gr.setHorseEnd(msg.getHorseID(),msg.getPlace());
                reply = new Message("Stuff");
                break;

            case GeneralRepository.SET_TRACK_SIZE:
                gr.setTrackSize(msg.getSize());
                reply = new Message("Stuff");
                break;

            case GeneralRepository.SET_RACE_NUMBER:
                gr.setRaceNumber(msg.getNum());
                reply = new Message("Stuff");
                break;

            case GeneralRepository.SET_BROKER_STATE:
                gr.setBrokerState(msg.getBrokerState());
                reply = new Message("Stuff");
                break;

            case GeneralRepository.SET_SPECTATOR_STATE:
                gr.setSpectatorState(msg.getSpecID(),msg.getSpecatorState());
                reply = new Message("Stuff");
                break;

            case GeneralRepository.SET_SPECTATOR_MONEY:
                gr.setSpectatorMoney(msg.getSpecID(),msg.getFunds());
                reply = new Message("Stuff");
                break;

            case GeneralRepository.SET_HORSE_STATE:
                gr.setHorseState(msg.getHorseID(),msg.getHorseState());
                reply = new Message("Stuff");
                break;

            case GeneralRepository.SET_HORSE_AGILITY:
                gr.setHorseAgility(msg.getHorseID(),msg.getHorseAgl());
                reply = new Message("Stuff");
                break;

            default:
                break;
        }
        return reply;
    }
}
