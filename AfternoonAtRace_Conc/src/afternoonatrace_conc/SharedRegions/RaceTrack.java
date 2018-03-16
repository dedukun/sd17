package afternoonatrace_conc.SharedRegions;

/**
 *
 */
public class RaceTrack {

    /**
     * Reference to General Repository
     */
    private GeneralRepository genRepos;

    /**
     * Race Track initialization
     *
     *  @param genRepos Reference to General Repository
     */
    public RaceTrack(GeneralRepository genRepos){
        this.genRepos = genRepos;
    }

    public static void unblockMakeAMove(){

    }

    public static void makeAMove(){

    }

    public static boolean lastMakeAMove(){
        return true;
    }

    public static boolean hasRaceFinished(){
        return true;
    }

    public static void proceedToStartLine(){

    }

    public static boolean hasFinishLineBeenCrossed(){
        return true;
    }

}
