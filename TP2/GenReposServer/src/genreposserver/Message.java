/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genreposserver;
import java.io.*;

/**
 *
 * Defines the exchanged messages between General Repository Server and clients.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1032;
    
    private String msgType="";
    private BrokerStates bstate;
    private HorseJockeyStates hjstate;
    private SpectatorStates sstate;
    private int param1;
    private int param2;
    private int horseId;
    private int specId;
     
    /*
    *
    *@param type Nome da função
    */
    public Message(String type){
        msgType = type;
    }
    
    /*
    *
    *@param type Nome da função
    *@param param1 First parameter of fucntion(specId/horseId/specId)
    *@param param2 Second parameter of function (betamount/horseId/horseAgl/place/pos/funds)
    */
    public Message(String type, int param1, int param2){
        msgType = type;
        this.param1=param1;
        this.param2=param2;
    }
    
    /*
    *
    *@param type Nome da função
    *@param param1 Parameter of fucntion(num/size)
    */
    public Message(String type, int param1){
        msgType = type;
        this.param1 = param1;
    }
    
    /*
    *
    *@param type Nome da função
    *@param bstateParam Broker state
    */
    public Message(String type, BrokerStates bstateParam){
        msgType = type;
        bstate = bstateParam;
    }
    
    /*
    *
    *@param type Nome da função
    *@param horseidParam Horse ID
    *@param bstateParam Broker state
    */
    public Message(String type, int horseidParam, HorseJockeyStates hjstateParam){
        msgType = type;
        horseId = horseidParam;
        hjstate = hjstateParam;
    }
    
    /*
    *
    *@param type Nome da função
    *@param specidParam Horse ID
    *@param sstateParam Specator state
    */
    public Message(String type, int specidParam, SpectatorStates sstateParam){
        msgType = type;
        specId = specidParam;
        sstate = sstateParam;
    }
    
}
