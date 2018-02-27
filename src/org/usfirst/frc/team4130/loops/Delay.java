package org.usfirst.frc.team4130.loops;

import com.ctre.phoenix.ILoopable;

/**
 * Waits a particular amount of time before finishing.
 * @author West
 */
public class Delay implements ILoopable {
	
	double delay = 0;
	double stopTime = 0;
	
	/**
	 * Constructor
	 * @param delayMs time to delay in Milliseconds
	 */
	public Delay(double delayMs) {
		
		delay = delayMs;
		
	}
	
	@Override
	public void onStart() {
		
		stopTime = System.currentTimeMillis() + delay;
		System.out.println("Starting Delay");
		
	}

	@Override
	public void onLoop() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isDone() {
		boolean finished = System.currentTimeMillis() >= stopTime;
		if (finished) System.out.println("Finished Delay.");
		return finished;
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub

	}

}
