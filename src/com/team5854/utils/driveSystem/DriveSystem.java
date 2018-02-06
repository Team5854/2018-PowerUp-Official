package com.team5854.utils.driveSystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team5854.utils.Maths;
import com.team5854.utils.mechanisms.Ultrasonic;
public class DriveSystem {
	private TalonSRX leftMotor;
	private TalonSRX rightMotor;
	private TalonSRX backLeftMotor;
	private TalonSRX backRightMotor;
	private int driveType = 0;
	private double wheelRadius = 1;
	
	
//--------------------CONSTRUCTORS----------------------
	/**
	 * Two Motor Drive Train
	 * @param type
	 * @param left
	 * @param right
	 */
	public DriveSystem(int type, TalonSRX left, TalonSRX right) {
		this.driveType = type;
		//-----SET THE MASTER MOTORS----
		this.leftMotor = left;
		this.rightMotor = right;
		setupAutonomous();
	}
	/**
	 * Four Motor Drive Train
	 * @param type
	 * @param frontLeft
	 * @param frontRight
	 * @param backLeft
	 * @param backRight
	 */
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
		setupAutonomous();
	}
	
	/**
	 * Eight Motor Drive Train
	 * @param type
	 * @param frontLeft
	 * @param frontRight
	 * @param backLeft
	 * @param backRight
	 * @param frontLeftTwo
	 * @param frontRightTwo
	 * @param backLeftTwo
	 * @param backRightTwo
	 */
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
		setupAutonomous();
	}
//------------DRIVE METHODS------------------------
	/**
	 * Normal two stick drive train. Examples: Tank, and Arcade.
	 * @param x
	 * @param y
	 */
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
	/**
	 * drive types that require 3 input values and a gyro.
	 * We will assume mecanum for examples
	 * @param x - the x input (Forward/backwards).
	 * @param y - the Y input (Left/Right).
	 * @param t - the twist value ()
	 * @param gyro
	 */
	public void drive(double x, double y, double t, double gyro) {
		if (this.driveType == DriveType.MecanumDrive) {
			
		} else {
			System.err.println("DRIVESYTEM: Drive Type Incorrent For A \"x y tist rotate\" Drive Type.");
		}
	}
	/**
	 * If the wheel diameter OR radius has been set than positions is in the units of said diameter/radius.
	 * If diameter/radius not set then position is number of full wheel rotations. 
	 * @param position
	 */
	public void drive(double position) {
		double rotations = 4096 * position;
		double distance = getWheelCircumference() * rotations;
		leftMotor.set(ControlMode.Position, distance);
		rightMotor.set(ControlMode.Position, distance);
		System.out.println(leftMotor.getSelectedSensorPosition(0) + " " + distance);
		System.out.println(rightMotor.getSelectedSensorPosition(0) + " " + distance);
	}
	/**
	 * Drive to a distance away from the object in front of you using an Ultrasonic sensor
	 * @param sonic - Ultrasonic sensor that you are using to drive
	 * @param distance - distance away from the object in front of the Ultrasonic sensor.
	 */
	public void drive(Ultrasonic sonic, double distance) {
		if (sonic.getDistance() > distance) {
			double speed = Maths.map(sonic.getDistance()-distance, 0, 50, 0.1, 0.4);
			this.drive(-speed, -speed);
		} else {
			this.drive(0, 0);
		}
	}
	
//-----------------UTIL FUNCTIONS-----------------------
	/**
	 * Set the encoder position to the value passed to this method. 
	 * @param value - The value to set the encoders to (Usually 0 to reset).
	 */
	public void setEncoderPosition(int value) {
		leftMotor.setSelectedSensorPosition(value, 0, 10);
		rightMotor.setSelectedSensorPosition(value, 0, 10);
	}
	
//-----------PRIVATE HELPER METHODS-------------------
	private void setupAutonomous() {
		int absolutePosition = this.leftMotor.getSelectedSensorPosition(10) & 0xFFF;
 		this.leftMotor.setSelectedSensorPosition(absolutePosition, 0, 10);
 		this.rightMotor.setSelectedSensorPosition(absolutePosition, 0, 10);
	 	/* choose the sensor and sensor direction */
 		this.leftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
 		this.rightMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
 		this.leftMotor.setSensorPhase(true);
 		this.rightMotor.setSensorPhase(true);
 		/* set the peak and nominal outputs, 12V means full */
 		this.leftMotor.configNominalOutputForward(0, 10);
 		this.rightMotor.configNominalOutputForward(0, 10);
 		this.leftMotor.configNominalOutputReverse(0, 10);
 		this.rightMotor.configNominalOutputReverse(0, 10);
 		this.leftMotor.configPeakOutputForward(1, 10);
 		this.rightMotor.configPeakOutputForward(1, 10);
 		this.leftMotor.configPeakOutputReverse(-1, 10);
 		this.rightMotor.configPeakOutputReverse(-1, 10);
 		/* set the allowable closed-loop error,
 		 * Closed-Loop output will be neutral within this range.
 		 * See Table in Section 17.2.1 for native units per rotation. 
 		 */
 		this.leftMotor.configAllowableClosedloopError(0, 0, 10); /* always servo */
 		this.rightMotor.configAllowableClosedloopError(0, 0, 10); /* always servo */
 		/* set closed loop gains in slot0 */
 		this.leftMotor.config_kF(0, 0.0, 10);
 		this.rightMotor.config_kF(0, 0.0, 10);
 		this.leftMotor.config_kP(0, 0.1, 10);
 		this.rightMotor.config_kP(0, 0.1, 10);
 		this.leftMotor.config_kI(0, 0.0, 10);
 		this.rightMotor.config_kI(0, 0.0, 10);
 		this.leftMotor.config_kD(0, 0.0, 10);
 		this.rightMotor.config_kD(0, 0.0, 10);
 		
 		if (backLeftMotor != null && backRightMotor != null) {
 			this.backLeftMotor.setSelectedSensorPosition(absolutePosition, 0, 10);
 			this.backRightMotor.setSelectedSensorPosition(absolutePosition, 0, 10);
 			/* choose the sensor and sensor direction */
 			this.backLeftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
 			this.backRightMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
 			this.backLeftMotor.setSensorPhase(true);
 			this.backRightMotor.setSensorPhase(true);
 			/* set the peak and nominal outputs, 12V means full */
 			this.backLeftMotor.configNominalOutputForward(0, 10);
 			this.backRightMotor.configNominalOutputForward(0, 10);
 			this.backLeftMotor.configNominalOutputReverse(0, 10);
 			this.backRightMotor.configNominalOutputReverse(0, 10);
 			this.backLeftMotor.configPeakOutputForward(1, 10);
 			this.backRightMotor.configPeakOutputForward(1, 10);
 			this.backLeftMotor.configPeakOutputReverse(-1, 10);
 			this.backRightMotor.configPeakOutputReverse(-1, 10);
 			/* set the allowable closed-loop error,
 			 * Closed-Loop output will be neutral within this range.
 			 * See Table in Section 17.2.1 for native units per rotation. 
 			 */
 			this.backLeftMotor.configAllowableClosedloopError(0, 0, 10); /* always servo */
 			this.backRightMotor.configAllowableClosedloopError(0, 0, 10); /* always servo */
 			/* set closed loop gains in slot0 */
 			this.backLeftMotor.config_kF(0, 0.0, 10);
 			this.backRightMotor.config_kF(0, 0.0, 10);
 			this.backLeftMotor.config_kP(0, 0.1, 10);
 			this.backRightMotor.config_kP(0, 0.1, 10);
 			this.backLeftMotor.config_kI(0, 0.0, 10);
 			this.backRightMotor.config_kI(0, 0.0, 10);
 			this.backLeftMotor.config_kD(0, 0.0, 10);
 			this.backRightMotor.config_kD(0, 0.0, 10);	
 		}
	}

	
//-------------------GETTERS AND SETTERS-------------------
	public double getWheelDiameter() {
		return 2 * wheelRadius;
	}
	public void setWheelDiameter(double wheelDiameter) {
		this.wheelRadius = wheelDiameter/2;
	}
	public double getWheelRadius() {
		return wheelRadius;
	}
	public void setWheelRadius(double wheelRadius) {
		this.wheelRadius = wheelRadius;
	}	
	public double getWheelCircumference() {
		double wheelCircumference = 2 * Math.PI * wheelRadius;
		return wheelCircumference ;
	}
//-------------DRIVE TYPE SUBCLASS---------------
	public class DriveType {
		public static final int TankDrive = 2343;
		public static final int ArcadeDrive = 2443;
		public static final int MecanumDrive = 2478;
	}
}