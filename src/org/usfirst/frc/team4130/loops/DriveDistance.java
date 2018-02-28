package org.usfirst.frc.team4130.loops;

import org.usfirst.frc.team4130.subsystem.DriveTrain;

import com.ctre.phoenix.ILoopable;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A Loopable class for motion magic driving
 * @author West
 */
public class DriveDistance implements ILoopable {
	
	private double distanceNative;
	private DriveTrain _drive;
	private double targetNativeLeft;
	private double targetNativeRight;
	
	private double errorLeft;
	private double errorRight;
	
	private int debounced = 0;
	private int debouncedTarget = 25;
	private double acceptableError = 4096*2;
	
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
		
		targetNativeLeft  = _drive.getLeftPos() + distanceNative;
		targetNativeRight = _drive.getRightPos()+ distanceNative;
		
	}

	@Override
	public void onLoop() {
		
		errorLeft  = targetNativeLeft  - _drive.getLeftPos();
		errorRight = targetNativeRight - _drive.getRightPos();
		
		_drive.setPosLeft(targetNativeLeft);
		_drive.setPosRight(targetNativeRight);
		
		SmartDashboard.putNumber("ErrorL" , errorLeft );
		SmartDashboard.putNumber("ErrorR", errorRight);
		
	}

	@Override
	public boolean isDone() {
		
		boolean leftAtPos = Math.abs( errorLeft ) <= acceptableError;
		boolean rightAtPos = Math.abs( errorRight ) <= acceptableError;
		
		debounced+= leftAtPos && rightAtPos ? 1 : -1;
		
		debounced = debounced > debouncedTarget*2  ? debouncedTarget*2 : debounced < 0 ? 0 : debounced;
		
		if (debounced >= debouncedTarget) {
			System.out.println("Drive Distance has finished.(");
			System.out.println("WARNING! The drive train will continue to hold position until it is used for something else or disabled.");
		}
		
		return debounced >= debouncedTarget;
	}

	@Override
	public void onStop() {
		
		_drive.driveDirect(0,0);
		System.out.println("Drive Distance Has been stopped!");
		System.out.println("WARNING! The drive train has been disabled.");
		
	}

}
