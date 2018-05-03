/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brokerclient;
import java.io.*;

/**
 *
 * Defines the exchanged messages between Spectator Client and servers
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1108;
    
    private String msgType="";
    private BrokerStates bstate;
     
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
    *@param sstateParam Estado do espectador
    */
    public Message(String type, BrokerStates bstateParam){
        msgType = type;
        bstate = bstateParam;
    }
}
