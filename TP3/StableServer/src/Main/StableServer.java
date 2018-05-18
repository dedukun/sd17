package Main;

import Communication.ServerCom;
import Auxiliar.Configurations;
import genclass.GenericIO;
import java.net.SocketTimeoutException;

public class StableServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Interface stableProxy = new Interface();;
        ServerCom scon, sconi;

        APS spro;

        scon = new ServerCom(Configurations.ST_PORT);
        scon.start();

        GenericIO.writelnString("Starting Stable");
        GenericIO.writelnString("Listening...");

        while(true) {
            try{
                sconi = scon.accept();
                spro = new APS(sconi, stableProxy);
                spro.start();
            }catch(SocketTimeoutException e){
                if(!stableProxy.isAlive()){
                    break;
                }
            }
        }

        GenericIO.writelnString("Service Terminated");
    }
}
