package controlcenterserver.Main;

import controlcenterserver.Auxiliar.Configurations;
import genclass.GenericIO;
import java.net.SocketTimeoutException;


public class ControlCenterServer {

     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        APS conCenProxy = new APS();;
        ServerCom scon, sconi;

        ServiceProvider spro;

        scon = new ServerCom(Configurations.PORT);
        scon.start();

        GenericIO.writelnString("Starting Control Center");
        GenericIO.writelnString("Listening...");

        while(true) {
            try{
                sconi = scon.accept();
                spro = new ServiceProvider(sconi, conCenProxy);
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
