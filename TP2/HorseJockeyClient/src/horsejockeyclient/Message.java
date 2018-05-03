/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horsejockeyclient;
import java.io.*;

/**
 *
 * Defines the exchanged messages between Spectator Client and servers
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1107;
    
    private String msgType="";
    private HorseJockeyStates hjstate;
     
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
    *@param hjstateParam Estado do horsejockey
    */
    public Message(String type, HorseJockeyStates hjstateParam){
        msgType = type;
        hjstate = hjstateParam;
    }
}
