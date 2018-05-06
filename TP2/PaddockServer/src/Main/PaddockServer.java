package Main;

import Communication.ServerCom;
import Auxiliar.Configurations;
import genclass.GenericIO;
import java.net.SocketTimeoutException;

public class PaddockServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        APS paddockProxy = new APS();;
        ServerCom scon, sconi;

        ServiceProvider spro;

        scon = new ServerCom(Configurations.PD_PORT);
        scon.start();

        GenericIO.writelnString("Starting Paddock");
        GenericIO.writelnString("Listening...");

        while(true) {
            try{
                sconi = scon.accept();
                spro = new ServiceProvider(sconi, paddockProxy);
                spro.start();
            }catch(SocketTimeoutException e){
                if(!paddockProxy.isAlive()){
                    break;
                }
            }
        }

        GenericIO.writelnString("Service Terminated");
    }
}
