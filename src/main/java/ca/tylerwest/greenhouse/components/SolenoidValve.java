package ca.tylerwest.greenhouse.components;

import ca.tylerwest.greenhouse.listeners.GPIOTaskListener;

public class SolenoidValve extends AbstractGPIOComponent {

    public enum State {
        OPEN, CLOSED;
    }
    
    private State state = State.CLOSED;
    
    public SolenoidValve(int GPIO) {
        super(GPIO);
    }
    
    public void open(GPIOTaskListener listener) {
        state = State.OPEN;
        listener.onTaskStarted();
        listener.onTaskCompleted();
    }
    
    public void close(GPIOTaskListener listener) {
        state = State.CLOSED;
        listener.onTaskStarted();
        listener.onTaskCompleted();
    }
    
    public State getState() {
        return state;
    }
}
