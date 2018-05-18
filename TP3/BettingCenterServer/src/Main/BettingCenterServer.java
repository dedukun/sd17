package Main;

import Communication.ServerCom;
import Auxiliar.Configurations;
import genclass.GenericIO;
import java.net.SocketTimeoutException;

/**
 * Main File.
 */
public class BettingCenterServer {

    /**
     * Main program.
     * 
     *   @param args the command line arguments
     */
    public static void main(String[] args) {

        Interface betCenProxy = new Interface();
        ServerCom scon, sconi;

        APS spro;

        scon = new ServerCom(Configurations.BC_PORT);
        scon.start();

        GenericIO.writelnString("Starting Betting Center");
        GenericIO.writelnString("Listening...");

        while(true) {
            try{
                sconi = scon.accept();
                spro = new APS(sconi, betCenProxy);
                spro.start();
            }catch(SocketTimeoutException e){
                if(!betCenProxy.isAlive()){
                    break;
                }
            }
        }

        GenericIO.writelnString("Service Terminated");
    }
}
