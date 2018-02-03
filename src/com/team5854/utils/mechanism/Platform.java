package com.team5854.utils.mechanism;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Platform {
	private TalonSRX left;
	private TalonSRX right;
	
	
	public Platform (TalonSRX left, TalonSRX right) {
		this.left = left;
		this.right = right;
		
		
	}
	public void deploy() {
		for(int i=0;i<3;i++) {
			this.left.set(ControlMode.PercentOutput,.2);
			this.right.set(ControlMode.PercentOutput, .2);
		}
	}
}
