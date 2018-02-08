package org.usfirst.frc.team5854.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team5854.utils.CameraSystem;
import com.team5854.utils.driveSystem.DriveSystem;
import com.team5854.utils.driveSystem.DriveSystem.DriveType;
import com.team5854.utils.sensors.Ultrasonic;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends IterativeRobot {
	TalonSRX frontLeft = new TalonSRX(4);
	TalonSRX frontRight = new TalonSRX(1);
	TalonSRX backLeft = new TalonSRX(5);
	TalonSRX backRight = new TalonSRX(7);
	TalonSRX frontLeftTwo = new TalonSRX(3);
	TalonSRX frontRightTwo = new TalonSRX(2);
	TalonSRX backLeftTwo = new TalonSRX(6);
	TalonSRX backRightTwo = new TalonSRX(8);
	Thread cameras;
	DriveSystem driveSystem;
	Joystick joystick;
	
	Ultrasonic sonic = new Ultrasonic(0);
	@Override
	public void robotInit() {
		driveSystem = new DriveSystem(DriveType.TankDrive, frontLeft, frontRight, backLeft, backRight,
				frontLeftTwo, frontRightTwo, backLeftTwo, backRightTwo);
		joystick = new Joystick(0);
		cameras = new Thread(new CameraSystem());
		cameras.start();
	}

	@Override
	public void autonomousInit() {
		
	}

	@Override
	public void autonomousPeriodic() {
		
	}

	@Override
	public void teleopPeriodic() {
		double left = joystick.getRawAxis(1) / 2;
		double right = joystick.getRawAxis(3) / 2;
		driveSystem.drive(left, right);
	}

	
	boolean isLoop = true;
	
	public void testInit() {
		isLoop = true;
	}
	
	@Override
	public void testPeriodic() {
		System.out.println(sonic.getDistance());
		driveSystem.drive(sonic, 15);
	}
}
