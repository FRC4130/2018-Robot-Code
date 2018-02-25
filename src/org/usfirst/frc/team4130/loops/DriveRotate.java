package org.usfirst.frc.team4130.loops;

import org.usfirst.frc.team4130.subsystem.DriveTrain;

import com.ctre.phoenix.ILoopable;

public class DriveRotate implements ILoopable {
	
	DriveTrain _drive;
	double _targetDegree;
	double _degToTurn;
	double errorDeg;
	
	public DriveRotate(DriveTrain drive, double degToTurn) {
		
		_drive = drive;
		_degToTurn = degToTurn;
		
	}
	
	@Override
	public void onStart() {
		
		System.out.print("Turning to ");
		
		_targetDegree = _degToTurn+_drive.getHeading();
		
		System.out.print(_targetDegree);
		System.out.println("...");
		
	}

	@Override
	public void onLoop() {
		
		errorDeg = _drive.turnLow(_targetDegree);
		
	}

	@Override
	public boolean isDone() {
		
		boolean bool = Math.abs(errorDeg) < _drive.turnAcceptableError;
		if (bool) onStop();
		return bool;
		
	}

	@Override
	public void onStop() {
		
		System.out.print("Finished turning with an error of ");
		System.out.print(errorDeg);
		System.out.println(".");
		
	}

}
