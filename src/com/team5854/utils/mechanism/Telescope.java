package com.team5854.utils.mechanism;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;

public class Telescope {
	private double maxSpeed = 0.9;
	private double minSpeed = 0.2;
	private TalonSRX telescopeMotor;
	private DigitalInput lowerLimit, upperLimit, bottomLimit;
	public Telescope(TalonSRX telescopeMotor, DigitalInput lowerLimit, DigitalInput upperLimit, DigitalInput bottomLimit) {
		this.telescopeMotor = telescopeMotor;
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit; 
		this.bottomLimit = bottomLimit;
	}
	public void lowerLimit() {
		if(!lowerLimit.get()) {
			this.telescopeMotor.set(ControlMode.PercentOutput, -maxSpeed);
		}else {
			this.telescopeMotor.set(ControlMode.PercentOutput, 0);
		}
	}
	public void upperLimit() {
		if(!upperLimit.get()) {
			this.telescopeMotor.set(ControlMode.PercentOutput, maxSpeed);
		} else {
			this.telescopeMotor.set(ControlMode.PercentOutput, 0);
		}
	}
	public void down() {
		if(!bottomLimit.get()) {
			this.telescopeMotor.set(ControlMode.PercentOutput, -minSpeed);
		}else {
			this.telescopeMotor.set(ControlMode.PercentOutput, 0);
		}
	}
	public void stop() {
		this.telescopeMotor.set(ControlMode.PercentOutput, 0);
	}
	
}
	