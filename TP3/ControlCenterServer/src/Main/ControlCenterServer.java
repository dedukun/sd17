package Main;

import Communication.ServerCom;
import Auxiliar.Configurations;
import genclass.GenericIO;
import java.net.SocketTimeoutException;


public class ControlCenterServer {

     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Interface conCenProxy = new Interface();;
        ServerCom scon, sconi;

        APS spro;

        scon = new ServerCom(Configurations.CC_PORT);
        scon.start();

        GenericIO.writelnString("Starting Control Center");
        GenericIO.writelnString("Listening...");

        while(true) {
            try{
                sconi = scon.accept();
                spro = new APS(sconi, conCenProxy);
                spro.start();
            }catch(SocketTimeoutException e){
                if(!conCenProxy.isAlive()){
                    break;
                }
            }
        }

        GenericIO.writelnString("Service Terminated");
    }
}
