/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlcenterserver;

/**
 *
 * @author Filipe
 */
public class APS {

    private final ControlCenter cc;
    public APS(){
        cc = new ControlCenter();
    }
    /*
    *Processes the message and returns a reply
    *
    *@param msg Mesage to be processed
    *
    */
    public Message compute(Message msg) throws Exception{
        Message reply = null;
        switch(msg.getType()){
            case "TODO":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                cc.entertainTheGuests();
                reply = new Message("Stuff");
                break;
            case "TODO1":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                cc.goWatchTheRace();
                reply = new Message("Stuff");
                break;
            case "TODO2":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                cc.haveIWon(msg.getHJID());
                reply = new Message("Stuff");
                break;
             case "TODO3":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                cc.relaxABit();
                reply = new Message("Stuff");
                break;
            case "TODO4":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                cc.reportResults();
                reply = new Message("Stuff");
                break;
            case "TODO5":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                cc.startTheRace();
                reply = new Message("Stuff");
                break;
            case "TODO6":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                cc.summonHorsesToPaddock();
                reply = new Message("Stuff");
                break;
            case "TODO7":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                cc.unblockGoCheckHorses();
                reply = new Message("Stuff");
                break;
            case "TODO8":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                cc.unblockMakeAMove();
                reply = new Message("Stuff");
                break;
            case "TODO9":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                cc.unblockProceedToPaddock();
                reply = new Message("Stuff");
                break;
            case "TODO10":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                cc.waitForNextRace();
                reply = new Message("Stuff");
                break;
            default:
                break;
        }
        
        return reply;
    }
    
    
}
