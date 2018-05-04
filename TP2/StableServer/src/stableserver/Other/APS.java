/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stableserver.Other;

/**
 *
 * @author Filipe
 */
public class APS {

    private final Stable st;
    public APS(){
        st = new Stable();
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
                st.entertainTheGuests();
                reply = new Message("Stuff");
                break;
            case "TODO1":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                st.proceedToStable();
                reply = new Message("Stuff");
                break;
            case "TODO2":
                //Processar mensagem
                //Escrever resposta ( se necessário ir buscar parametros para escrever resposta )
                st.summonHorsesToPaddock();
                reply = new Message("Stuff");
                break;
            default:
                break;
        }
        
        return reply;
    }
    
    
}
