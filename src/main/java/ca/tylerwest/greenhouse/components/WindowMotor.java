package ca.tylerwest.greenhouse.components;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;

import ca.tylerwest.greenhouse.Greenhouse;

public class WindowMotor extends AbstractGPIOComponent {

	private final String ID;
	private GpioPinDigitalOutput forwardPin;
	private GpioPinDigitalOutput backwardPin;

	public enum Direction {
		FORWARD, BACKWARD;
	}

	public WindowMotor(String ID, int forwardGpio, int backwardGpio) {
		super(new int[] { forwardGpio, backwardGpio });
		this.ID = ID;
	}

	public final String getID() {
		return ID;
	}

	public void on(Direction direction) {
		switch (direction) {
		case FORWARD:
			forwardPin.setState(PinState.HIGH);
			backwardPin.setState(PinState.LOW);
			break;
		case BACKWARD:
			backwardPin.setState(PinState.HIGH);
			forwardPin.setState(PinState.LOW);
			break;
		}
	}

	public void off() {
		backwardPin.setState(PinState.LOW);
		forwardPin.setState(PinState.LOW);
	}

	@Override
	protected void provisionPins() {
		forwardPin = Greenhouse.getInstance().getGreenhouseGpioController().getOutputPin(getGPIO()[0]);
		forwardPin.setState(PinState.LOW);
		forwardPin.setShutdownOptions(true, PinState.LOW, PinPullResistance.PULL_DOWN);

		backwardPin = Greenhouse.getInstance().getGreenhouseGpioController().getOutputPin(getGPIO()[1]);
		backwardPin.setState(PinState.LOW);
		backwardPin.setShutdownOptions(true, PinState.LOW, PinPullResistance.PULL_DOWN);
	}
}
