/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afternoonatrace_conc.Entities;

/**
 *
 * 
 */
public class Broker {
    
    private States state;

    public Broker() {
        this.state = States.OPENING_THE_EVENT;
    }

    public void setState(States state) {
        this.state = state;
    }

    public States getState() {
        return state;
    }
    
}
