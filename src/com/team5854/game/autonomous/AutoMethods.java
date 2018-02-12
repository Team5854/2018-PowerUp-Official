package com.team5854.game.autonomous;

import com.team5854.utils.driveSystem.DriveSystem;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class AutoMethods {
	private String gameData = "";
	private static ADXRS450_Gyro gyro; 
	private static DriveSystem driveSystem;
public static void centerLeft() {
	driveSystem.drive(gyro, 52);
	
}
}
