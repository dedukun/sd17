package afternoonatrace_conc.SharedRegions;
import afternoonatrace_conc.Entities.*;
import afternoonatrace_conc.Main.SimulPar;
import java.util.Arrays;
import java.text.DecimalFormat;
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
    private BrokerStates brkState;

    /**
    * Spectators state.
    */
    private SpectatorsStates[] specState = new SpectatorsStates[SimulPar.S];

    /**
    * Spectators wallet.
    */
    private int[] specMoney = new int[SimulPar.S];

    /**
    * Horses state.
    */
    private HorseJockeyStates[] horseState = new HorseJockeyStates[SimulPar.C];

    /**
    * Horses agility.
    */
    private int[] horseAgility = new int[SimulPar.C];

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

        // Set some initial values
        Arrays.fill(betS, -1);
        Arrays.fill(horseEnd, -1);
    }

    /**
    * Prints header of the logger file.
    */
    public synchronized void initLog(){
        if (!log.openForWriting(".", filename)) {
            GenericIO.writelnString("Operation " + filename + " failed!");
            System.exit(1);
        }

        log.writelnString("    AFTERNOON AT THE RACE TRACK - Description of the internal state of the problem");
        log.writelnString("");
        log.writelnString("MAN/BRK      SPECTATOR/BETTER             HORSE/JOCKEY PAIR at Race RN");
        log.writelnString(" Stat  St0  Am0 St1  Am1 St2  Am2 St3  Am3 RN St0 Len0 St1 Len1 St2 Len2 St3 Len3");
        log.writelnString("                                   Race RN Status");
        log.writelnString("RN Dist BS0  BA0 BS1  BA1 BS2  BA2 BS3  BA3  Od0 N0 Ps0 SD0 Od1 N1 Ps1 Sd1 Od2 N2 Ps2 Sd2 Od3 N3 Ps3 Sd3");
        log.writelnString(" ####  ### #### ### #### ### #### ### ####  # ###  ##  ###  ##  ###  ##  ###  ##");
        log.writelnString(" #  ##   #  ####  #  ####  #  ####  #  #### #### ##  ##  # #### ##  ##  # #### ##  ##  # #### ##  ##  #");

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

        // Doubles formatter
        DecimalFormat formatter = new DecimalFormat("#0.0");

        // Some print logic
        String[] tmpHorseState = new String[SimulPar.C];
        for(int i = 0; i < SimulPar.C; i++){
            if(horseState[i] != null && brkState != BrokerStates.OTE)
                tmpHorseState[i] = horseState[i].toString();
            else
                tmpHorseState[i] = "---";
        }

        String[] tmpHorseAgility = new String[SimulPar.C];
        for(int i = 0; i < SimulPar.C; i++){
            if(horseAgility[i] != 0)
                tmpHorseAgility[i] = "" + horseAgility[i];
            else
                tmpHorseAgility[i] = "--";
        }

        String tmpTrackSize = "";
        if(trackSize == 0)
            tmpTrackSize = "--";
        else
            tmpTrackSize = "" + trackSize;

        String[] tmpBetS = new String[SimulPar.S];
        for(int i = 0; i < SimulPar.S; i++){
            if(betS[i] != -1)
                tmpBetS[i] = Integer.toString(betS[i]);
            else
                tmpBetS[i] = "-";
        }

        String[] tmpBetA = new String[SimulPar.S];
        for(int i = 0; i < SimulPar.S; i++){
            if(betA[i] != 0)
                tmpBetA[i] = Integer.toString(betA[i]);
            else
                tmpBetA[i] = "----";
        }

        String[] tmpOdds = new String[SimulPar.C];
        for(int i = 0; i < SimulPar.C; i++){
            if(odds[i] != 0){
                tmpOdds[i] = formatter.format(odds[i]);
            }
            else
                tmpOdds[i] = "----";
        }

        String[] tmpHorseIteration = new String[SimulPar.C];
        for(int i = 0; i < SimulPar.C; i++){
            if(horseIteration[i] != 0)
                tmpHorseIteration[i] = Integer.toString(horseIteration[i]);
            else
                tmpHorseIteration[i] = "--";
        }

        String[] tmpHorsePosition = new String[SimulPar.C];
        for(int i = 0; i < SimulPar.C; i++){
            if(horsePosition[i] != 0)
                tmpHorsePosition[i] = Integer.toString(horsePosition[i]);
            else
                tmpHorsePosition[i] = "--";
        }

        String[] tmpHorseEnd = new String[SimulPar.C];
        for(int i = 0; i < SimulPar.C; i++){
            if(horseEnd[i] != -1)
                tmpHorseEnd[i] = Integer.toString(horseEnd[i]);
            else
                tmpHorseEnd[i] = "-";
        }

        String[] tmpSpecState = new String[SimulPar.S];
        for(int i = 0; i < SimulPar.S; i++){
            if(specState[i] != null)
                tmpSpecState[i] = specState[i].toString();
            else
                tmpSpecState[i] = "---";
        }

        String tmpBrkState = "";
        if(brkState == null)
            tmpBrkState = "----";
        else
            tmpBrkState = brkState.toString();

        log.writelnString(" "+String.format("%4s",brkState)+"  "+String.format("%3s",tmpSpecState[0])+" "+String.format("%4s",specMoney[0])+" "+String.format("%3s",tmpSpecState[1])+" "+String.format("%4s",specMoney[1])+" "+String.format("%3s",tmpSpecState[2])+" "+String.format("%4s",specMoney[2])+" "+String.format("%3s",tmpSpecState[3])+" "+String.format("%4s",specMoney[3])+"  "+raceNumber+" "+String.format("%3s",tmpHorseState[0])+"  "+String.format("%2s",tmpHorseAgility[0])+"  "+String.format("%3s",tmpHorseState[1])+"  "+String.format("%2s",tmpHorseAgility[1])+"  "+String.format("%3s",tmpHorseState[2])+"  "+String.format("%2s",tmpHorseAgility[2])+"  "+String.format("%3s",tmpHorseState[3])+"  "+String.format("%2s",tmpHorseAgility[3]));
        log.writelnString(" "+raceNumber+"  "+String.format("%2s",tmpTrackSize)+"   "+tmpBetS[0]+"  "+String.format("%4s",tmpBetA[0])+"  "+tmpBetS[1]+"  "+String.format("%4s",tmpBetA[1])+"  "+tmpBetS[2]+"  "+String.format("%4s",tmpBetA[2])+"  "+tmpBetS[3]+"  "+String.format("%4s",tmpBetA[3])+" "+String.format("%4s",String.format("%4s",tmpOdds[0]))+" "+String.format("%2s",tmpHorseIteration[0])+"  "+String.format("%2s",tmpHorsePosition[0])+"  "+tmpHorseEnd[0]+" "+String.format("%4s",String.format("%4s",tmpOdds[1]))+" "+String.format("%2s",tmpHorseIteration[1])+"  "+String.format("%2s",tmpHorsePosition[1])+"  "+tmpHorseEnd[1]+" "+String.format("%4s",String.format("%4s",tmpOdds[2]))+" "+String.format("%2s",tmpHorseIteration[2])+"  "+String.format("%2s",tmpHorsePosition[2])+"  "+tmpHorseEnd[2]+" "+String.format("%4s",String.format("%4s",tmpOdds[3]))+" "+String.format("%2s",tmpHorseIteration[3])+"  "+String.format("%2s",tmpHorsePosition[3])+"  "+tmpHorseEnd[3]);

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
        this.betA[specId] = betamount;
        //updateLog();
    }

    /**
     * Set the probability of winning for the given horse.
     *
     *   @param horseId Identifier of the Horse/Jockey pair
     *   @param odd Chance of winning in percentage
     */
    public synchronized void setOdds(int horseId, double odd){
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
        horsePosition[horseId] = pos;
        //updateLog();
    }

    /**
     * Set the Horse/Jockey pairs that have already finished the race.
     *
     *   @param horseId Identifier of the Horse/Jockey pair
     *   @param place Place that the given Horse/Jockey pair finished the race
     */
    public synchronized void setHorseEnd(int horseId, int place){
        this.horseEnd[horseId]=place;
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

        // Reset variables for the new race.
        horseState = new HorseJockeyStates[SimulPar.C];
        Arrays.fill(horseAgility, 0);
        trackSize = 0;
        Arrays.fill(betS, -1);
        Arrays.fill(betA, 0);
        Arrays.fill(odds, 0);
        Arrays.fill(horseIteration, 0);
        Arrays.fill(horsePosition, 0);
        Arrays.fill(horseEnd, -1);

        this.raceNumber = num;
        //updateLog();
    }

    /**
     * Set the current state of the Broker.
     *
     *   @param state state of the Broker
     */
    public synchronized void setBrokerState(BrokerStates state){
        this.brkState=state;

        updateLog();
    }

    /**
     * Set the current state of the specified Spectator.
     *
     *   @param specId ID of the Spectator
     *   @param state state of the Spectator
     */
    public synchronized void setSpectatorState(int specId,SpectatorsStates state){
        this.specState[specId]=state;

        if(brkState != null)
            updateLog();
    }

    /**
     * Set the current funds of the specified Spectator.
     *
     *   @param specId ID of the Spectator
     *   @param funds funds of the Spectator
     */
    public synchronized void setSpectatorMoney(int specId, int funds){
        this.specMoney[specId]=funds;
        //updateLog();
    }

    /**
     * Set the current state of the specified Horse/Jockey pair.
     *
     *   @param horseId ID of the Horse
     *   @param state state of the Horse/Jockey pair.
     */
    public synchronized void setHorseState(int horseId,HorseJockeyStates state){
        this.horseState[horseId]=state;

        if(brkState != null)
            updateLog();
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
