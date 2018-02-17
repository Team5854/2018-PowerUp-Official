package com.team5854.utils.driveSystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team5854.utils.Maths;
import com.team5854.utils.sensors.Ultrasonic;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class DriveSystem {
	private boolean direction = true;
	private int P = 0;
	private int I = 0;
	private int D = 0;
	private TalonSRX frontLeft, frontRight, backLeft, backRight;
	public DriveSystem(TalonSRX frontLeft, TalonSRX frontRight, TalonSRX backLeft, TalonSRX backRight) {
		this.frontLeft = frontLeft; 
		this.frontRight = frontRight;
		this.backLeft = backLeft;
		this.backRight = backRight;
		this.backLeft.set(ControlMode.Follower, this.frontLeft.getDeviceID());
		this.backRight.set(ControlMode.Follower, this.frontRight.getDeviceID());
		
		this.frontLeft.setInverted(direction);
		this.backLeft.setInverted(direction);
		this.frontRight.setInverted(!direction);
		this.backRight.setInverted(!direction);
		
		setupAutonomous();
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
		}
	}
	
	public void drive(double position) {
		setEncoder(0);
		this.frontLeft.set(ControlMode.Position, position);
		this.frontRight.set(ControlMode.Position, position);
	}
	
	public void drive(Ultrasonic ultraSensor, double distanceFromWall) {
		if (ultraSensor.getDistance()>distanceFromWall) {
			double speed = Maths.map(ultraSensor.getDistance(), distanceFromWall, 100, 0.1, 0.7);
			this.drive(speed,speed);
		}
	}
	
	public void setEncoder (int position) {
		 this.frontLeft.setSelectedSensorPosition(position, 0, 10);
		 this.frontRight.setSelectedSensorPosition(position, 0, 10);
	}
	
	private void setupAutonomous() {
		int absolutePosition = this.frontLeft.getSelectedSensorPosition(10)& 0xFFF;
		this.frontLeft.setSelectedSensorPosition(absolutePosition, 0, 10);
		this.frontRight.setSelectedSensorPosition(absolutePosition, 0, 10);
		this.frontLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		this.frontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		this.frontLeft.setSensorPhase(true);
		this.frontRight.setSensorPhase(true);
		this.frontLeft.configNominalOutputForward(0, 10);
		this.frontRight.configNominalOutputForward(0, 10);
		this.frontLeft.configNominalOutputReverse(0, 10);
		this.frontRight.configNominalOutputReverse(0, 10);
		this.frontLeft.configPeakOutputForward(1, 10);
		this.frontRight.configPeakOutputForward(1, 10);
		this.frontLeft.configPeakOutputReverse(-1, 10);
		this.frontRight.configPeakOutputReverse(-1, 10);
		this.frontLeft.configAllowableClosedloopError(0, 0, 10);
		this.frontRight.configAllowableClosedloopError(0, 0, 10);
		this.frontLeft.config_kF(0, 0.0, 10);
		this.frontRight.config_kF(0, 0.0, 10);
		this.frontLeft.config_kP(0, this.P, 10);
		this.frontRight.config_kP(0, this.P, 10);
		this.frontLeft.config_kI(0, this.I, 10);
		this.frontRight.config_kI(0, this.I, 10);
		this.frontLeft.config_kD(0, this.D, 10);
		this.frontRight.config_kD(0, this.D, 10);
		
	}
	public void setPID(int p, int i, int d) {
		this.P = p;
		this.I = i;
		this.D = d;
	}
}
