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
public class SoilMoistureSensor {
    private final int GPIO;

    public SoilMoistureSensor(int GPIO) {
        this.GPIO = GPIO;
    }
    
    public double getSoilMoistureLevel() {
        return 0.0;
    }
}
