package org.usfirst.frc.team4130.loops;

import org.usfirst.frc.team4130.robot.Subsystems;
import org.usfirst.frc.team4130.subsystem.Elevator;

import com.ctre.phoenix.ILoopable;

public class Elevate implements ILoopable {
	Elevator _elevator;
	double _height;
	double acceptableErr = 20;
	
	@Deprecated
	public Elevate(Elevator ele, double heightInches) {
		_elevator = ele;
		_height = _elevator.chainHeightToNative(heightInches);
	}
	
	public Elevate(double heightInches) {
		_elevator = Subsystems.elevator;
		_height = _elevator.chainHeightToNative(heightInches);
	}
	
	@Override
	public void onStart() {		
		System.out.print("[Info] Elevating to ");
		System.out.print(_height);
		System.out.println("\"");
	}

	@Override
	public void onLoop() {
		_elevator.setHeight(_height);
	}

	@Override
	public boolean isDone() {
		if (Math.abs(_elevator.getError()) < acceptableErr) {
			System.out.print("[Info] Finished elevating to ");
			System.out.print(_height);
			System.out.println("\"");
			return true;
		}
		return false;
	}

	@Override
	public void onStop() {
		System.out.print("[WARNING] Elevate was stopped");
	}

}
