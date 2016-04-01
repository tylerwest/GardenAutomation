package ca.tylerwest.greenhouse.components;

public class SoilMoistureSensor extends AbstractGPIOComponent {

    public SoilMoistureSensor(int GPIO) {
        super(GPIO);
    }
    
    public double getSoilMoistureLevel() {
        return 0.0;
    }

	@Override
	protected void provisionPins() {
		// TODO Auto-generated method stub
		
	}
}
