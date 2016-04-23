package ca.tylerwest.greenhouse.controllers;

import java.io.IOException;

public class SystemController {
	
	public enum State {
		RUNNING, POWERING_OFF, REBOOTING;
	}
	
	private State state = State.RUNNING;

	public void powerOff() {
		try {
			state = State.POWERING_OFF;
			Runtime.getRuntime().exec("sudo poweroff");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void reboot() {
		try {
			state = State.REBOOTING;
			Runtime.getRuntime().exec("sudo reboot");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public State getState() {
		return state;
	}
}
