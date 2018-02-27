package org.usfirst.frc.team4130.loops;

import org.usfirst.frc.team4130.subsystem.DriveTrain;

import com.ctre.phoenix.ILoopable;

/**
 * A Loopable class for motion magic driving
 * @author West
 */
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
		
		System.out.print("Driving ");
		System.out.print(distanceNative);
		System.out.println(" Native Units...");
		
		targetNativeLeft = _drive.getLeftPos()+distanceNative;
		targetNativeRight = _drive.getRightPos()+distanceNative;
		
		System.out.print("Target position left: ");
		System.out.println(targetNativeLeft);
		System.out.print("Target position right: ");
	}

	@Override
	public void onLoop() {
		
		_drive.setPosLeft(targetNativeLeft);
		_drive.setPosRight(targetNativeRight);
		
	}

	@Override
	public boolean isDone() {
		
		boolean leftAtPos = Math.abs(_drive.getLeftPos()) <= targetNativeLeft+acceptableError;
		boolean rightAtPos = Math.abs(_drive.getRightPos()) <= targetNativeRight+acceptableError;
		
		if (leftAtPos && rightAtPos) {
			System.out.println("Drive Distance has finished.");
			System.out.println("WARNING! the drive train will continue to hold position until it is used for something else or disabled.");
			return true;
		}
		
		return false;
	}

	@Override
	public void onStop() {
		
		_drive.driveDirect(0,0);
		System.out.println("Drive Distance Has been stopped!");
		
	}

}
