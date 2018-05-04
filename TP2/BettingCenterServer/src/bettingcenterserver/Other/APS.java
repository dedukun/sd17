/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bettingcenterserver.Other;

/**
 *
 * @author Filipe
 */
public class APS {

    private final BettingCenter bc;
    public APS(){
        bc = new BettingCenter();
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
                bc.acceptedAllBets();
                reply = new Message("Stuff");
                break;
            case "TODO1":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                bc.acceptTheBet();
                reply = new Message("Stuff");
                break;
            case "TODO2":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                bc.areThereAnyWinners(msg.getWinningHorses());
                reply = new Message("Stuff");
                break;
             case "TODO3":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                bc.goCollectTheGains();
                reply = new Message("Stuff");
                break;
            case "TODO4":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                bc.honouredAllBets();
                reply = new Message("Stuff");
                break;
            case "TODO5":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                bc.honourTheBet();
                reply = new Message("Stuff");
                break;
            case "TODO6":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                bc.placeABet(msg.getHorseId());
                reply = new Message("Stuff");
                break;
            case "TODO7":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                bc.setHorsesWinningChances(msg.getHorseChances());
                reply = new Message("Stuff");
                break;
            default:
                break;
        }
        
        return reply;
    }
    
    
}
