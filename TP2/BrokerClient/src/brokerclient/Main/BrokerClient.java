package brokerclient.Main;

public class BrokerClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Broker broker = new Broker("Broker");
        broker.start();

        try{
            broker.join();
        }catch(InterruptedException e){}
    }

}
