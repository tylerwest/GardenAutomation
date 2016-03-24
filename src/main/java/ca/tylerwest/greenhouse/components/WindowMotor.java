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
public class WindowMotor {
    private final int GPIO;

    public enum Direction {
        FORWARD, BACKWARD;
    }
    
    public WindowMotor(int GPIO) {
        this.GPIO = GPIO;
    }
    
    public void on(Direction direction) {
        
    }
    
    public void off() {
        
    }
}
