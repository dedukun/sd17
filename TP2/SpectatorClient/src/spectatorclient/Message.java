/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spectatorclient;
import java.io.*;

/**
 *
 * Defines the exchanged messages between Spectator Client and servers
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1107;
    
    private String msgType="";
    private double money;
    private SpectatorStates sstate;
     
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
    *@param moneyParam Spectator funds
    */
    public Message(String type, double moneyParam){
        msgType = type;
        money = moneyParam;
    }
    
    /*
    *
    *@param type Nome da função
    *@param sstateParam Estado do espectador
    */
    public Message(String type, SpectatorStates sstateParam){
        msgType = type;
        sstate = sstateParam;
    }
}
