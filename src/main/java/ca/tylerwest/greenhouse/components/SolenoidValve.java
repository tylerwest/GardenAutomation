/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.tylerwest.greenhouse.components;

/**
 *
 * @author twest
 */
public class SolenoidValve {
    private final int GPIO;

    public enum State {
        OPEN, CLOSED;
    }
    
    private State state;
    
    public SolenoidValve(int GPIO) {
        this.GPIO = GPIO;
    }
    
    public void open() {
        state = State.OPEN;
    }
    
    public void close() {
        state = State.CLOSED;
    }
    
    public State getState() {
        return state;
    }
}
