package com.team5854.utils.driveSystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class DriveSystem {
private TalonSRX frontLeft, frontRight, backLeft, backRight;
public DriveSystem(TalonSRX frontLeft, TalonSRX frontRight, TalonSRX backLeft, TalonSRX backRight) {
	this.frontLeft = frontLeft; 
	this.frontRight = frontRight;
	this.backLeft = backLeft;
	this.backRight = backRight;
	this.backLeft.set(ControlMode.Follower, this.frontLeft.getDeviceID());
	this.backRight.set(ControlMode.Follower, this.frontRight.getDeviceID());
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
	this.frontLeft.config_kP(0, 0.1, 10);
	this.frontRight.config_kP(0, 0.1, 10);
	this.frontLeft.config_kI(0, 0.0, 10);
	this.frontRight.config_kI(0, 0.0, 10);
	this.frontLeft.config_kD(0, 0.0, 10);
	this.frontRight.config_kD(0, 0.0, 10);
	
}
}
