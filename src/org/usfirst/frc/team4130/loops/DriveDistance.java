package org.usfirst.frc.team4130.loops;

import org.usfirst.frc.team4130.subsystem.DriveTrain;

import com.ctre.phoenix.ILoopable;

public class DriveDistance implements ILoopable {
	
	private double distanceNative;
	private DriveTrain _drive;
	private double targetNativeLeft;
	private double targetNativeRight;
	private double acceptableError = 350;
	
	public DriveDistance(DriveTrain driveTrain, double _native) {
		
		System.out.println("Drive Distance task has been created.");
		
		distanceNative = _native;
		_drive = driveTrain;
		
	}
	
	@Override
	public void onStart() {
		
		System.out.println("Drive Distance task has started.");
		
		targetNativeLeft = _drive.getLeftPos()+distanceNative;
		targetNativeRight = _drive.getRightPos()+distanceNative;
		
	}

	@Override
	public void onLoop() {
		// TODO Auto-generated method stub
		
		System.out.println("Driving...");
		
		_drive.setPosLeft(targetNativeLeft);
		_drive.setPosRight(targetNativeRight);
		
	}

	@Override
	public boolean isDone() {
		
		boolean leftAtPos = Math.abs(_drive.getLeftPos()) <= targetNativeLeft+acceptableError;
		boolean rightAtPos = Math.abs(_drive.getRightPos()) <= targetNativeRight+acceptableError;
		
		if (leftAtPos && rightAtPos) onStop();
		
		return false;//leftAtPos && rightAtPos;
	}

	@Override
	public void onStop() {
		
		_drive.driveDirect(0,0);
		System.out.println("Finished Driving");
		
	}

}
