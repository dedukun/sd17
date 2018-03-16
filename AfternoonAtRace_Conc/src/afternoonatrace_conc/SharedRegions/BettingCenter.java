package afternoonatrace_conc.SharedRegions;

/**
 *
 */
public class BettingCenter {

    /**
     * Reference to General Repository
     */
    private GeneralRepository genRepos;

    /**
     * Betting Center initialization
     *
     *  @param genRepos Reference to General Repository
     */
    public BettingCenter(GeneralRepository genRepos){
        this.genRepos = genRepos;
    }

    public static boolean acceptAllBets(){
        return true;
    }

    public static void acceptTheBet(){

    }

    public static boolean honourAllTheBets(){
        return true;
    }

    public static void hounourTheBet(){

    }

    public static void placeABet(){

    }

    public static void goCollectTheGains(){

    }
}
