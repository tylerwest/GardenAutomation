package ca.tylerwest.greenhouse.components;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;

import ca.tylerwest.greenhouse.Greenhouse;
import ca.tylerwest.greenhouse.listeners.GPIOTaskListener;

public class SolenoidValve extends AbstractGPIOComponent {

    public enum State {
        OPEN, CLOSED;
    }
    
    private State state = State.CLOSED;
    private GpioPinDigitalOutput outputPin;
    
    public SolenoidValve(int GPIO) {
        super(GPIO);
    }
    
    /*
     * (non-Javadoc)
     * @see ca.tylerwest.greenhouse.components.AbstractGPIOComponent#provisionPin()
     * 
     * The solenoid valves are hooked up to the Sainsmart relay board. The relays on this board
     * are active low, meaning that the relay is engaged when the pin is 0V. Because of this,
     * I have had to reverse the Raspberry Pi pin states when turning the relay on and off.
     * 
     * On startup and shutdown the pin state should be set to high to turn off the relays.
     */
    protected void provisionPins()
    {
    	outputPin = Greenhouse.getInstance().getGreenhouseGpioController().getOutputPin(getGPIO()[0]);
    	outputPin.setState(PinState.HIGH);
        outputPin.setShutdownOptions(true, PinState.HIGH, PinPullResistance.PULL_UP);
    }
    
    public void open(GPIOTaskListener listener) {
        state = State.OPEN;
        listener.onTaskStarted();
        outputPin.setState(PinState.LOW);
        listener.onTaskCompleted();
    }
    
    public void close(GPIOTaskListener listener) {
        state = State.CLOSED;
        listener.onTaskStarted();
        outputPin.setState(PinState.HIGH);
        listener.onTaskCompleted();
    }
    
    public State getState() {
        return state;
    }
}
