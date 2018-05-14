package Communication;

/**
 * Enumerate with message types.
 */
public enum MessageType {

    /**
     * Confirmed message.
     */
    OK,

    /**
     * Sending shutdown message.
     */
    END,

    // Betting Center
    // Functions
    /**
     * Betting Center's SET HORSES WINNING CHANCES functions message type.
     */
    BETTING_CENTER_SET_HORSES_WINNING_CHANCES,
    /**
     * Betting Center's ACCEPTED ALL BETS functions message type.
     */
    BETTING_CENTER_ACCEPTED_ALL_BETS,
    /**
     * Betting Center's ACCEPT THE BET functions message type.
     */
    BETTING_CENTER_ACCEPT_THE_BET,
    /**
     * Betting Center's PLACE A BET functions message type.
     */
    BETTING_CENTER_PLACE_A_BET,
    /**
     * Betting Center's ARE THERE ANY WINNERS functions message type.
     */
    BETTING_CENTER_ARE_THERE_ANY_WINNERS,
    /**
     * Betting Center's HONOURED ALL THE BETS functions message type.
     */
    BETTING_CENTER_HONOURED_ALL_THE_BETS,
    /**
     * Betting Center's HONOUR THE BET functions message type.
     */
    BETTING_CENTER_HONOUR_THE_BET,
    /**
     * Betting Center's GO COLLECT THE GAINS functions message type.
     */
    BETTING_CENTER_GO_COLLECT_THE_GAINS,

    // Replies
    /**
     * Betting Center's reply's to ACCEPTED ALL BETS functions messate type.
     */
    BETTING_CENTER_REPLY_ACCEPTED_ALL_BETS,
    /**
     * Betting Center's reply's to PLACE A BET functions messate type.
     */
    BETTING_CENTER_REPLY_PLACE_A_BET,
    /**
     * Betting Center's reply's to ARE THERE ANY WINNERS functions messate type.
     */
    BETTING_CENTER_REPLY_ARE_THERE_ANY_WINNERS,
    /**
     * Betting Center's reply's to HONOURED ALL THE BETS functions messate type.
     */
    BETTING_CENTER_REPLY_HONOURED_ALL_THE_BETS,
    /**
     * Betting Center's reply's to GO COLLECT THE GAINS functions messate type.
     */
    BETTING_CENTER_REPLY_GO_COLLECT_THE_GAINS,


    // Betting Center
    // Functions
    /**
     * Control Center's WAIT FOR NEXT RACE functions message type.
     */
    CONTROL_CENTER_WAIT_FOR_NEXT_RACE,
    /**
     * Control Center's SUMMON HORSES TO PADDOCK functions message type.
     */
    CONTROL_CENTER_SUMMON_HORSES_TO_PADDOCK,
    /**
     * Control Center's UNBLOCK GO CHECK HORSES functions message type.
     */
    CONTROL_CENTER_UNBLOCK_GO_CHECK_HORSES,
    /**
     * Control Center's UNBLOCK PROCEED TO PADDOCK functions message type.
     */
    CONTROL_CENTER_UNBLOCK_PROCEED_TO_PADDOCK,
    /**
     * Control Center's START THE RACE functions message type.
     */
    CONTROL_CENTER_START_THE_RACE,
    /**
     * Control Center's UNBLOCK MAKE A MOVE functions message type.
     */
    CONTROL_CENTER_UNBLOCK_MAKE_A_MOVE,
    /**
     * Control Center's GO WATCH THE RACE functions message type.
     */
    CONTROL_CENTER_GO_WATCH_THE_RACE,
    /**
     * Control Center's REPORT RESULTS functions message type.
     */
    CONTROL_CENTER_REPORT_RESULTS,
    /**
     * Control Center's HAVE I WON functions message type.
     */
    CONTROL_CENTER_HAVE_I_WON,
    /**
     * Control Center's ENTERTAIN THE GUESTS functions message type.
     */
    CONTROL_CENTER_ENTERTAIN_THE_GUESTS,
    /**
     * Control Center's RELAX A BIT functions message type.
     */
    CONTROL_CENTER_RELAX_A_BIT,

    // Replies
    /**
     * Control Center's reply's to WAIT FOR NEXT RACE functions messate type.
     */
    CONTROL_CENTER_REPLY_WAIT_FOR_NEXT_RACE,
    /**
     * Control Center's reply's to HAVE I WON functions messate type.
     */
    CONTROL_CENTER_REPLY_HAVE_I_WON,


    // General Repository
    // Functions
    /**
     * General Repo's INIT LOG functions message type.
     */
    GENERAL_REPO_INIT_LOG,
    /**
     * General Repo's UPDATE LOG functions message type.
     */
    GENERAL_REPO_UPDATE_LOG,
    /**
     * General Repo's SET BET S functions message type.
     */
    GENERAL_REPO_SET_BET_S,
    /**
     * General Repo's SET BET A functions message type.
     */
    GENERAL_REPO_SET_BET_A,
    /**
     * General Repo's SET ODDS functions message type.
     */
    GENERAL_REPO_SET_ODDS,
    /**
     * General Repo's SET HORSE ITERATION functions message type.
     */
    GENERAL_REPO_SET_HORSE_ITERATION,
    /**
     * General Repo's SET HORSE POSITION functions message type.
     */
    GENERAL_REPO_SET_HORSE_POSITION,
    /**
     * General Repo's SET HORSE END functions message type.
     */
    GENERAL_REPO_SET_HORSE_END,
    /**
     * General Repo's SET TRACK SIZE functions message type.
     */
    GENERAL_REPO_SET_TRACK_SIZE,
    /**
     * General Repo's SET RACE NUMBER functions message type.
     */
    GENERAL_REPO_SET_RACE_NUMBER,
    /**
     * General Repo's SET BROKER STATE functions message type.
     */
    GENERAL_REPO_SET_BROKER_STATE,
    /**
     * General Repo's SET SPECTATOR STATE functions message type.
     */
    GENERAL_REPO_SET_SPECTATOR_STATE,
    /**
     * General Repo's SET SPECTATOR MONEY functions message type.
     */
    GENERAL_REPO_SET_SPECTATOR_MONEY,
    /**
     * General Repo's SET HORSE STATE functions message type.
     */
    GENERAL_REPO_SET_HORSE_STATE,
    /**
     * General Repo's SET HORSE AGILITY functions message type.
     */
    GENERAL_REPO_SET_HORSE_AGILITY,


    // Paddock
    // Functions
    /**
     * Paddock's PROCEED TO PADDOCK functions message type.
     */
    PADDOCK_PROCEED_TO_PADDOCK,
    /**
     * Paddock's LAST ARRIVED TO PADDOCK functions message type.
     */
    PADDOCK_LAST_ARRIVED_TO_PADDOCK,
    /**
     * Paddock's GO CHECK HORSES functions message type.
     */
    PADDOCK_GO_CHECK_HORSES,
    /**
     * Paddock's LAST CHECK HORSES functions message type.
     */
    PADDOCK_LAST_CHECK_HORSES,
    /**
     * Paddock's UNBLOCK GO CHECK HORSES functions message type.
     */
    PADDOCK_UNBLOCK_GO_CHECK_HORSES,

    // Replies
    /**
     * Paddock's reply's to LAST ARRIVED TO PADDOCK functions message type.
     */
    PADDOCK_REPLY_LAST_ARRIVED_TO_PADDOCK,
    /**
     * Paddock's reply's to GO CHECK HORSES functions message type.
     */
    PADDOCK_REPLY_GO_CHECK_HORSES,
    /**
     * Paddock's reply's to LAST CHECK HORSES functions message type.
     */
    PADDOCK_REPLY_LAST_CHECK_HORSES,


    // Race Track
    // Functions
    /**
     * Race Track's START THE RACE functions message type.
     */
    RACE_TRACK_START_THE_RACE,
    /**
     * Race Track's PROCEED TO START LINE functions message type.
     */
    RACE_TRACK_PROCEED_TO_START_LINE,
    /**
     * Race Track's MAKE A MOVE functions message type.
     */
    RACE_TRACK_MAKE_A_MOVE,
    /**
     * Race Track's HAS RACE FINISHED functions message type.
     */
    RACE_TRACK_HAS_RACE_FINISHED,
    /**
     * Race Track's GET RESULTS functions message type.
     */
    RACE_TRACK_GET_RESULTS,

    // Replies
    /**
     * Race Track's reply's to MAKE A MOVE functions messate type.
     */
    RACE_TRACK_REPLY_MAKE_A_MOVE,
    /**
     * Race Track's reply's to HAS RACE FINISHED functions messate type.
     */
    RACE_TRACK_REPLY_HAS_RACE_FINISHED,
    /**
     * Race Track's reply's to GET RESULTS functions messate type.
     */
    RACE_TRACK_REPLY_GET_RESULTS,


    // Stable
    // Functions
    /**
     * Stable's PROCEED TO STABLE functions message type.
     */
    STABLE_PROCEED_TO_STABLE,
    /**
     * Stable's SUMMON HORSES TO PADDOCK functions message type.
     */
    STABLE_SUMMON_HORSES_TO_PADDOCK,
    /**
     * Stable's ENTERTAIN THE GUESTS functions message type.
     */
    STABLE_ENTERTAIN_THE_GUESTS,

    // Reply
    /**
     * Stable's reply's to SUMMON HORSES TO PADDOCK functions message type.
     */
    STABLE_REPLY_SUMMON_HORSES_TO_PADDOCK
}
