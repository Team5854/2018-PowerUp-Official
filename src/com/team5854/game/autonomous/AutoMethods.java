package com.team5854.game.autonomous;

import com.team5854.utils.driveSystem.DriveSystem;
import com.team5854.utils.mechanism.Grabber;
import com.team5854.utils.sensors.Ultrasonic;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DriverStation;

public class AutoMethods {
	private String gameData = "";
	private ADXRS450_Gyro gyro; 
	private DriveSystem driveSystem;
	private Grabber grabber;
	private Ultrasonic ultraSensor;
	public AutoMethods(ADXRS450_Gyro gyro, DriveSystem driveSystem, Grabber grabber, Ultrasonic ultraSensor) {
		this.gyro = gyro;
		this.driveSystem = driveSystem;
		this.grabber = grabber;
		this.ultraSensor = ultraSensor;
	}
	
	public void cLeftSwitch() {
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if('R'==gameData.charAt(0)) {
			driveSystem.drive(gyro, 52);
			driveSystem.drive(227.04);
			driveSystem.drive(gyro, -51);
			grabber.output();
		} else {
			driveSystem.drive(ultraSensor, 12);
		}
			
	}
	public void cLeftScale() {
		// TODO Auto-generated method stub
		
	}
	public void cRightSwitch() {
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if('L'==gameData.charAt(0)) {
			driveSystem.drive(gyro, -52);
			driveSystem.drive(227.04);
			driveSystem.drive(gyro, 51);
			grabber.output();
		} else {
			driveSystem.drive(ultraSensor, 12);
		}
			
	}
	public void cRightScale() {
		// TODO Auto-generated method stub
		
	}

	public void leftSwitch() {
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if('L'==gameData.charAt(0)) {
			driveSystem.drive(168);
			driveSystem.drive(gyro, 90);
			driveSystem.drive(58.56);
			grabber.output();
		} else {
			driveSystem.drive(226);
			driveSystem.drive(gyro, 90);
			driveSystem.drive(147);
			driveSystem.drive(gyro, 90);
			driveSystem.drive(56);
			driveSystem.drive(gyro, 90);
			driveSystem.drive(5);
			grabber.output();
		}
	}
	
	public void leftScale() {
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if('L'==gameData.charAt(0)) {
			driveSystem.drive(317.65);
			driveSystem.drive(gyro, 90);
			driveSystem.drive(4.75);
			grabber.output();
		} else {
			driveSystem.drive(226);
			driveSystem.drive(gyro, 90);
			driveSystem.drive(155);
			driveSystem.drive(gyro, -90);
			driveSystem.drive(121.65);
			driveSystem.drive(gyro, -90);
			driveSystem.drive(4.75);
			grabber.output();
		}
	
	}
	public void rightSwitch() {
		// TODO Auto-generated method stub
		
	}
	
	public void rightScale() {
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if('R'==gameData.charAt(0)) {
			driveSystem.drive(317.65);
			driveSystem.drive(gyro, -90);
			driveSystem.drive(4.75);
			grabber.output();
		} else {
			driveSystem.drive(226);
			driveSystem.drive(gyro, -90);
			driveSystem.drive(155);
			driveSystem.drive(gyro, 90);
			driveSystem.drive(121.65);
			driveSystem.drive(gyro, 90);
			driveSystem.drive(4.75);
			grabber.output();
		}
	
	}
}
