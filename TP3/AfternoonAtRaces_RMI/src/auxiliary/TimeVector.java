/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliary;

import java.io.Serializable;

/**
 *
 * Vectorial clock to keep track of the order
 */
public class TimeVector implements Serializable {

    private static final long serialVersionUID = 1945916302401871747L;
        
    private int time;
    
    public TimeVector(){
        this.time=0;
    }
    
    public void updateTime(int time){
        this.time=time;
    }
    
    public int getTime(){
        return time;
    }
    
    public void incTime(){
        time++;
    }
}
