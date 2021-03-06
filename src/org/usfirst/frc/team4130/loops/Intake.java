package org.usfirst.frc.team4130.loops;

import org.usfirst.frc.team4130.robot.Subsystems;
import org.usfirst.frc.team4130.subsystem.Arms;

import com.ctre.phoenix.ILoopable;

public class Intake implements ILoopable {
	
	private Arms _arms;
	private double durriationMs = 500;
	private double endTimeMs = 0;
	
	@Deprecated
	public Intake(Arms arms) {
		_arms = arms;
	}
	
	public Intake() {
		_arms = Subsystems.arms;
	}
	
	public Intake(double durriationMS) {
		_arms = Subsystems.arms;
		durriationMs = durriationMS;
	}

	@Override
	public void onStart() {
		System.out.print("[Info] Started Intaking for ");
		System.out.print(durriationMs);
		endTimeMs = System.currentTimeMillis() + durriationMs;
	}

	@Override
	public void onLoop() {
		_arms.suck();
	}

	@Override
	public boolean isDone() {
		if ( System.currentTimeMillis() >= endTimeMs) {
			_arms.disableMotors();
			return true;
		}
		return false;
	}

	@Override
	public void onStop() {
		System.out.println("[WARNING] Stopped Intaking");
	}

}
