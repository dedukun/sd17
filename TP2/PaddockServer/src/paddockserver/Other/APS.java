/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paddockserver.Other;

/**
 *
 * @author Filipe
 */
public class APS {

    private final Paddock pd;
    public APS(){
        pd = new Paddock();
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
                pd.goCheckHorses();
                reply = new Message("Stuff");
                break;
            case "TODO1":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                pd.lastArrivedToPaddock();
                reply = new Message("Stuff");
                break;
            case "TODO2":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                pd.lastCheckHorses();
                reply = new Message("Stuff");
                break;
             case "TODO3":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                pd.proceedToPaddock();
                reply = new Message("Stuff");
                break;
            case "TODO4":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                pd.unblockGoCheckHorses();
                reply = new Message("Stuff");
                break;
            default:
                break;
        }
        
        return reply;
    }
    
    
}
