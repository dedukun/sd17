/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genreposserver.Other;

/**
 *
 * @author Filipe
 */
public class APS {

    private final GeneralRepository gr;
    public APS(){
        gr = new GeneralRepository();
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
                gr.initLog();
                reply = new Message("Stuff");
                break;
            case "TODO1":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                gr.setBetA(msg.getSpecID(),msg.getBetAmount());
                reply = new Message("Stuff");
                break;
            case "TODO2":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                gr.setBetS(msg.getSpecID(),msg.getHorseID());
                reply = new Message("Stuff");
                break;
             case "TODO3":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                gr.setBrokerState(msg.getBrokerState());
                reply = new Message("Stuff");
                break;
            case "TODO4":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                gr.setHorseAgility(msg.getHorseID(),msg.getHorseAgl());
                reply = new Message("Stuff");
                break;
            case "TODO5":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                gr.setHorseEnd(msg.getHorseID(),msg.getPlace());
                reply = new Message("Stuff");
                break;
            case "TODO6":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                gr.setHorseIteration(msg.getHorseID(),msg.getPos());
                reply = new Message("Stuff");
                break;
            case "TODO7":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                gr.setHorseAgility(msg.getHorseID(),msg.getHorseState());
                reply = new Message("Stuff");
                break;
            case "TODO8":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                gr.setHorseAgility(msg.getHorseID(),msg.getOdd());
                reply = new Message("Stuff");
                break;
            case "TODO9":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                gr.setRaceNumber(msg.getNum());
                reply = new Message("Stuff");
                break;
            case "TODO10":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                gr.setSpectatorMoney(msg.getSpecID(),msg.getFunds());
                reply = new Message("Stuff");
                break;
            case "TODO11":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                gr.setSpectatorState(msg.getSpecID(),msg.getSpecatorState());
                reply = new Message("Stuff");
                break;
            case "TODO12":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                gr.setTrackSize(msg.getSize());
                reply = new Message("Stuff");
                break;
            case "TODO13":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                gr.updateLog();
                reply = new Message("Stuff");
                break;
            default:
                break;
        }
        
        return reply;
    }
    
    
}
