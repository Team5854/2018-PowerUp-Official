package org.usfirst.frc.team5854.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team5854.utils.Maths;
import com.team5854.utils.Toggle;
import com.team5854.utils.driveSystem.DriveSystem;
import com.team5854.utils.mechanism.Grabber;
import com.team5854.utils.mechanism.Telescope;
import com.team5854.utils.sensors.Ultrasonic;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.VictorSP;

public class Robot extends IterativeRobot {
	//--------------------SENSOR INITIALIZATION----------------
	ADXRS450_Gyro gyro; 
	Ultrasonic sonic;
	//---------------------GRABBER INITIALIZATION------------------
	VictorSP rightGrabber, leftGrabber;
	Grabber grabber;
	
	//-------------------------TELESCOPE MOTOR AND LIMIT SWITCH INITIALIZATION
	TalonSRX telescopeMotor;//telescope motor
	DigitalInput lowerLimit, upperLimit, bottomLimit;// limitswitches
	Telescope telescope;
	
	//--------------------JOYSTICK CONTROLERS----------
	Joystick mainJoystick; //driver joystick
	Joystick controlJoystick; //secondary joystick
	
	//--------------DRIVE MOTOR INITIALIZATION-------------
	TalonSRX frontLeft, frontRight, backLeft, backRight; //drive motors 
	DriveSystem driveSystem;//drive system
	Toggle driveStraightToggle;
	
	@Override
	public void robotInit() {
		//------------------SENSOR CONSTRUCTION----------
		gyro = new ADXRS450_Gyro();
		sonic = new Ultrasonic(0);
		
		//--------------------TELESCOPE MOTOR AND LIMIT SWITCH CONSTRUCTION-------------
		telescopeMotor = new TalonSRX(6);
		lowerLimit = new DigitalInput(2);
		upperLimit = new DigitalInput(1);
		bottomLimit = new DigitalInput(0);
		telescope = new Telescope(telescopeMotor, lowerLimit, upperLimit, bottomLimit);
		
		//------------------JOYSTICK CONSTRUCTION----------------
		mainJoystick = new Joystick(0);
		controlJoystick = new Joystick(1);
		
		//------------------GRABBER CONSTRUCTION---------------
		leftGrabber = new VictorSP(2);
		rightGrabber = new VictorSP(1);
		grabber = new Grabber(leftGrabber, rightGrabber);
		
		//-----------------DRIVE SYSTEM CONSTRUCTION--------
		frontLeft = new TalonSRX(2);
		backLeft = new TalonSRX(1);
		frontRight = new TalonSRX(7);
		backRight = new TalonSRX(8);
		driveSystem = new DriveSystem(frontLeft, frontRight, backLeft, backLeft);
		driveStraightToggle = new Toggle(mainJoystick);
		driveSystem.setPID(0, 0, 0);
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
	boolean driveStraightGyroReset = true;
	@Override
	public void teleopPeriodic() {
		//--------------DRIVE CODE----------------
		driveStraightToggle.toggle(2); //toggle if button 2 is pressed
		
		double xStick = mainJoystick.getRawAxis(3);//value for left stick
		double yStick =  mainJoystick.getRawAxis(1); //value for right stick
		double xSpeed = xStick / 2;
		double ySpeed = yStick / 2;
		if (driveStraightToggle.getState()) {//if Drive Straight Toggle
			if (driveStraightGyroReset) { //and gyro has not been reset
				driveStraightGyroReset = false; //set it so it won't reset multiple times
				gyro.reset();//reset gyro to 0
			}
			double avgSpeed = (xStick + yStick) / 2; //get the average speed of both joysticks
			double gyroAdjust = Maths.map(gyro.getAngle(), -20, 20, -.2, .2); //get the amount of gyro adjustment
			xSpeed = avgSpeed + gyroAdjust; 
			ySpeed = avgSpeed + gyroAdjust;
		} else {
			driveStraightGyroReset = true; //if toggle is off, allow gyro to be reset next time toggle is on.
		}
		driveSystem.drive(xSpeed, ySpeed); //drive code
		
		//------------------TELESCOPE CODE------------
		if(controlJoystick.getRawButton(7)) {
			this.telescope.upperLimit();
		} else if(controlJoystick.getRawButton(8)) {
			this.telescope.down();
		} else {
			this.telescope.stop();
		}
		
		//------------------GRABBER CODE-------------
		if (controlJoystick.getRawButton(9)) {
			grabber.intake();
		} else if (controlJoystick.getRawButton(10)) {
			grabber.output();
		} else {
			grabber.stop();
		}
	}

	public void testInit() {
		
	}
	
	@Override
	public void testPeriodic() {
		
	}
}
