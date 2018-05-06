package stableserver.Main;

import stableserver.Auxiliar.Configurations;
import genclass.GenericIO;
import java.net.SocketTimeoutException;

public class StableServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        APS stableProxy = new APS();;
        ServerCom scon, sconi;

        ServiceProvider spro;

        scon = new ServerCom(Configurations.PORT);
        scon.start();

        GenericIO.writelnString("Starting Stable");
        GenericIO.writelnString("Listening...");

        while(true) {
            try{
                sconi = scon.accept();
                spro = new ServiceProvider(sconi, stableProxy);
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
