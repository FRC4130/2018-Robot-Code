package org.usfirst.frc.team4130.loops;

import com.ctre.phoenix.ILoopable;
import org.usfirst.frc.team4130.subsystem.Arms;

public class Outtake implements ILoopable {
	
	Arms _arms;
	private double durriationMs = 500;
	private double endTimeMs = 0;
	
	public Outtake(Arms arms) {
		_arms = arms;
	}
	
	@Override
	public void onStart() {
		System.out.print("Outaking for ");
		System.out.print(durriationMs);
		System.out.println(".");
		endTimeMs = System.currentTimeMillis() + durriationMs;
	}

	@Override
	public void onLoop() {
		_arms.spit();

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
		_arms.setSolenoid(_arms.opened);
		System.out.println("Finished Outtaking.");
	}

}
