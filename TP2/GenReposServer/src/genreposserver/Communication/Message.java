package genreposserver.Communication;

import genreposserver.Other.BrokerStates;
import genreposserver.Other.HorseJockeyStates;
import genreposserver.Other.SpectatorStates;
import java.io.*;

/**
 *
 * Defines the exchanged messages between General Repository Server and clients.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1032;

    private MessageType.GeneralRepository msgType;
    private BrokerStates bstate;
    private HorseJockeyStates hjstate;
    private SpectatorStates sstate;
    private int num;
    private int size;
    private int betAmount;
    private int pos;
    private int horseAgl;
    private int place;
    private int funds;
    private int horseId;
    private int specId;
    private double odd;
       
   /*
    *
    *  @param type Enumerate indicating the type of the message
    */
    public Message(MessageType.GeneralRepository type){
        msgType = type;
    }

    /*
    *
    *  @param type Enumerate indicating the type of the message
    *  @param param1 Parameter of function(num/size)
    */
    public Message(MessageType.GeneralRepository type, int param1){
        msgType = type;
        
        switch(type.toString()) {
            case "SET_RACE_NUMBER" : 
                num = param1;
                break;
            case "SET_TRACK_SIZE":
                size = param1;
                break;
        }
    }

    /*
    *
    *  @param type Enumerate indicating the type of the message
    *  @param param1 First parameter of fucntion(specId/horseId/specId)
    *  @param param2 Second parameter of function (betamount/horseId/horseAgl/place/pos/funds)
    */
    public Message(MessageType.GeneralRepository type, int param1, int param2){
        msgType = type;
        
        switch(type.toString()){
            case "SET_BET_A" :
                specId = param1;
                betAmount = param2;
                break;
            case "SET_BET_S" :
                specId = param1;
                horseId = param2;
                break;
            case "SET_HORSE_POSITION" :
                horseId = param1;
                pos = param2;
                break;
            case "SET_HORSE_AGILITY" :
                horseId = param1;
                horseAgl = param2;
                break;
            case "SET_HORSE_END" :
                horseId = param1;
                place = param2;
                break;
            case "SET_SPECATOR_MONEY" :
                specId = param1;
                funds = param2;
                break;

        }
    }

    /*
    *
    *  @param type Enumerate indicating the type of the message
    *  @param oddParam representing the odds wanted
    */
    public Message(MessageType.GeneralRepository type, int horseidParam, double oddParam){
        msgType = type;
        horseId = horseidParam;
        odd = oddParam;
    }
    
    /*
    *
    *  @param type Enumerate indicating the type of the message
    *  @param bstateParam Broker state
    */
    public Message(MessageType.GeneralRepository type, BrokerStates bstateParam){
        msgType = type;
        bstate = bstateParam;
    }

    /*
    *
    *  @param type Enumerate indicating the type of the message
    *  @param horseidParam Horse ID
    *  @param bstateParam Broker state
    */
    public Message(MessageType.GeneralRepository type, int horseidParam, HorseJockeyStates hjstateParam){
        msgType = type;
        horseId = horseidParam;
        hjstate = hjstateParam;
    }

    /*
    *
    *  @param type Enumerate indicating the type of the message
    *  @param specidParam Horse ID
    *  @param sstateParam Specator state
    */
    public Message(MessageType.GeneralRepository type, int specidParam, SpectatorStates sstateParam){
        msgType = type;
        specId = specidParam;
        sstate = sstateParam;
    }

    /**
     * Returns the enumerate indicating the type of the message.
     *
     *   @return Enumerate indicating the type
     */
    public MessageType.GeneralRepository getMessageType(){
        return msgType;
    }
    
    
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
    public int getNum() {
        return num;
    }

    /**
     * Returns the race track size
     *
     *   @return Race track size
     */
    public int getSize() {
        return size;
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
     * Returns a horse ID
     *
     *   @return horse ID
     */
    public int getHorseId() {
        return horseId;
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
}
