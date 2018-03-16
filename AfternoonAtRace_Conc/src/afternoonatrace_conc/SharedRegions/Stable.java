package afternoonatrace_conc.SharedRegions;

/**
 *
 */
public class Stable {

    /**
     * Reference to General Repository
     */
    private GeneralRepository genRepos;

    /**
     * Stable initialization
     *
     *  @param genRepos Reference to General Repository
     */
    public Stable(GeneralRepository genRepos){
        this.genRepos = genRepos;
    }

    public static void proceedToStable(){

    }

    public static boolean lastProoceedToPaddock(){
        return true;
    }
}
