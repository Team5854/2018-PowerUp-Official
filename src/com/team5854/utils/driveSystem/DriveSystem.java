package com.team5854.utils.driveSystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
public class DriveSystem {
	private TalonSRX leftMotor;
	private TalonSRX rightMotor;
	private int driveType = 0;
	
	public DriveSystem(int type, TalonSRX frontLeft, TalonSRX frontRight, TalonSRX backLeft, TalonSRX backRight) {
		this.driveType = type;
		//-----SET THE MASTER MOTORS----
		if (DriveType.TankDrive == this.driveType) {
			this.leftMotor = frontLeft;
			this.rightMotor = frontRight;
			//----SET THE FOllOWER MOTORS--------
			backLeft.set(ControlMode.Follower, this.leftMotor.getDeviceID());
			backRight.set(ControlMode.Follower, this.rightMotor.getDeviceID());
		}
	}
	
	public DriveSystem(int type, TalonSRX frontLeft, TalonSRX frontRight, TalonSRX backLeft, TalonSRX backRight,
					   TalonSRX frontLeftTwo, TalonSRX frontRightTwo, TalonSRX backLeftTwo, TalonSRX backRightTwo) {
		this.driveType = type;
		if (DriveType.TankDrive == this.driveType) {
			//-----SET THE MASTER MOTORS----
			this.leftMotor = frontLeft;
			this.rightMotor = frontRight;
			
			//----SET THE FOllOWER MOTORS--------
			backLeft.set(ControlMode.Follower, this.leftMotor.getDeviceID());
			backRight.set(ControlMode.Follower, this.rightMotor.getDeviceID());
			frontLeftTwo.set(ControlMode.Follower, this.leftMotor.getDeviceID());
			frontRightTwo.set(ControlMode.Follower, this.rightMotor.getDeviceID());
			backLeftTwo.set(ControlMode.Follower, this.leftMotor.getDeviceID());
			backRightTwo.set(ControlMode.Follower, this.rightMotor.getDeviceID());
		}
	}
	
	
	public void drive(double left, double right) {
		if (this.driveType == DriveType.TankDrive) {
			leftMotor.set(ControlMode.PercentOutput, left);
			rightMotor.set(ControlMode.PercentOutput, right);
		}
	}
	
	public class DriveType {
		public static final int TankDrive = 2343;
	}
}
