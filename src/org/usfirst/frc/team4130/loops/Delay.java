package org.usfirst.frc.team4130.loops;

import com.ctre.phoenix.ILoopable;

public class Delay implements ILoopable {
	
	double delay = 0;
	double stopTime = 0;
	boolean done = false;
	
	public Delay(int delayMs) {
		
		delay = delayMs;
		
	}
	
	@Override
	public void onStart() {
		
		stopTime = System.currentTimeMillis() + delay;
		System.out.print("[Info] Starting Delay for ");
		System.out.print(delay);
		System.out.print("ms");
		
	}

	@Override
	public void onLoop() {

		done = System.currentTimeMillis() >= stopTime;
		
	}

	@Override
	public boolean isDone() {
		if (done) {
			System.out.println("[Info] Finished Delaying");
			return true;
		}
		return false;
	}

	@Override
	public void onStop() {
		
		System.out.println("[WARNING] Delay was stopped");

	}

}
