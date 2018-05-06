package Main;

import Communication.ServerCom;
import Auxiliar.Configurations;
import java.net.SocketTimeoutException;
import genclass.GenericIO;

public class GenReposServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        APS genRepoProxy = new APS();;
        ServerCom scon, sconi;

        ServiceProvider spro;

        scon = new ServerCom(Configurations.GR_PORT);
        scon.start();

        GenericIO.writelnString("Starting General Repository");
        GenericIO.writelnString("Listening...");

        while(true) {
            try{
                sconi = scon.accept();
                spro = new ServiceProvider(sconi, genRepoProxy);
                spro.start();
            }catch(SocketTimeoutException e){
                if(!genRepoProxy.isAlive()){
                    break;
                }
            }
        }

        GenericIO.writelnString("Service Terminated");
    }
}
