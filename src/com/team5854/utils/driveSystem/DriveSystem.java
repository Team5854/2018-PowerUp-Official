package com.team5854.utils.driveSystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
public class DriveSystem {
	private TalonSRX leftMotor;
	private TalonSRX rightMotor;
	private TalonSRX backLeftMotor;
	private TalonSRX backRightMotor;
	private int driveType = 0;
	
	public DriveSystem(int type, TalonSRX left, TalonSRX right) {
		this.driveType = type;
		//-----SET THE MASTER MOTORS----
		this.leftMotor = left;
		this.rightMotor = right;
	}
	
	public DriveSystem(int type, TalonSRX frontLeft, TalonSRX frontRight, TalonSRX backLeft, TalonSRX backRight) {
		this.driveType = type;
		//-----SET THE MASTER MOTORS----
		if (DriveType.TankDrive == this.driveType || DriveType.ArcadeDrive == this.driveType) {
			this.leftMotor = frontLeft;
			this.rightMotor = frontRight;
			//----SET THE FOllOWER MOTORS--------
			backLeft.set(ControlMode.Follower, this.leftMotor.getDeviceID());
			backRight.set(ControlMode.Follower, this.rightMotor.getDeviceID());
		} else if (DriveType.MecanumDrive == this.driveType) {
			this.leftMotor = frontLeft;
			this.rightMotor = frontRight;
			this.backLeftMotor = backLeft;
			this.backRightMotor = backRight;
		}
	}
	
	public DriveSystem(int type, TalonSRX frontLeft, TalonSRX frontRight, TalonSRX backLeft, TalonSRX backRight,
					   TalonSRX frontLeftTwo, TalonSRX frontRightTwo, TalonSRX backLeftTwo, TalonSRX backRightTwo) {
		this.driveType = type;
		if (DriveType.TankDrive == this.driveType || DriveType.ArcadeDrive == this.driveType) {
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
			
			leftMotor.setInverted(true);
			backLeft.setInverted(true);
			frontLeftTwo.setInverted(true);
			backLeftTwo.setInverted(true);
			
		}  else if (DriveType.MecanumDrive == this.driveType) {
			this.leftMotor = frontLeft;
			this.rightMotor = frontRight;
			this.backLeftMotor = backLeft;
			this.backRightMotor = backRight;
			//---------------SET SECOND MOTORS TO FOLLOW FIRST MOTORS--------------
			frontLeftTwo.set(ControlMode.Follower, this.leftMotor.getDeviceID());
			frontRightTwo.set(ControlMode.Follower, this.rightMotor.getDeviceID());
			backLeftTwo.set(ControlMode.Follower, this.backLeftMotor.getDeviceID());
			backRightTwo.set(ControlMode.Follower, this.backRightMotor.getDeviceID());
		}
	}
	
	
	public void drive(double x, double y) {
		if (this.driveType == DriveType.TankDrive) {
			leftMotor.set(ControlMode.PercentOutput, x);
			rightMotor.set(ControlMode.PercentOutput, y);
		} else if (this.driveType == DriveType.ArcadeDrive){
			leftMotor.set(ControlMode.PercentOutput, x+y);
			rightMotor.set(ControlMode.PercentOutput, x-y);
		} else {
			System.err.println("DRIVESYTEM: Drive Type Incorrent For A \"x y\" Drive Type.");
		}
	}
	public void drive(double x, double y, double t, double r, double gyro) {
		if (this.driveType == DriveType.MecanumDrive) {
			
		} else {
			System.err.println("DRIVESYTEM: Drive Type Incorrent For A \"x y tist rotate\" Drive Type.");
		}
	}
	public class DriveType {
		public static final int TankDrive = 2343;
		public static final int ArcadeDrive = 2443;
		public static final int MecanumDrive = 2478;
	}
}