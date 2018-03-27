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

    /**
    * Broker state.
    */
    private Broker.States brkState;

    /**
    * Spectators state.
    */
    private Spectators.States[] specState = new Spectators.States[SimulPar.S];

    /**
    * Spectators wallet.
    */
    private double[] specMoney = new double[SimulPar.S];

    /**
    * Horses state.
    */
    private HorseJockey.States[] horseState = new HorseJockey.States[SimulPar.C];

    /**
    * Horses agility.
    */
    private double[] horseAgility = new double[SimulPar.C];

    /**
    * Name of the log file.
    */
    private String filename;

    /**
    * File declaration.
    */
    private TextFile log = new TextFile();

    /**
    * Race number happening.
    */
    private int raceNumber;

    /**
    * Track size of the race in moment.
    */
    private int trackSize;

    /**
    * Horse betted by spectators.
    */
    private int[] betS = new int[SimulPar.S];

    /**
    * Ammount betted by spectators.
    */
    private int[] betA = new int [SimulPar.S];

    /**
    * Horse's probability of winning.
    */
    private double[] odds = new double[SimulPar.C];

    /**
    * Current horse iteration.
    */
    private int[] horseIteration = new int [SimulPar.C];

    /**
    * Current horse position relative to the track.
    */
    private int[] horsePosition = new int [SimulPar.C];

    /**
    * Horses that have finished.
    */
    private int[] horseEnd = new int[SimulPar.C];

    /**
    * First line to print.
    */
    private String line1="";

    /**
    * Second line to print.
    */
    private String line2="";

    /**
    * General Repository initialization.
    */
    public GeneralRepository(){
        this.filename="Log.txt";
    }

    /**
    * Prints header and the first line of the logger file.
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

    /**
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
     * Set the Horse/Jockey pair selected by the Spectator to bet on.
     *
     *   @param specId Identifier of the Spectator
     *   @param horseId Identifier of the Horse/Jockey pair
     */
    public synchronized void setBetS(int specId, int horseId){
        this.betS[specId]=horseId;
        //updateLog();
    }

    /**
     * Set the ammount betted by a specified Spectator in the current race.
     *
     *   @param specId Identifier of the Spectator
     *   @param betamount Amount betted
     */
    public synchronized void setBetA(int specId, int betamount){
        this.betA[specId]=+(int)betamount;
        //updateLog();
    }

    /**
     * Set the probability of winning for the given horse.
     *
     *   @param horseId Identifier of the Horse/Jockey pair
     *   @param odd Chance of winning in percentage
     */
    public synchronized void setOdds(int horseId, int odd){
        this.odds[horseId] = odd;
        //updateLog();
    }

    /**
     * Increment interation number of the Horse/Jockey pair.
     *
     *   @param horseId Identifier of the Horse/Jockey pair
     */
    public synchronized void setHorseIteration(int horseId){
        this.horseIteration[horseId]++;
        //updateLog();
    }

    /**
     * Set the specified Horse/Jockey pair current position in the ocurring race.
     *
     *   @param horseId Identifier of the Horse/Jockey pair
     *   @param pos Current position
     */
    public synchronized void setHorsePosition(int horseId, int pos){
        this.horsePosition[horseId]=pos;
        //updateLog();
    }

    /**
     * Set the Horse/Jockey pairs that have already finished the race.
     *
     *   @param horseId Identifier of the Horse/Jockey pair
     */
    public synchronized void setHorseEnd(int horseId){
        this.horseEnd[horseId]=horseId;
        //updateLog();
    }

    /**
     * Set the current race track size.
     *
     *   @param size size of the track
     */
    public synchronized void setTrackSize(int size){
        this.trackSize=size;
        //updateLog();
    }

    /**
     * Set the current race number.
     *
     *   @param num number race being run
     */
    public synchronized void setRaceNumber(int num){
        this.raceNumber = num;
        //updateLog();
    }

    /**
     * Set the current state of the Broker.
     *
     *   @param state state of the Broker
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
     * Set the current state of the specified Spectator.
     *
     *   @param specId ID of the Spectator
     *   @param state state of the Spectator
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
     * Set the current funds of the specified Spectator.
     *
     *   @param specId ID of the Spectator
     *   @param funds funds of the Spectator
     */
    public synchronized void setSpectatorMoney(int specId,double funds){
        this.specMoney[specId]=(int)funds;
        //updateLog();
    }

    /**
     * Set the current state of the specified Horse/Jockey pair.
     *
     *   @param horseId ID of the Horse
     *   @param state state of the Horse/Jockey pair.
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
     * Set the agility of the specified Horse/Jockey pair.
     *
     *   @param horseId ID of the Horse
     *   @param horseAgl agility of the Horse/Jockey pair.
     */
    public synchronized void setHorseAgility(int horseId,int horseAgl){
        this.horseAgility[horseId]=horseAgl;
        //updateLog();
    }
}
