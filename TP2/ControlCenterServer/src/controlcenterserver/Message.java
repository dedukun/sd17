/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlcenterserver;
import java.io.*;

/**
 *
 * Defines the exchanged messages between Control Center Server and clients.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1032;
    
    private String msgType="";
    private int[] winners;
    private int hjid;
     
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
    *@param winnersParam number of winning horses
    */
    public Message(String type, int[] winnersParam){
        msgType = type;
        winners = winnersParam;
    }
    
    /*
    *
    *@param type Nome da função
    *@param hjidParam ID of the desired horse/jockey pair
    */
    public Message(String type, int hjidParam){
        msgType = type;
        hjid=hjidParam;
    }
}
