package ca.tylerwest.greenhouse.components;

public class AbstractGPIOComponent {
	private int GPIO;
	
	public AbstractGPIOComponent(int GPIO) {
		this.GPIO = GPIO;
	}
	
	public final int getGPIO() {
		return GPIO;
	}
}
