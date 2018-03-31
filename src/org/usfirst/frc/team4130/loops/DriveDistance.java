package org.usfirst.frc.team4130.loops;

import org.usfirst.frc.team4130.robot.Subsystems;
import org.usfirst.frc.team4130.subsystem.DriveTrain;

import com.ctre.phoenix.ILoopable;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveDistance implements ILoopable {
	
	private double distanceNative = 0;
	private DriveTrain _drive;
	private double targetNativeLeft;
	private double targetNativeRight;
	private double acceptableError = 1000;
	
	private Value	gear			=	Subsystems.driveTrain.lowGear;
	private int 	cruiseVelocity	=	0;
	private int 	acceleration	=	0;
	
	@Deprecated
	public DriveDistance(DriveTrain driveTrain, double inches) {
		
		System.out.println("Drive Distance task has been created.");
		
		distanceNative = ( ( (2048*75) * inches ) / 92 );
		_drive = driveTrain;
		
	}
	
	public DriveDistance(double inches) {
		
		System.out.println("Drive Distance task has been created.");
		
		_drive = Subsystems.driveTrain;
		distanceNative = _drive.distanceToRotations(inches);
		gear = inches > (12*3) ? Subsystems.driveTrain.highGear : Subsystems.driveTrain.lowGear;
		
	}
	
	public DriveDistance(double inches, Value gear1, int cruiseVelocity1, int acceleration1) {
		
		System.out.println("Custom Drive Distance task has been created.");
		
		_drive = Subsystems.driveTrain;
		
		distanceNative = _drive.distanceToRotations(inches);
		gear = gear1;
		cruiseVelocity = cruiseVelocity1;
		acceleration = acceleration1;
		
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
		
		if (cruiseVelocity > 0 && acceleration > 0) {
			_drive.setMagic(cruiseVelocity, acceleration);
		}
		
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
