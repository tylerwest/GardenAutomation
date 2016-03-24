/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.tylerwest.greenhouse.controllers;

import ca.tylerwest.greenhouse.components.SoilMoistureSensor;
import ca.tylerwest.greenhouse.components.SolenoidValve;

/**
 *
 * @author Tyler
 */
public class Zone {
    private final String ID;
    private final String description;
    private final SoilMoistureSensor soilMoistureSensor;
    private final SolenoidValve solenoidValve;
    
    public Zone(String ID, String description, SoilMoistureSensor soilMoistureSensor, SolenoidValve solenoidValve) {
        this.ID = ID;
        this.description = description;
        this.soilMoistureSensor = soilMoistureSensor;
        this.solenoidValve = solenoidValve;
    }

    /**
     * @return the ID
     */
    public String getID() {
        return ID;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the soilMoistureSensor
     */
    public SoilMoistureSensor getSoilMoistureSensor() {
        return soilMoistureSensor;
    }

    /**
     * @return the solenoidValve
     */
    public SolenoidValve getSolenoidValve() {
        return solenoidValve;
    }
    
    
}
