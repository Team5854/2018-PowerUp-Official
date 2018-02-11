package com.team5854.utils.sensors;

import edu.wpi.first.wpilibj.AnalogInput;

public class Ultrasonic {
	private AnalogInput ultraSensor;
	public Ultrasonic(int channel) {
		ultraSensor = new AnalogInput(channel);
		
	}
	public double getDistance() {
		double dist = ultraSensor.getValue()*21;
		return dist;
		
	}

}
