package org.usfirst.frc.team4130.loops;

import org.usfirst.frc.team4130.subsystem.DriveTrain;

import com.ctre.phoenix.ILoopable;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveDistance implements ILoopable {
	
	private double distanceNative = 0;
	private DriveTrain _drive;
	private double targetNativeLeft;
	private double targetNativeRight;
	private double acceptableError = 1000;
	
	public DriveDistance(DriveTrain driveTrain, double inches) {
		
		System.out.println("Drive Distance task has been created.");
		
		distanceNative = ( ( (2048*75) * inches ) / 92 );
		_drive = driveTrain;
		
	}
	
	@Override
	public void onStart() {
		
		_drive.resetSensors();
		
		System.out.println("[Info] Started Driving for Distance");
		
		targetNativeLeft = distanceNative;
		targetNativeRight = distanceNative;
		
		System.out.println(_drive.getRightPos());
		System.out.println(distanceNative);
		
		_drive.setShifter(_drive.lowGear);
		_drive.setNeutralMode(NeutralMode.Brake);
		
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
		
		SmartDashboard.putNumber("Left Error", targetNativeLeft - _drive.getLeftPos());
		SmartDashboard.putNumber("Right Error", targetNativeRight - _drive.getRightPos());
		
		if (leftAtPos && rightAtPos) {
			System.out.println("[Info] Finished Driving for Distance");
			return true;
		}
		
		return false;
		
	}

	@Override
	public void onStop() {
		
		System.out.println("[WARNING] Driving for distance was stopped");
		System.out.println("[WARNING] The DriveTrain is still in the Motion Magic Control Mode");
		
	}

}
