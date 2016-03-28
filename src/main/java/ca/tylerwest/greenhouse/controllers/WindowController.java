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
		executor = Executors.newCachedThreadPool();
		Greenhouse.getInstance().addTerminationListener(new WindowControllerTerminationListener());
	}
	
	public double getActiveTimeSeconds() {
		return activeTimeSeconds;
	}
	
	public void setActiveTimeSeconds(double activeTimeSeconds) {
		this.activeTimeSeconds = activeTimeSeconds;
	}

	public void raiseWindows(GPIOTaskListener listener) {
		state = State.OPENING;
		listener.onTaskStarted();

		List<Callable<Object>> todo = new ArrayList<Callable<Object>>();
		for (WindowMotor motor : windowMotors) {
			todo.add(Executors.callable(new MotorForwardTask(motor)));
		}
		
		try {
			executor.invokeAll(todo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		state = State.OPEN;
		listener.onTaskCompleted();
	}

	public void lowerWindows(GPIOTaskListener listener) {
		state = State.CLOSING;
		listener.onTaskStarted();

		List<Callable<Object>> todo = new ArrayList<Callable<Object>>();
		for (WindowMotor motor : windowMotors) {
			todo.add(Executors.callable(new MotorBackwardTask(motor)));
		}
		
		try {
			executor.invokeAll(todo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		state = State.CLOSED;
		listener.onTaskCompleted();
	}

	public State getState() {
		return state;
	}
	
	private class MotorForwardTask implements Runnable
	{
		private WindowMotor motor;
		
		public MotorForwardTask(WindowMotor motor) {
			this.motor = motor;
		}
		
		@Override
		public void run() {
			try {
				motor.on(Direction.FORWARD);
				Thread.sleep((long) (getActiveTimeSeconds() * 1000));
				motor.off();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private class MotorBackwardTask implements Runnable
	{
		private WindowMotor motor;
		
		public MotorBackwardTask(WindowMotor motor) {
			this.motor = motor;
		}
		
		@Override
		public void run() {
			try {
				motor.on(Direction.BACKWARD);
				Thread.sleep((long) (getActiveTimeSeconds() * 1000));
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
