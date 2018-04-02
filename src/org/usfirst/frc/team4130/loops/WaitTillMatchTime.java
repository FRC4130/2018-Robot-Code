package org.usfirst.frc.team4130.loops;

import com.ctre.phoenix.ILoopable;

import edu.wpi.first.wpilibj.DriverStation;

public class WaitTillMatchTime implements ILoopable {
	
	double t = 0;
	
	public WaitTillMatchTime(double timeRemainingSeconds) {
		t = timeRemainingSeconds;
	}
	
	@Override
	public void onStart() {
		System.out.println("[Info] Starting match time delay.");
	}

	@Override
	public void onLoop() {

	}

	@Override
	public boolean isDone() {
		if (DriverStation.getInstance().getMatchTime() <= t) {
			System.out.println("[Info] Match time delay finished.");
			return true;
		}
		return false;
	}

	@Override
	public void onStop() {

	}

}
