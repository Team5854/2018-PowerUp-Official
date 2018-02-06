package com.team5854.utils.sensors;

import edu.wpi.first.wpilibj.AnalogInput;

public class Ultrasonic {
	private AnalogInput input;
	public Ultrasonic(int channel) {
		input = new AnalogInput(channel);
	}
	
	public double getDistance() {
		double convertion = 21;
		double distance = input.getValue() / convertion;
		return distance;
	}
}
