/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.tylerwest.greenhouse;

import ca.tylerwest.greenhouse.components.TemperatureHumiditySensor;
import ca.tylerwest.greenhouse.components.WindowMotor;
import ca.tylerwest.greenhouse.controllers.WindowController;
import ca.tylerwest.greenhouse.controllers.ZoneController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tyler
 */
public class Greenhouse {
    
    private static Greenhouse instance;
    
    private boolean initialized;
    private Properties properties;
    
    private TemperatureHumiditySensor temperatureHumiditySensor;
    private WindowController windowController;
    private ZoneController zoneController;
    
    private Greenhouse() {
        initialized = false;
    }
    
    public static Greenhouse getInstance()
    {
        if (instance == null)
            instance = new Greenhouse();
        return instance;
    }
    
    public boolean isInitialized() {
        return initialized;
    }
    
    private void loadProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException ex) {
            Logger.getLogger(Greenhouse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void createComponents() {
        temperatureHumiditySensor = new TemperatureHumiditySensor(properties.getProperty("Global.TemperatureHumiditySensor.ID"), Integer.valueOf(properties.getProperty("Global.TemperatureHumiditySensor.GPIO")));
        
        List<WindowMotor> windowMotors = new ArrayList<WindowMotor>();
        
        int i = 1;
        String windowMotorID = properties.getProperty(String.format("Global.WindowMotor%s.ID", i));
        while (windowMotorID != null)
        {
            windowMotors.add(new WindowMotor(Integer.valueOf(properties.getProperty(String.format("Global.WindowMotor%s.GPIO", i)))));
            
            i++;
            windowMotorID = properties.getProperty(String.format("Global.WindowMotor%s.ID", i));
        }
        
        windowController = new WindowController(windowMotors);
    }
    
    private void startTimedServices() {
        
    }
    
    public void startup()
    {
        if (!initialized)
        {
            loadProperties();
            createComponents();
            startTimedServices();
            
            initialized = true;
        }
    }
    
    public void shutdown() {
        if (initialized) {
            initialized = false;
        }
    }

    public TemperatureHumiditySensor getTemperatureHumiditySensor() {
        return temperatureHumiditySensor;
    }

    public WindowController getWindowController() {
        return windowController;
    }

    public ZoneController getZoneController() {
        return zoneController;
    }

    public Properties getProperties() {
        return properties;
    }
    
}
