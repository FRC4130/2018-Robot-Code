package org.usfirst.frc.team4130.loops;

import org.usfirst.frc.team4130.subsystem.DriveTrain;

import com.ctre.phoenix.ILoopable;

public class DriveDistance implements ILoopable {
	
	private double distanceNative = 0;
	private DriveTrain _drive;
	private double targetNativeLeft;
	private double targetNativeRight;
	private double acceptableError = 4096/2;
	
	public DriveDistance(DriveTrain driveTrain, double inches) {
		
		System.out.println("Drive Distance task has been created.");
		
		distanceNative = ( ( 286899 * inches ) / 169.5 );
		_drive = driveTrain;
		
	}
	
	@Override
	public void onStart() {
		
		System.out.println("Drive Distance task has started.");
		
		targetNativeLeft = _drive.getLeftPos()+distanceNative;
		targetNativeRight = _drive.getRightPos()+distanceNative;
		
		_drive.setShifter(_drive.highGear);
		
	}

	@Override
	public void onLoop() {
		// TODO Auto-generated method stub
		
		_drive.setPosLeft(targetNativeLeft);
		_drive.setPosRight(targetNativeRight);
		
	}

	@Override
	public boolean isDone() {
		
		boolean leftAtPos = Math.abs(targetNativeLeft - _drive.getLeftPos()) <= acceptableError;
		boolean rightAtPos = Math.abs(targetNativeRight - _drive.getRightPos()) <= acceptableError;
		
		if (leftAtPos && rightAtPos) {
			System.out.println("Finished Driving");
			return true;
		}
		
		return false;
	}

	@Override
	public void onStop() {
		
		//_drive.driveDirect(0,0);
		System.out.println("Finished Driving");
		
	}

}
