/**
 * 
 * @author Veronica Winebarger (ebolakitten) 02/03/18
 * This is the base grabber code for the robots intake/output system.
 *
 */
package com.team5854.utils.mechanism;

import edu.wpi.first.wpilibj.VictorSP;

public class Grabber {
	private VictorSP left;
	private VictorSP right;
	public Grabber(VictorSP left, VictorSP right) {
		this.left = left;
		this.right = right;
	}
	/**
	 * Intake mode
	 */
	public void intake() {
		this.left.set(-1);
		this.right.set(1);
	}
	/**
	 * Output mode
	 */
	public void output() {
		this.left.set(1);
		this.right.set(-1);
	}
	
	public void stop() {
		this.left.set(0);
		this.right.set(0);
	}
}
