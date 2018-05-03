/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racetrackserver;
import java.io.*;

/**
 *
 * Defines the exchanged messages between Race Track Server and clients.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1040;
    
    private String msgType="";
  
    /*
    *
    *@param type Nome da função
    */
    public Message(String type){
        msgType = type;
    }
}
