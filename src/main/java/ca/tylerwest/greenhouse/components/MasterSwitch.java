package ca.tylerwest.greenhouse.components;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;

import ca.tylerwest.greenhouse.Greenhouse;
import ca.tylerwest.greenhouse.listeners.GPIOTaskListener;

public class MasterSwitch extends AbstractGPIOComponent {

	public enum State {
        ON, OFF;
    }
    
    private State state = State.OFF;
    
	private final String ID;
	private GpioPinDigitalOutput outputPin;
	
	public MasterSwitch(String ID, int GPIO) {
		super(GPIO);
		this.ID = ID;
	}
	
	public final String getID() {
		return ID;
	}
	
	/*
     * (non-Javadoc)
     * @see ca.tylerwest.greenhouse.components.AbstractGPIOComponent#provisionPin()
     * 
     * The master switch utilizes the Sainsmart relay board. The relays on this board
     * are active low, meaning that the relay is engaged when the pin is 0V. Because of this,
     * I have had to reverse the Raspberry Pi pin states when turning the relay on and off.
     * 
     * On startup and shutdown the pin state should be set to high to turn off the relays.
     */
	protected void provisionPins() {
		outputPin = Greenhouse.getInstance().getGreenhouseGpioController().getOutputPin(getGPIO()[0]);
    	outputPin.setState(PinState.HIGH);
        outputPin.setShutdownOptions(true, PinState.HIGH, PinPullResistance.PULL_UP);
	}
	
	public void on(GPIOTaskListener listener) {
        state = State.ON;
        listener.onTaskStarted();
        outputPin.setState(PinState.LOW);
        listener.onTaskCompleted();
    }
	
	public void off(GPIOTaskListener listener) {
        state = State.OFF;
        listener.onTaskStarted();
        outputPin.setState(PinState.HIGH);
        listener.onTaskCompleted();
    }
	
	public State getState() {
		return state;
	}

}
