package stableserver.Communication;

public enum MessageType {

    OK,
    END,

    // Betting Center
    // Functions
    BETTING_CENTER_SET_HORSES_WINNING_CHANCES,
    BETTING_CENTER_ACCEPTED_ALL_BETS,
    BETTING_CENTER_ACCEPT_THE_BET,
    BETTING_CENTER_PLACE_A_BET,
    BETTING_CENTER_ARE_THERE_ANY_WINNERS,
    BETTING_CENTER_HONOURED_ALL_THE_BETS,
    BETTING_CENTER_HONOUR_THE_BET,
    BETTING_CENTER_GO_COLLECT_THE_GAINS,

    // Replies
    BETTING_CENTER_REPLY_ACCEPTED_ALL_BETS,
    BETTING_CENTER_REPLY_PLACE_A_BET,
    BETTING_CENTER_REPLY_ARE_THERE_ANY_WINNERS,
    BETTING_CENTER_REPLY_HONOURED_ALL_THE_BETS,
    BETTING_CENTER_REPLY_GO_COLLECT_THE_GAINS,


    // Betting Center
    // Functions
    CONTROL_CENTER_WAIT_FOR_NEXT_RACE,
    CONTROL_CENTER_SUMMON_HORSES_TO_PADDOCK,
    CONTROL_CENTER_UNBLOCK_GO_CHECK_HORSES,
    CONTROL_CENTER_UNBLOCK_PROCEED_TO_PADDOCK,
    CONTROL_CENTER_START_THE_RACE,
    CONTROL_CENTER_UNBLOCK_MAKE_A_MOVE,
    CONTROL_CENTER_GO_WATCH_THE_RACE,
    CONTROL_CENTER_REPORT_RESULTS,
    CONTROL_CENTER_HAVE_I_WON,
    CONTROL_CENTER_ENTERTAIN_THE_GUESTS,
    CONTROL_CENTER_RELAX_A_BIT,

    // Replies
    CONTROL_CENTER_REPLY_WAIT_FOR_NEXT_RACE,
    CONTROL_CENTER_REPLY_HAVE_I_WON,


    // General Repository
    // Functions
    GENERAL_REPO_INIT_LOG,
    GENERAL_REPO_UPDATE_LOG,
    GENERAL_REPO_SET_BET_S,
    GENERAL_REPO_SET_BET_A,
    GENERAL_REPO_SET_ODDS,
    GENERAL_REPO_SET_HORSE_ITERATION,
    GENERAL_REPO_SET_HORSE_POSITION,
    GENERAL_REPO_SET_HORSE_END,
    GENERAL_REPO_SET_TRACK_SIZE,
    GENERAL_REPO_SET_RACE_NUMBER,
    GENERAL_REPO_SET_BROKER_STATE,
    GENERAL_REPO_SET_SPECTATOR_STATE,
    GENERAL_REPO_SET_SPECTATOR_MONEY,
    GENERAL_REPO_SET_HORSE_STATE,
    GENERAL_REPO_SET_HORSE_AGILITY,


    // Paddock
    // Functions
    PADDOCK_PROCEED_TO_PADDOCK,
    PADDOCK_LAST_ARRIVED_TO_PADDOCK,
    PADDOCK_GO_CHECK_HORSES,
    PADDOCK_LAST_CHECK_HORSES,
    PADDOCK_UNBLOCK_GO_CHECK_HORSES,

    // Replies
    PADDOCK_REPLY_LAST_ARRIVED_TO_PADDOCK,
    PADDOCK_REPLY_GO_CHECK_HORSES,
    PADDOCK_REPLY_LAST_CHECK_HORSES,


    // Race Track
    // Functions
    RACE_TRACK_START_THE_RACE,
    RACE_TRACK_PROCEED_TO_START_LINE,
    RACE_TRACK_MAKE_A_MOVE,
    RACE_TRACK_HAS_RACE_FINISHED,
    RACE_TRACK_GET_RESULTS,

    // Replies
    RACE_TRACK_REPLY_MAKE_A_MOVE,
    RACE_TRACK_REPLY_HAS_RACE_FINISHED,
    RACE_TRACK_REPLY_GET_RESULTS,


    // Stable
    // Functions
    STABLE_PROCEED_TO_STABLE,
    STABLE_SUMMON_HORSES_TO_PADDOCK,
    STABLE_ENTERTAIN_THE_GUESTS,

    // Reply
    STABLE_REPLY_SUMMON_HORSES_TO_PADDOCK
}
