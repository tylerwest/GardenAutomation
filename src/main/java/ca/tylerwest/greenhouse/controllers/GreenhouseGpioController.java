package ca.tylerwest.greenhouse.controllers;

import java.util.HashMap;
import java.util.Map;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;

import ca.tylerwest.greenhouse.Greenhouse;
import ca.tylerwest.greenhouse.listeners.GreenhouseTerminationListener;

public class GreenhouseGpioController {

	private GpioController gpio;
	private Map<Integer, GpioPinDigitalOutput> outputPins = new HashMap<Integer, GpioPinDigitalOutput>();
	private Map<Integer, GpioPinDigitalInput> inputPins = new HashMap<Integer, GpioPinDigitalInput>();
	
	public GreenhouseGpioController() {
		try
		{
			gpio = GpioFactory.getInstance();
			Greenhouse.getInstance().addTerminationListener(new GreenhouseGpioControllerTerminationListener());
		}
		catch (UnsatisfiedLinkError e)
		{
			System.err.println("Unable to bind to the Raspberry Pi GPIO. The greenhouse will be running in software-only mode.");
		}
	}
	
	public GpioPinDigitalOutput getOutputPin(int pinNumber)
	{
		GpioPinDigitalOutput pin = outputPins.get(pinNumber);
		if (pin == null)
		{
			pin = gpio.provisionDigitalOutputPin(RaspiPin.getPinByName(String.format("GPIO %s", pinNumber)));
			outputPins.put(pinNumber, pin);
		}
		return pin;
	}
	
	public GpioPinDigitalInput getInputPin(int pinNumber)
	{
		GpioPinDigitalInput pin = inputPins.get(pinNumber);
		if (pin == null)
		{
			pin = gpio.provisionDigitalInputPin(RaspiPin.getPinByName(String.format("GPIO %s", pinNumber)));
			inputPins.put(pinNumber, pin);
		}
		return pin;
	}
	
	
	private class GreenhouseGpioControllerTerminationListener implements GreenhouseTerminationListener {

		@Override
		public void onGreenhouseTerminated() {
			gpio.shutdown();
		}

	}
}
