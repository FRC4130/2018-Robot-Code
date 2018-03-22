package org.usfirst.frc.team4130.loops;

import org.usfirst.frc.team4130.robot.Subsystems;
import org.usfirst.frc.team4130.subsystem.DriveTrain;

import com.ctre.phoenix.ILoopable;
import com.ctre.phoenix.motorcontrol.NeutralMode;


public class DriveForTime implements ILoopable {
	
	double stopTime = 0;
	double delay = 0;
	double throttle = 0;
	double turn = 0;
	DriveTrain drive;
	
	@Deprecated
	public DriveForTime(DriveTrain _drive, double TimeToDriveMS, double percentThrottle, double percentTurn) {
		delay = TimeToDriveMS;
		drive = _drive;
		turn = percentTurn;
		throttle = percentThrottle;
	}
	
	public DriveForTime(double TimeToDriveMS, double percentThrottle, double percentTurn) {
		delay = TimeToDriveMS;
		drive = Subsystems.driveTrain;
		turn = percentTurn;
		throttle = percentThrottle;
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
