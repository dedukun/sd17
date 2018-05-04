package spectatorclient.Communication;

public class MessageType {

    public static enum BettingCenter
    {
        SET_HORSES_WINNING_CHANCES,
        ACCEPTED_ALL_BETS,
        ACCEPT_THE_BET,
        PLACE_A_BET,
        ARE_THERE_ANY_WINNERS,
        HONOURED_ALL_THE_BETS,
        HONOUR_THE_BET,
        GO_COLLECT_THE_GAINS
    }

    public static enum ControlCenter
    {
        WAIT_FOR_NEXT_RACE,
        SUMMON_HORSES_TO_PADDOCK,
        UNBLOCK_GO_CHECK_HORSES,
        UNBLOCK_PROCEED_TO_PADDOCK,
        START_THE_RACE,
        UNBLOCK_MAKE_A_MOVE,
        GO_WATCH_THE_RACE,
        REPORT_RESULTS,
        HAVE_I_WON,
        ENTERTAIN_THE_GUESTS,
        RELAX_A_BIT
    }

    public static enum GeneralRepository
    {
        INIT_LOG,
        UPDATE_LOG,
        SET_BET_S,
        SET_BET_A,
        SET_ODDS,
        SET_HORSE_ITERATION,
        SET_HORSE_POSITION,
        SET_HORSE_END,
        SET_TRACK_SIZE,
        SET_RACE_NUMBER,
        SET_BROKER_STATE,
        SET_SPECTATOR_STATE,
        SET_SPECTATOR_MONEY,
        SET_HORSE_STATE,
        SET_HORSE_AGILITY
    }

    public static enum Paddock
    {
        PROCEED_TO_PADDOCK,
        LAST_ARRIVED_TO_PADDOCK,
        GO_CHECK_HORSES,
        LAST_CHECK_HORSES,
        UNBLOCK_GO_CHECK_HORSES
    }

    public static enum RaceTrack
    {
        START_THE_RACE,
        PROCEED_TO_START_LINE,
        MAKE_A_MOVE,
        HAS_RACE_FINISHED,
        GET_RESULTS
    }

    public static enum Stable
    {
        PROCEED_TO_STABLE,
        SUMMON_HORSES_TO_PADDOCK,
        ENTERTAIN_THE_GUESTS
    }
}
