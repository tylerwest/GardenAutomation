package ca.tylerwest.greenhouse.controllers;

import ca.tylerwest.greenhouse.components.SoilMoistureSensor;
import ca.tylerwest.greenhouse.components.SolenoidValve;

public class Zone {
    private final String ID;
    private final SoilMoistureSensor soilMoistureSensor;
    private final SolenoidValve solenoidValve;
    
    private String description;
    private double minimumMoistureLevel;
    private double maximumMoistureLevel;
    private double waterActiveTimeSeconds;
    
    public Zone(String ID, SoilMoistureSensor soilMoistureSensor, SolenoidValve solenoidValve) {
        this.ID = ID;
        this.soilMoistureSensor = soilMoistureSensor;
        this.solenoidValve = solenoidValve;
    }

    public final String getID() {
        return ID;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
		this.description = description;
	}

    public SoilMoistureSensor getSoilMoistureSensor() {
        return soilMoistureSensor;
    }

    public SolenoidValve getSolenoidValve() {
        return solenoidValve;
    }

    public void setMaximumMoistureLevel(double maximumMoistureLevel) {
		this.maximumMoistureLevel = maximumMoistureLevel;
	}
    
    public void setMinimumMoistureLevel(double minimumMoistureLevel) {
		this.minimumMoistureLevel = minimumMoistureLevel;
	}
    
    public void setWaterActiveTimeSeconds(double waterActiveTimeSeconds) {
		this.waterActiveTimeSeconds = waterActiveTimeSeconds;
	}
    
    public double getMaximumMoistureLevel() {
		return maximumMoistureLevel;
	}
    
    public double getMinimumMoistureLevel() {
		return minimumMoistureLevel;
	}
    
    public double getWaterActiveTimeSeconds() {
		return waterActiveTimeSeconds;
	}
}
