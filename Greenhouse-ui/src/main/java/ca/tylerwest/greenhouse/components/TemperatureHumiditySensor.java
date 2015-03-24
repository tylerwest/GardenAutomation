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
public class TemperatureHumiditySensor {
    private final int GPIO;
    private final String ID;

    /**
     *
     * @param ID
     * @param GPIO
     */
    public TemperatureHumiditySensor(String ID, int GPIO) {
        this.ID = ID;
        this.GPIO = GPIO;
    }
    
    public double getTemperature() {
        return 0.0;
    }
    
    public double getHumidity() {
        return 0.0;
    }
}
