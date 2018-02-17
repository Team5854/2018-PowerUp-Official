package com.team5854.utils;

import edu.wpi.first.wpilibj.Joystick;

public class Toggle {
	boolean state = false;
	Joystick joystick;
	public Toggle(Joystick joystick) {
		this.joystick = joystick;
	}
	
	boolean isFirst = true;
	public void toggle(int button) {
		if (this.joystick.getRawButton(button) && isFirst) {
			isFirst = false;
			this.state = !this.state;
		} else {
			isFirst = true;
		}
	}
	
	public boolean getState() {
		return this.state;
	}
}
