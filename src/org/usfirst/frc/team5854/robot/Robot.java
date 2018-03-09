package org.usfirst.frc.team5854.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team5854.game.autonomous.AutoMethods;
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
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	
	
	//--------------CONSTRUCT SMARTDASHBOARD----------
	SendableChooser<String> autoPlacement;
	final String autoLeft = "LEFT";
	final String autoRight = "RIGHT";
	final String autoCenterLeft = "CLEFT";
	final String autoCenterRight = "CRIGHT";
	SendableChooser<String> autoObjective;
	final String autoSwitch = "SWITCH";
	final String autoScale = "SCALE";
	
	//-------------SETUP AUTONOMOUS OBJECT----------
	AutoMethods autonomous;
	
	
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
		frontLeft = new TalonSRX(7);
		backLeft = new TalonSRX(8);
		frontRight = new TalonSRX(2);
		backRight = new TalonSRX(1);
		driveSystem = new DriveSystem(frontLeft, frontRight, backLeft, backRight);
		driveStraightToggle = new Toggle(mainJoystick);
		driveSystem.setPID(0, 0, 0);
		
		//-----------SMARTDASHBOARD CONSTRUCTION-------
		autoPlacement = new SendableChooser<String>();
		autoPlacement.addDefault(autoLeft, autoLeft);
		autoPlacement.addObject(autoRight, autoRight);
		autoPlacement.addObject(autoCenterLeft, autoCenterLeft);
		autoPlacement.addObject(autoCenterRight, autoCenterRight);
		SmartDashboard.putData("Robot Placement:", autoPlacement);
		autoObjective = new SendableChooser<String>();
		autoObjective.addDefault(autoSwitch, autoSwitch);
		autoObjective.addObject(autoScale, autoScale);
		SmartDashboard.putData("Robot Objective:", autoObjective);
		
		//----------CONSTRUCT AUTONOMOUS OBJECT----------
		autonomous = new AutoMethods(gyro, driveSystem, grabber, sonic);
	}

	@Override
	public void autonomousInit() {
		
	}

	@Override
	public void autonomousPeriodic() {
		String placement = autoPlacement.getSelected();
		String objective = autoObjective.getSelected();
		switch (placement) {
		case autoLeft:
			if (objective.equals(autoSwitch)) {
				autonomous.leftSwitch();
			} else {
				autonomous.leftScale();
			}
			break;
		case autoRight:
			if (objective.equals(autoSwitch)) {
				autonomous.rightSwitch();
			} else {
				autonomous.rightScale();
			}
			break;
		case autoCenterLeft:
			if (objective.equals(autoSwitch)) {
				autonomous.cLeftSwitch();
			} else {
				autonomous.cLeftScale();
			}
			break;
		case autoCenterRight:
			if (objective.equals(autoSwitch)) {
				autonomous.cRightSwitch();
			} else {
				autonomous.cRightScale();
			}
			break;
		}
	}
	@Override
	public void teleopInit() {
		driveSystem.setEncoder(0);
		
	}
	boolean driveStraightGyroReset = true;
	@Override
	public void teleopPeriodic() {
		//--------------DRIVE CODE----------------
		driveStraightToggle.toggle(2); //toggle if button 2 is pressed
		
		double xStick = mainJoystick.getRawAxis(1);//value for left stick
		double yStick =  mainJoystick.getRawAxis(3); //value for right stick
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
		System.out.println("ENCODER POSITIO: " + driveSystem.getLeftEncoderPosition() + " " + driveSystem.getRightEncoderPosition());
	}
	boolean once = false;
	public void testInit() {
		double P = (0.6 * 1023) / 4096;
		double I = P/50000;
		double D = P * 1000;
		driveSystem.setPID(P, I, D);
		driveSystem.setupAutonomous();
		driveSystem.setEncoder(0);
		once = true;
	}
	@Override
	public void testPeriodic() {
		if (once) {
			System.out.println("Running");
			double encoderTarget = driveSystem.drive(3);
			System.out.println(encoderTarget);
//			while (driveSystem.getAvgEncoderPosition() < encoderTarget-5 || driveSystem.getAvgEncoderPosition() > encoderTarget+5) {
//				
//			}
//			System.out.println("FINISHED");
			once = false;
		}
		System.out.println("Encoder Position: " + driveSystem.getLeftEncoderPosition() + " " + driveSystem.getRightEncoderPosition());
		SmartDashboard.putNumber("Position Left", driveSystem.getLeftEncoderPosition());
		SmartDashboard.putNumber("Position Right", driveSystem.getRightEncoderPosition());
	}
}
