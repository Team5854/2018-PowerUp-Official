package org.usfirst.frc.team5854.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team5854.utils.mechanism.Telescope;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends IterativeRobot {
	TalonSRX telescopeMotor;
	DigitalInput lowerLimit, upperLimit, bottomLimit;
	Telescope telescope;
	Joystick joystick;

	@Override
	public void robotInit() {
		telescopeMotor = new TalonSRX(3);
		lowerLimit = new DigitalInput(0);
		upperLimit = new DigitalInput(1);
		bottomLimit = new DigitalInput(2);
		telescope = new Telescope(telescopeMotor, lowerLimit, upperLimit, bottomLimit);
		joystick = new Joystick(0);
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
		if(joystick.getRawButton(1)) {
		telescope.upperLimit();
		} else if(joystick.getRawButton(2)) {
			telescope.lowerLimit();
		} else if(joystick.getRawButton(3)) {
			telescope.down();
		}
	}

	
	public void testInit() {
		
	}
	
	@Override
	public void testPeriodic() {
		
	}
}
