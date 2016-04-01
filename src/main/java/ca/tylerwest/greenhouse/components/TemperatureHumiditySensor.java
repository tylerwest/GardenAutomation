package ca.tylerwest.greenhouse.components;

public class TemperatureHumiditySensor extends AbstractGPIOComponent {
    private final String ID;
    
    private double minimumTemperature;
    private double maximumTemperature;
    private double minimumHumidity;
    private double maximumHumidity;

    public TemperatureHumiditySensor(String ID, int GPIO) {
    	super(GPIO);
    	this.ID = ID;
    }
    
    public String getID() {
		return ID;
	}
    
    public double getTemperature() {
        return 0.0;
    }
    
    public double getHumidity() {
        return 0.0;
    }

	public double getMinimumTemperature() {
		return minimumTemperature;
	}

	public void setMinimumTemperature(double minimumTemperature) {
		this.minimumTemperature = minimumTemperature;
	}

	public double getMaximumTemperature() {
		return maximumTemperature;
	}

	public void setMaximumTemperature(double maximumTemperature) {
		this.maximumTemperature = maximumTemperature;
	}

	public double getMinimumHumidity() {
		return minimumHumidity;
	}

	public void setMinimumHumidity(double minimumHumidity) {
		this.minimumHumidity = minimumHumidity;
	}

	public double getMaximumHumidity() {
		return maximumHumidity;
	}

	public void setMaximumHumidity(double maximumHumidity) {
		this.maximumHumidity = maximumHumidity;
	}

	@Override
	protected void provisionPins() {
		// TODO Auto-generated method stub
		
	}
    
    
}
