package Main;

import Communication.ServerCom;
import Auxiliar.Configurations;
import genclass.GenericIO;
import java.net.SocketTimeoutException;

public class RaceTrackServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        APS raceProxy = new APS();;
        ServerCom scon, sconi;

        ServiceProvider spro;

        scon = new ServerCom(Configurations.RT_PORT);
        scon.start();

        GenericIO.writelnString("Starting Race Track");
        GenericIO.writelnString("Listening...");

        while(true) {
            try{
                sconi = scon.accept();
                spro = new ServiceProvider(sconi, raceProxy);
                spro.start();
            }catch(SocketTimeoutException e){
                if(!raceProxy.isAlive()){
                    break;
                }
            }
        }

        GenericIO.writelnString("Service Terminated");
    }  
}
