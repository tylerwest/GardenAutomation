package ca.tylerwest.greenhouse.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import ca.tylerwest.greenhouse.Greenhouse;
import ca.tylerwest.greenhouse.components.WindowMotor;
import ca.tylerwest.greenhouse.components.WindowMotor.Direction;
import ca.tylerwest.greenhouse.listeners.GPIOTaskListener;
import ca.tylerwest.greenhouse.listeners.GreenhouseTerminationListener;

public class WindowController {
	private List<WindowMotor> windowMotors;
	private ExecutorService executor;
	private double activeTimeSeconds;

	public enum State {
		OPEN, OPENING, CLOSED, CLOSING;
	}

	private State state = State.CLOSED;

	public WindowController(List<WindowMotor> windowMotors) {
		this.windowMotors = windowMotors;
		executor = Executors.newSingleThreadExecutor();
		Greenhouse.getInstance().addTerminationListener(new WindowControllerTerminationListener());
	}
	
	public double getActiveTimeSeconds() {
		return activeTimeSeconds;
	}
	
	public void setActiveTimeSeconds(double activeTimeSeconds) {
		this.activeTimeSeconds = activeTimeSeconds;
	}
	
	public void nudgeUp(GPIOTaskListener listener) {
		raiseWindows(0.5, listener);
	}
	
	public void nudgeDown(GPIOTaskListener listener) {
		lowerWindows(0.5, listener);
	}
	
	public void raiseWindows(GPIOTaskListener listener)
	{
		raiseWindows(getActiveTimeSeconds(), listener);
	}
	
	public void lowerWindows(GPIOTaskListener listener)
	{
		lowerWindows(getActiveTimeSeconds(), listener);
	}

	private void raiseWindows(double activeTime, GPIOTaskListener listener) {
		executor.submit(new RaiseWindowsTask(activeTime, listener));
	}

	private void lowerWindows(double activeTime, GPIOTaskListener listener) {
		executor.submit(new LowerWindowsTask(activeTime, listener));
	}

	public State getState() {
		return state;
	}
	
	private abstract class WindowControllerTask implements Runnable
	{
		protected GPIOTaskListener listener;
		protected double activeTime;
		
		public WindowControllerTask(double activeTime, GPIOTaskListener listener) {
			this.activeTime = activeTime;
			this.listener = listener;
		}
		
		@Override
		public final void run() {
			begin();
			listener.onTaskStarted();
			
			ExecutorService service = Executors.newCachedThreadPool();
			
			List<Callable<Object>> todo = new ArrayList<Callable<Object>>();
			for (WindowMotor motor : windowMotors) {
				todo.add(Executors.callable(createTaskForMotor(motor, activeTime)));
			}
			
			try {
				service.invokeAll(todo);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			service.shutdown();
			
			end();
			listener.onTaskCompleted();
		}
		
		protected abstract void begin();
		protected abstract void end();
		protected abstract Runnable createTaskForMotor(WindowMotor motor, double activeTime);
	}
	
	private class RaiseWindowsTask extends WindowControllerTask
	{
		public RaiseWindowsTask(double activeTime, GPIOTaskListener listener) {
			super(activeTime, listener);
		}
		
		@Override
		protected void begin() {
			state = State.OPENING;
		}
		
		@Override
		protected Runnable createTaskForMotor(WindowMotor motor, double activeTime) {
			return new MotorTask(motor, activeTime, Direction.FORWARD);
		}
		
		@Override
		protected void end() {
			state = State.OPEN;
		}
	}
	
	private class LowerWindowsTask extends WindowControllerTask
	{
		public LowerWindowsTask(double activeTime, GPIOTaskListener listener) {
			super(activeTime, listener);
		}
		
		@Override
		protected void begin() {
			state = State.CLOSING;
		}
		
		@Override
		protected Runnable createTaskForMotor(WindowMotor motor, double activeTime) {
			return new MotorTask(motor, activeTime, Direction.BACKWARD);
		}
		
		@Override
		protected void end() {
			state = State.CLOSED;
		}
	}
	
	private class MotorTask implements Runnable
	{
		private WindowMotor motor;
		private double activeTime;
		private Direction direction;
		
		public MotorTask(WindowMotor motor, double activeTime, Direction direction) {
			this.motor = motor;
			this.activeTime = activeTime;
			this.direction = direction;
		}
		
		@Override
		public void run() {
			try {
				motor.on(direction);
				Thread.sleep((long) (activeTime * 1000));
				motor.off();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private class WindowControllerTerminationListener implements GreenhouseTerminationListener {

		@Override
		public void onGreenhouseTerminated() {
			try {
				System.out.println("attempt to shutdown window controller");
				executor.shutdown();
				executor.awaitTermination(5, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				System.err.println("window controller interrupted");
			} finally {
				if (!executor.isTerminated()) {
					System.err.println("cancel non-finished window controller tasks");
				}
				executor.shutdownNow();
				System.out.println("window controller shutdown");
			}
		}

	}
}
