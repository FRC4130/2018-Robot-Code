package org.usfirst.frc.team4130.loops;

import org.usfirst.frc.team4130.subsystem.Arms;

import com.ctre.phoenix.ILoopable;

public class Intake implements ILoopable {
	
	private Arms _arms;
	private double durriationMs = 500;
	private double endTimeMs = 0;
	
	public Intake(Arms arms) {
		_arms = arms;
	}

	@Override
	public void onStart() {
		System.out.print("Intaking for ");
		System.out.print(durriationMs);
		System.out.println(".");
		endTimeMs = System.currentTimeMillis() + durriationMs;
	}

	@Override
	public void onLoop() {
		_arms.suck();
	}

	@Override
	public boolean isDone() {
		boolean bool = System.currentTimeMillis() >= endTimeMs;
		if (bool) onStop();
		return bool;
	}

	@Override
	public void onStop() {
		_arms.disableMotors();
		System.out.println("Finished Intaking.");
	}

}
