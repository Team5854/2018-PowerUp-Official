/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5854.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team5854.utils.driveSystem.DriveSystem;
import com.team5854.utils.driveSystem.DriveSystem.DriveType;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends IterativeRobot {
	TalonSRX frontLeft = new TalonSRX(1);
	TalonSRX frontRight = new TalonSRX(3);
	TalonSRX backLeft = new TalonSRX(7);
	TalonSRX backRight = new TalonSRX(5);
	TalonSRX frontLeftTwo = new TalonSRX(2);
	TalonSRX frontRightTwo = new TalonSRX(4);
	TalonSRX backLeftTwo = new TalonSRX(8);
	TalonSRX backRightTwo = new TalonSRX(6);
	
	DriveSystem driveSystem;
	Joystick joystick;
	@Override
	public void robotInit() {
		driveSystem = new DriveSystem(DriveType.TankDrive, frontLeft, frontRight, backLeft, backRight,
				frontLeftTwo, frontRightTwo, backLeftTwo, backRightTwo);
		
		joystick = new Joystick(0);
	}

	@Override
	public void autonomousInit() {
		
	}

	@Override
	public void autonomousPeriodic() {
		
	}

	@Override
	public void teleopPeriodic() {
		driveSystem.drive(joystick.getRawAxis(1), joystick.getRawAxis(3));
	}

	@Override
	public void testPeriodic() {
		
	}
}
