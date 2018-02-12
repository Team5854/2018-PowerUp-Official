package org.usfirst.frc.team5854.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team5854.utils.driveSystem.DriveSystem;
import com.team5854.utils.mechanism.Telescope;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.VictorSP;

public class Robot extends IterativeRobot {
	VictorSP rightGrabber, leftGrabber;
	
	TalonSRX telescopeMotor;
	DigitalInput lowerLimit, upperLimit, bottomLimit;
	Telescope telescope;
	Joystick joystick;
	
	TalonSRX frontLeft, frontRight, backLeft, backRight; 
	DriveSystem driveSystem;
	@Override
	public void robotInit() {
		telescopeMotor = new TalonSRX(6);
		lowerLimit = new DigitalInput(0);
		upperLimit = new DigitalInput(1);
		bottomLimit = new DigitalInput(2);
		telescope = new Telescope(telescopeMotor, lowerLimit, upperLimit, bottomLimit);
		joystick = new Joystick(0);
		
		leftGrabber = new VictorSP(2);
		rightGrabber = new VictorSP(1);
		
		frontLeft = new TalonSRX(2);
		backLeft = new TalonSRX(1);
		frontRight = new TalonSRX(7);
		backRight = new TalonSRX(8);
		driveSystem = new DriveSystem(frontLeft, frontRight, backLeft, backLeft);
	}

	@Override
	public void autonomousInit() {
		
	}

	@Override
	public void autonomousPeriodic() {
		
	}
	@Override
	public void teleopInit() {
		
	}
	@Override
	public void teleopPeriodic() {
		driveSystem.drive(joystick.getRawAxis(3), joystick.getRawAxis(1));
		if(joystick.getRawButton(1)) {
			this.telescopeMotor.set(ControlMode.PercentOutput, .5);
		} else if(joystick.getRawButton(2)) {
			this.telescopeMotor.set(ControlMode  .PercentOutput, -.5);
		} else {
			this.telescopeMotor.set(ControlMode.PercentOutput, 0);
		}
		if (joystick.getRawButton(3)) {
			rightGrabber.set(-1);
			leftGrabber.set(1);
		} else if (joystick.getRawButton(4)) {
			rightGrabber.set(1);
			leftGrabber.set(-1);
		} else {
			rightGrabber.set(0);
			leftGrabber.set(0);
		}
	}

	
	public void testInit() {
		
	}
	
	@Override
	public void testPeriodic() {
		
	}
}
