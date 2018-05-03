/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stableserver;
import java.io.*;

/**
 *
 * Defines the exchanged messages between Stable Server and clients.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1114;
    
    private String msgType="";
    private int raceNumber;
     
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
    *@param raceNumberParam Number of the actual race
    */
    public Message(String type, int raceNumberParam){
        msgType = type;
        raceNumber=raceNumberParam;
    }
}
