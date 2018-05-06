package bettingcenterserver.Main;

import bettingcenterserver.Auxiliar.Configurations;
import genclass.GenericIO;
import java.net.SocketTimeoutException;


public class BettingCenterServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        APS betCenProxy = new APS();;
        ServerCom scon, sconi;

        ServiceProvider spro;

        scon = new ServerCom(Configurations.PORT);
        scon.start();

        GenericIO.writelnString("Starting Betting Center");
        GenericIO.writelnString("Listening...");

        while(true) {
            try{
                sconi = scon.accept();
                spro = new ServiceProvider(sconi, betCenProxy);
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
