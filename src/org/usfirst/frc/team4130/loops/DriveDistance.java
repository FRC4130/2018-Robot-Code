package org.usfirst.frc.team4130.loops;

import org.usfirst.frc.team4130.subsystem.DriveTrain;

import com.ctre.phoenix.ILoopable;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class DriveDistance implements ILoopable {
	
	private double distanceNative = 0;
	private DriveTrain _drive;
	private double targetNativeLeft;
	private double targetNativeRight;
	private double acceptableError = 4096/2;
	private Value gear;
	
	public DriveDistance(DriveTrain driveTrain, double inches, Value _gear) {
		
		System.out.println("Drive Distance task has been created.");
		
		distanceNative = ( ( 286899 * inches ) / 169.5 );
		_drive = driveTrain;
		
		_drive.setNeutralMode(NeutralMode.Brake);
		
		gear = _gear;
		
	}
	
	@Override
	public void onStart() {
		
		System.out.println("Drive Distance task has started.");
		
		targetNativeLeft = _drive.getLeftPos()+distanceNative;
		targetNativeRight = _drive.getRightPos()+distanceNative;
		
		System.out.println(_drive.getRightPos());
		System.out.println(distanceNative);
		System.out.println(targetNativeRight);
		
		_drive.setShifter(gear);
		
	}

	@Override
	public void onLoop() {
		
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
