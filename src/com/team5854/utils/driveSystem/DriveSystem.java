package com.team5854.utils.driveSystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team5854.utils.Maths;
import com.team5854.utils.sensors.Ultrasonic;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class DriveSystem {
	private double P = 0;
	private double I = 0;
	private double D = 0;
	private int kTimeoutMs = 10;
	private int kPIDLoopIdx = 0;
	private boolean kSensorPhase = false;
	private boolean kMotorInvert = false;
	
	
	private TalonSRX frontLeft, frontRight, backLeft, backRight;
	public DriveSystem(TalonSRX frontLeft, TalonSRX frontRight, TalonSRX backLeft, TalonSRX backRight) {
		this.frontLeft = frontLeft; 
		this.frontRight = frontRight;
		this.backLeft = backLeft;
		this.backRight = backRight;
		this.backLeft.set(ControlMode.Follower, this.frontLeft.getDeviceID());
		this.backRight.set(ControlMode.Follower, this.frontRight.getDeviceID());
		
		this.frontLeft.setInverted(this.kMotorInvert);
		this.backLeft.setInverted(this.kMotorInvert);
		this.frontRight.setInverted(!this.kMotorInvert);
		this.backRight.setInverted(!this.kMotorInvert);
	}
	
	public void drive(double x, double y) {
		frontLeft.set(ControlMode.PercentOutput, x);
		frontRight.set(ControlMode.PercentOutput, y);
	}

	public void drive(ADXRS450_Gyro gyro, double degreeToTurn) {
		if (gyro.getAngle()-degreeToTurn > 0) {
			double speed = Maths.map(gyro.getAngle()-degreeToTurn, 0, 270, 0.1,0.4);
			if (degreeToTurn < 0) {
				this.drive(-speed, speed);
			} else {
				this.drive(speed, -speed);
			}
		} else {
			double speed = Maths.map(gyro.getAngle()-degreeToTurn, 0, 270, -0.1,-0.4);
			if (degreeToTurn < 0) {
				this.drive(-speed, speed);
			} else {
				this.drive(speed, -speed);
			}
		}
	}
	
	public double drive(double position) {
		double rotations = position;
		double ticks = rotations * 4096;
		this.frontLeft.set(ControlMode.Position, ticks);
		this.frontRight.set(ControlMode.Position, ticks);
		return ticks;
	}
	
	public void drive(Ultrasonic ultraSensor, double distanceFromWall) {
		if (ultraSensor.getDistance()>distanceFromWall+5) {
			double speed = Maths.map(ultraSensor.getDistance(), distanceFromWall, 100, 0.1, 0.7);
			this.drive(speed,speed);
		}
	}
	
	public void setEncoder (int position) {
		 this.frontLeft.setSelectedSensorPosition(position, 0, 10);
		 this.frontRight.setSelectedSensorPosition(position, 0, 10);
	}
	public double getLeftEncoderPosition() {
		return this.frontLeft.getSelectedSensorPosition(0);
	}
	public double getRightEncoderPosition() {
		return this.frontRight.getSelectedSensorPosition(0);
	}
	public double getAvgEncoderPosition() {
		double sum = getLeftEncoderPosition() + getRightEncoderPosition();
		return sum / 2;
	}
	public void setRamping() {
		
	}
	
	public void setupAutonomous() {
 		//-------------------------------SETUP FRONT LEFT MOTOR-----------------
		this.frontLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, this.kPIDLoopIdx, this.kTimeoutMs);//set the type of sensor.
		
		this.frontLeft.setSensorPhase(true);//change the direction of encoder
		
		this.frontLeft.configNominalOutputForward(0, this.kTimeoutMs);
		this.frontLeft.configNominalOutputReverse(0, this.kTimeoutMs);
		this.frontLeft.configPeakOutputForward(1, this.kTimeoutMs);//the peak output it can go forward
		this.frontLeft.configPeakOutputReverse(-1, this.kTimeoutMs);//peak output it can go backwards
		
		this.frontLeft.configAllowableClosedloopError(0, this.kPIDLoopIdx, this.kTimeoutMs); //closed loop error (if you want it to be in a range of values)
		
		this.frontLeft.config_kF(this.kPIDLoopIdx, 0.0, this.kTimeoutMs); //setup initial F
		this.frontLeft.config_kP(this.kPIDLoopIdx, this.P, this.kTimeoutMs); //setup initial P
		this.frontLeft.config_kI(this.kPIDLoopIdx, this.I, this.kTimeoutMs); //setup initial I
		this.frontLeft.config_kD(this.kPIDLoopIdx, this.D, this.kTimeoutMs); //setup initial D
		
		int absolutePosition = this.frontLeft.getSensorCollection().getPulseWidthPosition(); //get the Encoders absolute position and set the relative to match.
		absolutePosition &= 0xFFF;//Mask out overflows
		if (this.kSensorPhase)
			absolutePosition *= -1;
		if (this.kMotorInvert)
			absolutePosition *= -1;
		this.frontLeft.setSelectedSensorPosition(absolutePosition, this.kPIDLoopIdx, this.kTimeoutMs);
		
		//-------------------------------SETUP FRONT LEFT MOTOR-----------------
				this.frontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, this.kPIDLoopIdx, this.kTimeoutMs);//set the type of sensor.
				
				this.frontRight.setSensorPhase(true);//change the direction of encoder
				
				this.frontRight.configNominalOutputForward(0, this.kTimeoutMs);
				this.frontRight.configNominalOutputReverse(0, this.kTimeoutMs);
				this.frontRight.configPeakOutputForward(1, this.kTimeoutMs);//the peak output it can go forward
				this.frontRight.configPeakOutputReverse(-1, this.kTimeoutMs);//peak output it can go backwards
				
				this.frontRight.configAllowableClosedloopError(0, this.kPIDLoopIdx, this.kTimeoutMs); //closed loop error (if you want it to be in a range of values)
				
				this.frontRight.config_kF(this.kPIDLoopIdx, 0.0, this.kTimeoutMs); //setup initial F
				this.frontRight.config_kP(this.kPIDLoopIdx, this.P, this.kTimeoutMs); //setup initial P
				this.frontRight.config_kI(this.kPIDLoopIdx, this.I, this.kTimeoutMs); //setup initial I
				this.frontRight.config_kD(this.kPIDLoopIdx, this.D, this.kTimeoutMs); //setup initial D
				
				absolutePosition = this.frontRight.getSensorCollection().getPulseWidthPosition(); //get the Encoders absolute position and set the relative to match.
				absolutePosition &= 0xFFF;//Mask out overflows
				if (this.kSensorPhase)
					absolutePosition *= -1;
				if (this.kMotorInvert)
					absolutePosition *= -1;
				this.frontRight.setSelectedSensorPosition(absolutePosition, this.kPIDLoopIdx, this.kTimeoutMs);
	}
	
	public void setPID(double p, double i, double d) {
		this.P = p;
		this.I = i;
		this.D = d;
		//----------------SET FRONT LEFT PID------------
		this.frontLeft.config_kP(this.kPIDLoopIdx, this.P, this.kTimeoutMs);
		this.frontLeft.config_kI(this.kPIDLoopIdx, this.I, this.kTimeoutMs);
		this.frontLeft.config_kD(this.kPIDLoopIdx, this.D, this.kTimeoutMs);
		//----------------SET FRONT RIGHT PID---------------
		this.frontRight.config_kP(this.kPIDLoopIdx, this.P, this.kTimeoutMs);
		this.frontRight.config_kI(this.kPIDLoopIdx, this.I, this.kTimeoutMs);
		this.frontRight.config_kD(this.kPIDLoopIdx, this.D, this.kTimeoutMs);
	}
}
