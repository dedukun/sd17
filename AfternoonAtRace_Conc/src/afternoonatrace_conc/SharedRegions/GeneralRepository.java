package afternoonatrace_conc.SharedRegions;
import afternoonatrace_conc.Entities.*;
import afternoonatrace_conc.Main.SimulPar;
import genclass.TextFile;
import genclass.GenericIO;

/**
 * General repository.<br>
 * Where the log file is made.
 */
public class GeneralRepository {
    
    /*
    * Broker state.
    */
    private Broker.States brkState;
    
    /*
    * Spectators state.
    */
    private Spectators.States[] specState = new Spectators.States[SimulPar.S];
    
    /*
    * Spectators wallet.
    */
    private double[] specMoney = new double[SimulPar.S];
    
    /*
    * Horses state.
    */
    private HorseJockey.States[] horseState = new HorseJockey.States[SimulPar.C];
    
    /*
    * Horses agility.
    */
    private double[] horseAgility = new double[SimulPar.C];
    
    /*
    * Name of the log file.
    */
    private String filename;
    
    /*
    * File declaration.
    */
    private TextFile log = new TextFile();
    
    /*
    * Race number happening.
    */
    private int raceNumber;
    
    /*
    * Track size of the race in moment.
    */
    private int trackSize;
    
    /*
    * Horse betted by spectators.
    */
    private int[] betS = new int[SimulPar.S];
    
    /*
    * Ammount betted by spectators.
    */
    private int[] betA = new int [SimulPar.S];
    
    /*
    * Horse's probability of winning.
    */
    private double[] odds = new double[SimulPar.C];
    
    /*
    * Current horse iteration.
    */
    private int[] horseIteration = new int [SimulPar.C];
    
    /*
    * Current horse position relative to the track.
    */
    private int[] horsePosition = new int [SimulPar.C];
    
    /*
    * Horses that have finished.
    */
    private int[] horseEnd = new int[SimulPar.C];
    
    /*
    * First line to print.
    */
    private String line1="";
    
    /*
    * Second line to print.
    */
    private String line2="";
    
    /*
    * General Repository initialization.
    */
    public GeneralRepository(){
        this.filename="Log.txt"; 
    }
    
    /*
    * Prints everything into logger file.
    */
    public synchronized void initLog(){
        if (!log.openForWriting(".", filename)) {
            GenericIO.writelnString("Operation " + filename + " failed!");
            System.exit(1);
        }      
        
        log.writelnString("    AFTERNOON AT THE RACE TRACK - Description of the internal state of the problem");
        log.writelnString("");
        log.writelnString("MAN/BRK      SPECTATOR/BETTER             HORSE/JOCKEY PAIR at Race RN");
        log.writelnString("  Stat  St0  Am0 St1  Am1 St2  Am2 St3  Am3 RN St0 Len0 St1 Len1 St2 Len2 St3 Len3");
        log.writelnString("                                   Race RN Status");
        log.writelnString("RN Dist BS0  BA0 BS1  BA1 BS2  BA2 BS3  BA3  Od0 N0 Ps0 SD0 Od1 N1 Ps1 Sd1 Od2 N2 Ps2 Sd2 Od3 N3 Ps3 Sd3");  
        log.writelnString(" ####  ### #### ### #### ### #### ### ####  # ###  ##  ###  ##  ###  ##  ###  ##");
        log.writelnString(" #  ##   #  ####  #  ####  #  ####  #  #### #### ##  #   # #### ##  #   # #### ##  #   # #### ##  #   #");
        log.writelnString("");
        
        if (!log.close()) {
            GenericIO.writelnString("Operation " + filename + " failed!");
            System.exit(1);
        }
    }
    
    /*
    * Updates the log when a change happens.
    */
    public synchronized void updateLog(){
        
        if (!log.openForAppending(".", filename)) {
            GenericIO.writelnString("Operation " + filename + " failed!");
            System.exit(1);
        }
        
        log.writelnString(" "+String.format("%4s",brkState)+"  "+String.format("%3s",specState[0])+" "+String.format("%4s",String.format("%.1f",specMoney[0]))+" "+String.format("%3s",specState[1])+" "+String.format("%4s",String.format("%.1f",specMoney[1]))+" "+String.format("%3s",specState[2])+" "+String.format("%4s",String.format("%.1f", specMoney[2]))+" "+String.format("%3s",specState[3])+" "+String.format("%4s",String.format("%.1f",specMoney[3]))+"  "+raceNumber+" "+String.format("%3s",horseState[0])+"  "+String.format("%2s",horseAgility[0])+"  "+String.format("%3s",horseState[1])+"  "+String.format("%2s",horseAgility[1])+"  "+String.format("%3s",horseState[2])+"  "+String.format("%2s",horseAgility[2])+"  "+String.format("%3s",horseState[3])+"  "+String.format("%2s",horseAgility[3]));
        log.writelnString(" "+raceNumber+"  "+String.format("%2s",trackSize)+"   "+betS[0]+"  "+String.format("%4s",betA[0])+"  "+betS[1]+"  "+String.format("%4s",betA[1])+"  "+betS[2]+"  "+String.format("%4s",betA[2])+"  "+betS[3]+"  "+String.format("%4s",betA[3])+" "+String.format("%4s",String.format("%.1f",odds[0]))+" "+String.format("%2s",horseIteration[0])+"  "+String.format("%2s",horsePosition[0])+"   "+horseEnd[0]+" "+String.format("%4s",String.format("%.1f",odds[1]))+" "+String.format("%2s",horseIteration[1])+"  "+String.format("%2s",horsePosition[1])+"   "+horseEnd[1]+" "+String.format("%4s",String.format("%.1f",odds[2]))+" "+String.format("%2s",horseIteration[2])+"  "+String.format("%2s",horsePosition[2])+"   "+horseEnd[2]+" "+String.format("%4s",String.format("%.1f",odds[3]))+" "+String.format("%2s",horseIteration[3])+"  "+String.format("%2s",horsePosition[3])+"   "+horseEnd[3]);  
        
        if (!log.close()) {
            GenericIO.writelnString("Operation  " + filename + " failed!");
            System.exit(1);
        }
    }
    
    /**
     * Bet Selections
     * @param specid id of spectator
     * @param horseid id of the horse that was the pick of the spectator
     */
    public synchronized void setBetS(int specid, int horseid){
        this.betS[specid]=horseid;
        //updateLog();
    }
    
    /**
     * Bet Amount
     * @param specid id of spectator
     * @param betamount amount betted by the spectator
     */
    public synchronized void setBetA(int specid, int betamount){
        this.betA[specid]=+(int)betamount;
        //updateLog();
    }
    
    /**
     * Odd
     */
    public synchronized void setOdds(){
        boolean calc=true;
        double divider=0;
        for (double i : horseAgility)
            if(i==0) calc = false;
        
        
        if(calc){
            for( double j : horseAgility)
                divider+=j;
            
            for(int i=0; i<SimulPar.C;i++){
                this.odds[i]=horseAgility[i]/divider;
            }
        }
        //updateLog();
    }
    
    /**
     * Horse Iteration
     * @param horseid id of spectator
     */
    public synchronized void setHorseIteration(int horseid){
        this.horseIteration[horseid]++;
        //updateLog();
    }
    
    /**
     * Horse Position
     * @param horseid id of spectator
     * @param pos amount betted by the spectator
     */
    public synchronized void setHorsePosition(int horseid, int pos){
        this.horsePosition[horseid]=pos;
        //updateLog();
    }
    
    /**
     * Horse have finished
     * @param horseid id of spectator
     */
    public synchronized void setHorseEnd(int horseid){
        this.horseEnd[horseid]=horseid;
        //updateLog();
    }
    
    /**
     * Track Size
     * @param size size of the track
     */
    public synchronized void setTrackSize(int size){
        this.trackSize=size;
        //updateLog();
    }
    
    /**
     * Race number
     * @param num number of the track
     */
    public synchronized void setRaceNumber(){
        this.raceNumber++;
        //updateLog();
    }
    
    /**
     * Broker State
     * @param state state of the Broker
     */
    public synchronized void setBrokerState(Broker.States state){
        this.brkState=state;
        
        boolean print =true;
        for (HorseJockey.States i : horseState)
            if(i==null) print = false;
        
        for (Spectators.States i : specState)
            if(i==null) print = false;
        
        if(print) updateLog();
    }
    
    /**
     * Spectator state
     * @param specId ID of the Spectator
     * @param state state of the Spectator
     */
    public synchronized void setSpectatorState(int specId,Spectators.States state){
        this.specState[specId]=state;
        boolean print = true;
        
        for (HorseJockey.States i : horseState)
            if(i==null) print = false;
        
        for (Spectators.States i : specState)
            if(i==null) print = false;
     
        if(print) updateLog();
    }
    
    /**
     * Spectator state
     * @param specId ID of the Spectator
     * @param funds funds of the Spectator
     */
    public synchronized void setSpectatorMoney(int specId,double funds){
        this.specMoney[specId]=(int)funds;
        //updateLog();
    }
    
    /**
     * Horse Jockey state
     * @param horseId ID of the Horse
     * @param state state of the Horse/Jockey pair.
     */
    public synchronized void setHorseState(int horseId,HorseJockey.States state){
        this.horseState[horseId]=state;
        
        boolean print = true;
        
        for (HorseJockey.States i : horseState)
            if(i==null) print = false;
        
        for (Spectators.States i : specState)
            if(i==null) print = false;
        
        if(print) updateLog();
    }
    
    /**
     * Horse Jockey Agility
     * @param horseId ID of the Horse
     * @param agi agility of the Horse/Jockey pair.
     */
    public synchronized void setHorseAgility(int horseId,int agi){
        this.horseAgility[horseId]=agi;
        //updateLog();
    }
    
    
    
    
}
