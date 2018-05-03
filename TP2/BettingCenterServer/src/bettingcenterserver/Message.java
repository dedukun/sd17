/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bettingcenterserver;
import java.io.*;

/**
 *
 * Defines the exchanged messages between Betting Center Server and clients.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 947;
    
    private String msgType="";
    private int[] winningHorses;
    private int horseId;
    private double horseChances;
    
    
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
    *@param winningHorsesParam number of winning horses
    */
    public Message(String type, int[] winningHorsesParam){
        msgType = type;
        winningHorses = winningHorsesParam;
    }
    
    /*
    *
    *@param type Nome da função
    *@param horseIdParam Id of the desired horse
    */
    public Message(String type, int horseIdParam){
        msgType = type;
        horseId = horseIdParam;
    }
    
    /*
    *
    *@param type Nome da função
    *@param horseChancesParam Horses winning chances
    */
    public Message(String type, double horseChancesParam){
        msgType = type;
        horseChances = horseChancesParam;
    }
    
    
}
