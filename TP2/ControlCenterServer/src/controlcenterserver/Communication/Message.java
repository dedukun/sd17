package controlcenterserver.Communication;

import controlcenterserver.Entities.BrokerStates;
import controlcenterserver.Entities.HorseJockeyStates;
import controlcenterserver.Entities.SpectatorStates;

import java.io.*;

/**
 *
 * Defines the exchanged messages between Betting Center Server and clients.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1032;

    private MessageType msgType;

    private int horseId;
    private double[] horsesChances;

    // Betting Center
    private int[] winningHorses;
    private boolean acceptedBets;
    private boolean anyWinners;
    private boolean honouredBets;

    // Control Center
    private int[] winners;
    private int hjid;
    private boolean wait;
    private boolean winner;

    // General Repo
    private BrokerStates bstate;
    private HorseJockeyStates hjstate;
    private SpectatorStates sstate;
    private int raceNum;
    private int trackSize;
    private int betAmount;
    private int pos;
    private int horseAgl;
    private int horseIter;
    private int place;
    private int funds;
    private int specId;
    private double odd;

    // Paddock
    private boolean lastToPaddock;
    private boolean lastCheckHorses;
    private int horseToBet;

    // Race Track
    private boolean move;
    private boolean finished;
    private int[] results;

    // Stable
    private int raceNumber;

    /**
    *
    *  @param type Enumerate indicating the type of the message
    */
    public Message(MessageType type){
        msgType = type;
    }

    /**
    *
    *  @param type Enumerate indicating the type of the message
    *  @param param Id of the desired horse
    */
    public Message(MessageType type, int param){
        msgType = type;
        switch(type){
            case BETTING_CENTER_PLACE_A_BET:
                horseId = param;
                break;
            case CONTROL_CENTER_HAVE_I_WON:
                hjid = param;
                break;
            case GENERAL_REPO_SET_HORSE_ITERATION:
                horseIter = param;
                break;
            case GENERAL_REPO_SET_TRACK_SIZE:
                trackSize = param;
                break;
            case GENERAL_REPO_SET_RACE_NUMBER:
                raceNum = param;
                break;
            case PADDOCK_REPLY_GO_CHECK_HORSES:
                horseToBet = param;
                break;
            case STABLE_SUMMON_HORSES_TO_PADDOCK:
                raceNumber = param;
                break;
        }
    }

    /**
    *
    *  @param type Enumerate indicating the type of the message
    *  @param param1 First parameter of fucntion(specId/horseId/specId)
    *  @param param2 Second parameter of function (betamount/horseId/horseAgl/place/pos/funds)
    */
    public Message(MessageType type, int param1, int param2){
        msgType = type;

        switch(type){
            case GENERAL_REPO_SET_BET_S:
                specId = param1;
                horseId = param2;
                break;
            case GENERAL_REPO_SET_BET_A:
                specId = param1;
                betAmount = param2;
                break;
            case GENERAL_REPO_SET_HORSE_POSITION:
                horseId = param1;
                pos = param2;
                break;
            case GENERAL_REPO_SET_HORSE_END:
                horseId = param1;
                place = param2;
                break;
            case GENERAL_REPO_SET_SPECTATOR_MONEY:
                specId = param1;
                funds = param2;
                break;
            case GENERAL_REPO_SET_HORSE_AGILITY:
                horseId = param1;
                horseAgl = param2;
                break;
        }
    }

    /**
    *
    *  @param type Enumerate indicating the type of the message
    *  @param oddParam representing the odds wanted
    */
    public Message(MessageType type, int horseidParam, double oddParam){
        msgType = type;
        horseId = horseidParam;
        odd = oddParam;
    }

    /**
    *
    *  @param type Enumerate indicating the type of the message
    *  @param bstateParam Broker state
    */
    public Message(MessageType type, BrokerStates bstateParam){
        msgType = type;
        bstate = bstateParam;
    }

    /**
    *
    *  @param type Enumerate indicating the type of the message
    *  @param horseidParam Horse ID
    *  @param bstateParam Broker state
    */
    public Message(MessageType type, int horseidParam, HorseJockeyStates hjstateParam){
        msgType = type;
        horseId = horseidParam;
        hjstate = hjstateParam;
    }

    /**
    *
    *  @param type Enumerate indicating the type of the message
    *  @param specidParam Horse ID
    *  @param sstateParam Specator state
    */
    public Message(MessageType type, int specidParam, SpectatorStates sstateParam){
        msgType = type;
        specId = specidParam;
        sstate = sstateParam;
    }

    /**
    *
    *  @param type Enumerate indicating the type of the message
    *  @param param number of winning horses
    */
    public Message(MessageType type, int[] param){
        msgType = type;
        switch(type){
            case BETTING_CENTER_ARE_THERE_ANY_WINNERS:
                winningHorses = param;
                break;
            case CONTROL_CENTER_REPORT_RESULTS:
                winners = param;
                break;
            case RACE_TRACK_REPLY_GET_RESULTS:
                results = param;
                break;
        }
    }

    /**
    *
    *  @param type Enumerate indicating the type of the message
    *  @param param Horses winning chances
    */
    public Message(MessageType type, double[] param){
        msgType = type;
        switch(type){
            case BETTING_CENTER_SET_HORSES_WINNING_CHANCES:
                horsesChances = param;
                break;
            case STABLE_REPLY_SUMMON_HORSES_TO_PADDOCK:
                horsesChances = param;
                break;
        }
    }

    /**
    *
    *  @param type Enumerate indicating the type of the message
    *  @param param Boolean accepted/winners/honoured
    */
    public Message(MessageType type, boolean param){
        msgType = type;
        switch(type){
            case BETTING_CENTER_REPLY_ACCEPTED_ALL_BETS:
                acceptedBets = param;
                break;
            case BETTING_CENTER_REPLY_ARE_THERE_ANY_WINNERS:
                anyWinners = param;
                break;
            case BETTING_CENTER_REPLY_HONOURED_ALL_THE_BETS:
                honouredBets = param;
                break;
            case CONTROL_CENTER_REPLY_HAVE_I_WON:
                winner = param;
                break;
            case CONTROL_CENTER_REPLY_WAIT_FOR_NEXT_RACE:
                wait = param;
                break;
            case PADDOCK_REPLY_LAST_ARRIVED_TO_PADDOCK:
                lastToPaddock = param;
                break;
            case PADDOCK_REPLY_LAST_CHECK_HORSES:
                lastCheckHorses = param;
                break;
            case RACE_TRACK_REPLY_MAKE_A_MOVE:
                move = param;
                break;
            case RACE_TRACK_REPLY_HAS_RACE_FINISHED:
                finished = param;
                break;
        }
    }

    /**
     * Returns the enumerate indicating the type of the message.
     *
     *   @return Enumerate indicating the type
     */
    public MessageType getMessageType(){
        return msgType;
    }

    /**
     * Returns a horse ID
     *
     *   @return horse ID
     */
    public int getHorseId() {
        return horseId;
    }

    /**
     *
     */
    public double[] getHorsesChances(){
        return horsesChances;
    }

    /*******************Betting Center*******************/
    /**
     *
     */
    public int[] getWinningHorses(){
        return winningHorses;
    }

    /**
     *
     */
    public boolean getAllBetsAccepted(){
        return acceptedBets;
    }

    /**
     *
     */
    public boolean getAnyWinners(){
        return anyWinners;
    }

    /**
     *
     */
    public boolean getAllBetsHonoured(){
        return honouredBets;
    }

    /*******************Control Center*******************/
    /**
     *
     */
    public int[] getWinners(){
        return winners;
    }

    /**
     *
     */
    public int getHorseJockeyId(){
        return hjid;
    }

    /**
     *
     */
    public boolean getHaveIWon(){
        return winner;
    }

    /**
     *
     */
    public boolean getWaitNextRace(){
        return wait;
    }

    /********************General Repo********************/
    /**
     * Returns the broker state
     *
     *   @return Broker state
     */
    public BrokerStates getBstate() {
        return bstate;
    }

    /**
     * Returns the Horse/Jockey state
     *
     *   @return Horse/Jockey state
     */
    public HorseJockeyStates getHjstate() {
        return hjstate;
    }

    /**
     * Returns the Spectator state
     *
     *   @return Spectator state
     */
    public SpectatorStates getSstate() {
        return sstate;
    }

    /**
     * Returns the race number
     *
     *   @return race number
     */
    public int getRaceNum() {
        return raceNum;
    }

    /**
     * Returns the race track size
     *
     *   @return Race track size
     */
    public int getTrackSize() {
        return trackSize;
    }

    /**
     * Returns the bet amount
     *
     *   @return Bet amount
     */
    public int getBetAmount() {
        return betAmount;
    }

    /**
     * Returns the position of a horse
     *
     *   @return a horse position
     */
    public int getPos() {
        return pos;
    }

    /**
     * Returns a horse agility
     *
     *   @return a horse agility
     */
    public int getHorseAgl() {
        return horseAgl;
    }

    /**
     * Returns the place of a horse/jockey pair
     *
     *   @return place of a horse/jockey pair
     */
    public int getPlace() {
        return place;
    }

    /**
     * Returns the funds of a spectator
     *
     *   @return a spectator funds
     */
    public int getFunds() {
        return funds;
    }


    /**
     * Returns a spec ID
     *
     *   @return spec ID
     */
    public int getSpecId() {
        return specId;
    }

    /**
     * Returns a horse odds
     *
     *   @return horse odd
     */
    public double getOdd() {
        return odd;
    }

    /**
     *
     */
    public int getHorseIter(){
        return horseIter;
    }


    /**********************Paddock***********************/
    /**
     *
     */
    public boolean getLastToPaddock(){
        return lastToPaddock;
    }

    /**
     *
     */
    public int getHorseToBet(){
        return horseToBet;
    }

    /**
     *
     */
    public boolean getLastCheckHorses(){
        return lastCheckHorses;
    }


    /*********************Race Track*********************/
    /**
     *
     */
    public boolean getMakeMove(){
        return move;
    }

    /**
     *
     */
    public boolean getRaceFinished(){
        return finished;
    }

    /**
     *
     */
    public int[] getResults(){
        return results;
    }


    /***********************Stable***********************/
    /**
     *
     *  @return Race Number
     */
    public int getRaceNumber(){
        return raceNumber;
    }
}
