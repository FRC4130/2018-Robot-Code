package org.usfirst.frc.team4130.loops;

import org.usfirst.frc.team4130.robot.Subsystems;
import org.usfirst.frc.team4130.subsystem.DriveTrain;

import com.ctre.phoenix.ILoopable;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveDistance implements ILoopable {
	
	private double distanceNative;
	private double distanceInches;
	private DriveTrain _drive;
	private double acceptableError = 1000;
	
	private Value	gear			=	Subsystems.driveTrain.lowGear;
	private int 	cruiseVelocity	=	0;
	private int 	acceleration	=	0;
	
	@Deprecated
	public DriveDistance(DriveTrain driveTrain, double inches) {
		
		System.out.println("Drive Distance task has been created.");
		
		gear = Subsystems.driveTrain.lowGear;
		distanceInches = inches;
		_drive = driveTrain;
		
	}
	
	public DriveDistance(double inches) {
		
		System.out.println("Drive Distance task has been created.");
		
		distanceInches = inches;
		_drive = Subsystems.driveTrain;
		gear = Math.floor(inches) > 150 ? Subsystems.driveTrain.highGear : Subsystems.driveTrain.lowGear;
		//Force low gear
		gear = _drive.lowGear;
		//Force high gear
		//gear = _drive.highGear;
	}
	
	public DriveDistance(double inches, Value gear1) {
		
		System.out.println("Drive Distance task has been created.");
		
		distanceInches = inches;
		_drive = Subsystems.driveTrain;
		gear = gear1;
	}
	
	public DriveDistance(double inches, Value gear1, int cruiseVelocity1, int acceleration1) {
		
		System.out.println("Custom Drive Distance task has been created.");
		
		_drive = Subsystems.driveTrain;
		
		distanceInches = inches;
		gear = gear1;
		cruiseVelocity = cruiseVelocity1;
		acceleration = acceleration1;
		
	}
	
	@Override
	public void onStart() {
		
		_drive.resetSensors();
		
		System.out.print("[Info] Driving ");
		System.out.println(distanceInches);
		_drive.setShifter(gear);
		_drive.setNeutralMode(NeutralMode.Brake);
		
		distanceNative = _drive.distanceToRotations(distanceInches);
		
		if (cruiseVelocity > 0 && acceleration > 0) {
			_drive.setMagic(cruiseVelocity, acceleration);
		}
		else if (gear == _drive.highGear) {
			_drive.setMagicHighDefault();
		}
		else {
			System.out.println("SET TO LOW DEFAUTLS");
			_drive.setMagicLowDefault();
		}
		
	}

	@Override
	public void onLoop() {
		
		_drive.setPosLeft(distanceNative);
		_drive.setPosRight(distanceNative);
		_drive.putDash();
		
	}

	@Override
	public boolean isDone() {
		
		boolean leftAtPos = Math.abs(distanceNative - _drive.getLeftPos()) <= acceptableError;
		boolean rightAtPos = Math.abs(distanceNative - _drive.getRightPos()) <= acceptableError;
		
		SmartDashboard.putNumber("Left Error", distanceNative - _drive.getLeftPos());
		SmartDashboard.putNumber("Right Error", distanceNative - _drive.getRightPos());
		
		if (leftAtPos && rightAtPos) {
			System.out.println("[Info] Finished Driving for Distance");
			System.out.println("[WARNING] The DriveTrain is still in the Motion Magic Control Mode");
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
