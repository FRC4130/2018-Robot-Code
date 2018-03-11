package org.usfirst.frc.team4130.loops;

import org.usfirst.frc.team4130.subsystem.DriveTrain;

import com.ctre.phoenix.ILoopable;
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class DriveForTime implements ILoopable {
	
	double stopTime = 0;
	double delay = 0;
	double throttle = 0;
	double turn = 0;
	DriveTrain drive;
	
	public DriveForTime(DriveTrain _drive, double ms, double _throttle, double _turn) {
		delay = ms;
		drive = _drive;
		turn = _turn;
		throttle = _throttle;
	}

	@Override
	public void onStart() {
		
		drive.setNeutralMode(NeutralMode.Coast);
		
		System.out.println("[Info] Drive for time has started");
		
		stopTime = delay + System.currentTimeMillis();
		
	}

	@Override
	public void onLoop() {
		
		drive.arcade(throttle, turn);
		
	}

	@Override
	public boolean isDone() {
		if (System.currentTimeMillis() >= stopTime) {
			drive.driveDirect(0, 0);
			System.out.println("[Info] Drive for time has finished");
			return true;
		}
		return false;
	}

	@Override
	public void onStop() {
		drive.driveDirect(0, 0);
		System.out.println("[WARNING] Drive for time has been stopped");
	}

}
