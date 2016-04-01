package ca.tylerwest.greenhouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import ca.tylerwest.greenhouse.components.SoilMoistureSensor;
import ca.tylerwest.greenhouse.components.SolenoidValve;
import ca.tylerwest.greenhouse.components.TemperatureHumiditySensor;
import ca.tylerwest.greenhouse.components.WindowMotor;
import ca.tylerwest.greenhouse.controllers.GreenhouseGpioController;
import ca.tylerwest.greenhouse.controllers.WindowController;
import ca.tylerwest.greenhouse.controllers.Zone;
import ca.tylerwest.greenhouse.controllers.ZoneController;
import ca.tylerwest.greenhouse.listeners.GreenhouseTerminationListener;

public class Greenhouse {

	private static Greenhouse instance;

	private boolean initialized;
	private Properties properties;
	private List<GreenhouseTerminationListener> terminationListeners;

	private TemperatureHumiditySensor temperatureHumiditySensor;
	private WindowController windowController;
	private ZoneController zoneController;
	private GreenhouseGpioController greenhouseGpioController;

	private Greenhouse() {
		initialized = false;
		terminationListeners = new ArrayList<GreenhouseTerminationListener>();
	}

	public static Greenhouse getInstance() {
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
		greenhouseGpioController = new GreenhouseGpioController();

		temperatureHumiditySensor = new TemperatureHumiditySensor(
				properties.getProperty("Global.TemperatureHumiditySensor.ID"),
				Integer.valueOf(properties.getProperty("Global.TemperatureHumiditySensor.GPIO")));

		List<WindowMotor> windowMotors = new ArrayList<WindowMotor>();

		int windowMotorNumber = 1;
		String windowMotorID = properties.getProperty(String.format("Global.WindowMotor%s.ID", windowMotorNumber));
		while (windowMotorID != null) {
			Integer forwardGpio = Integer
					.valueOf(properties.getProperty(String.format("Global.WindowMotor%s.Forward.GPIO", windowMotorNumber)));
			Integer backwardGpio = Integer
					.valueOf(properties.getProperty(String.format("Global.WindowMotor%s.Backward.GPIO", windowMotorNumber)));
			WindowMotor motor = new WindowMotor(windowMotorID, forwardGpio, backwardGpio);
			windowMotors.add(motor);

			windowMotorNumber++;
			windowMotorID = properties.getProperty(String.format("Global.WindowMotor%s.ID", windowMotorNumber));
		}

		windowController = new WindowController(windowMotors);
		windowController.setActiveTimeSeconds(
				Double.valueOf(properties.getProperty("Global.WindowController.ActiveTimeSeconds")));

		List<Zone> zones = new ArrayList<Zone>();
		int zoneNumber = 1;
		String zoneID = properties.getProperty(String.format("Zone%s.ID", zoneNumber));
		while (zoneID != null) {
			int soilMoistureSensorGPIO = Integer
					.valueOf(properties.getProperty(String.format("Zone%s.MoistureSensor.GPIO", zoneNumber)));
			int solenoidValveGPIO = Integer
					.valueOf(properties.getProperty(String.format("Zone%s.SolenoidValve.GPIO", zoneNumber)));
			SoilMoistureSensor soilMoistureSensor = new SoilMoistureSensor(soilMoistureSensorGPIO);
			SolenoidValve solenoidValve = new SolenoidValve(solenoidValveGPIO);

			String description = properties.getProperty(String.format("Zone%s.Description", zoneNumber));
			double minimumMoistureLevel = Double.valueOf(
					properties.getProperty(String.format("Zone%s.MoistureSensor.MinimumMoistureLevel", zoneNumber)));
			double maximumMoistureLevel = Double.valueOf(
					properties.getProperty(String.format("Zone%s.MoistureSensor.MaximumMoistureLevel", zoneNumber)));
			double waterActiveTimeSeconds = Double.valueOf(
					properties.getProperty(String.format("Zone%s.SolenoidValve.ActiveTimeSeconds", zoneNumber)));

			Zone zone = new Zone(zoneID, soilMoistureSensor, solenoidValve);
			zone.setDescription(description);
			zone.setMinimumMoistureLevel(minimumMoistureLevel);
			zone.setMaximumMoistureLevel(maximumMoistureLevel);
			zone.setWaterActiveTimeSeconds(waterActiveTimeSeconds);

			zones.add(zone);

			zoneNumber++;
			zoneID = properties.getProperty(String.format("Zone%s.ID", zoneNumber));
		}

		zoneController = new ZoneController(zones);
	}

	private void startTimedServices() {

	}

	public void startup() {
		if (!initialized) {
			loadProperties();
			createComponents();
			startTimedServices();

			initialized = true;
		}
	}

	public void shutdown() {
		if (initialized) {
			initialized = false;

			fireShutdownEvents();
		}
	}

	public void addTerminationListener(GreenhouseTerminationListener listener) {
		terminationListeners.add(listener);
	}

	private void fireShutdownEvents() {
		for (GreenhouseTerminationListener listener : terminationListeners) {
			listener.onGreenhouseTerminated();
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

	public GreenhouseGpioController getGreenhouseGpioController() {
		return greenhouseGpioController;
	}

	public Properties getProperties() {
		return properties;
	}

}
