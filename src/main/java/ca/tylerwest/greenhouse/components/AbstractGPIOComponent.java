package ca.tylerwest.greenhouse.components;

public abstract class AbstractGPIOComponent {
	private int[] GPIO;
	
	public AbstractGPIOComponent(int... GPIO) {
		this.GPIO = GPIO;
		provisionPins();
	}
	
	public final int[] getGPIO() {
		return GPIO;
	}
	
	protected abstract void provisionPins();
}
