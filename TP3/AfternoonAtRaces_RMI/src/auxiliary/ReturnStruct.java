package auxiliary;

import java.io.Serializable;

/**
 * Structure used pass values between machines
 */
public class ReturnStruct implements Serializable{

    private static final long serialVersionUID = -4495216721200724318L;
    
    private TimeVector clk;
    private int ret_int;
    private double ret_dou;
    private boolean ret_bool;
    private int[] ret_int_arr;
    private double[] ret_dou_arr;

    /**
     * Get int array from structure
     * 
     *   @return Int Array
     */
    public int[] getRet_int_arr() {
        return ret_int_arr;
    }

    /**
     * Get double array from structure
     * 
     *   @return Double Array
     */
    public double[] getRet_dou_arr() {
        return ret_dou_arr;
    }
    
    /**
     *  Initialize return structure
     * 
     *    @param clk TimeVector
     */
    public ReturnStruct(TimeVector clk){
        this.clk=clk; 
    }
    
    /**
     *  Initialize return structure
     * 
     *    @param clk TimeVector
     *    @param ret_int Int value
     */
    public ReturnStruct(TimeVector clk, int ret_int){
        this.clk=clk; 
        this.ret_int=ret_int;
    }
    
    /**
     *  Initialize return structure
     * 
     *    @param clk TimeVector
     *    @param ret_dou Double value
     */
    public ReturnStruct(TimeVector clk, double ret_dou){
        this.clk=clk; 
        this.ret_dou=ret_dou;
    }
    
    /**
     *  Initialize return structure
     * 
     *    @param clk TimeVector
     *    @param ret_bool Boolean value
     */
    public ReturnStruct(TimeVector clk, boolean ret_bool){
        this.clk=clk; 
        this.ret_bool=ret_bool;
    }
   
    /**
     *  Initialize return structure
     * 
     *    @param clk TimeVector
     *    @param ret_int_arr Int array
     */
    public ReturnStruct(TimeVector clk, int[] ret_int_arr){
        this.clk=clk; 
        this.ret_int_arr=ret_int_arr;
    }
    
    /**
     *  Initialize return structure
     * 
     *    @param clk TimeVector
     *    @param ret_dou_arr Double array
     */
    public ReturnStruct(TimeVector clk, double[] ret_dou_arr){
        this.clk=clk; 
        this.ret_dou_arr=ret_dou_arr;
    }

    /**
     * Get clock from structure
     * 
     *   @return Clock
     */
    public TimeVector getClk() {
        return clk;
    }

    /**
     * Get int value from structure
     * 
     *   @return int
     */
    public int getRet_int() {
        return ret_int;
    }

    /**
     * Get double value from structure
     * 
     *   @return double
     */
    public double getRet_dou() {
        return ret_dou;
    }

    /**
     * Get boolean value from structure
     * 
     *   @return boolean
     */
    public boolean isRet_bool() {
        return ret_bool;
    }

}
