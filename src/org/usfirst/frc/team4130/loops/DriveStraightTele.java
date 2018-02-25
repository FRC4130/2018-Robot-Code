package org.usfirst.frc.team4130.loops;

import org.usfirst.frc.team4130.subsystem.DriveTrain;

import com.ctre.phoenix.ILoopable;

public class DriveStraightTele implements ILoopable {
	
	DriveTrain _drive;
	double targetAngle;
	
	public DriveStraightTele(DriveTrain drive) {
		
		_drive = drive;
		
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		targetAngle = _drive.getHeading();
		
	}

	@Override
	public void onLoop() {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		
		
	}

}
