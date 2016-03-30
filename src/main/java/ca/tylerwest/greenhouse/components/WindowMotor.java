package ca.tylerwest.greenhouse.components;

public class WindowMotor extends AbstractGPIOComponent {

    private final String ID;
    
    public enum Direction {
        FORWARD, BACKWARD;
    }
    
    public WindowMotor(String ID, int GPIO) {
        super(GPIO);
    	this.ID = ID;
    }
    
    public final String getID() {
		return ID;
	}
    
    public void on(Direction direction) {
        
    }
    
    public void off() {
        
    }

	@Override
	protected void provisionPin() {
		// TODO Auto-generated method stub
		
	}
}
