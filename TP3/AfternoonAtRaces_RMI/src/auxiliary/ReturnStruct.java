/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliary;

import java.io.Serializable;

/**
 *
 * @author Filipe
 */
public class ReturnStruct implements Serializable{

    private static final long serialVersionUID = -4495216721200724318L;
    
    private TimeVector clk;
    private int ret_int;
    private double ret_dou;
    private boolean ret_bool;
    private int[] ret_int_arr;
    private double[] ret_dou_arr;

    public int[] getRet_int_arr() {
        return ret_int_arr;
    }

    public double[] getRet_dou_arr() {
        return ret_dou_arr;
    }
    
    public ReturnStruct(TimeVector clk){
        this.clk=clk; 
    }
    
    public ReturnStruct(TimeVector clk, int ret_int){
        this.clk=clk; 
        this.ret_int=ret_int;
    }
    
    public ReturnStruct(TimeVector clk, double ret_dou){
        this.clk=clk; 
        this.ret_dou=ret_dou;
    }
    
    public ReturnStruct(TimeVector clk, boolean ret_bool){
        this.clk=clk; 
        this.ret_bool=ret_bool;
    }
    
    public ReturnStruct(TimeVector clk, int[] ret_int_arr){
        this.clk=clk; 
        this.ret_int_arr=ret_int_arr;
    }
    
    public ReturnStruct(TimeVector clk, double[] ret_dou_arr){
        this.clk=clk; 
        this.ret_dou_arr=ret_dou_arr;
    }

    public TimeVector getClk() {
        return clk;
    }

    public int getRet_int() {
        return ret_int;
    }

    public double getRet_dou() {
        return ret_dou;
    }

    public boolean isRet_bool() {
        return ret_bool;
    }

}
