package org.usfirst.frc.team4130.loops;

import org.usfirst.frc.team4130.subsystem.Elevator;

import com.ctre.phoenix.ILoopable;

/**
 * Loopable class to set the height of the elevator.
 * @author West
 */
public class Elevate implements ILoopable {
	Elevator _elevator;
	double _height;
	
	public Elevate(Elevator ele, double heightInches) {
		_elevator = ele;
		_height = _elevator.chainHeightToNative(heightInches);
	}
	
	@Override
	public void onStart() {		
		System.out.print("Elevating to ");
		System.out.print(_height);
		System.out.println("...");
	}

	@Override
	public void onLoop() {
		_elevator.setHeight(_height);
	}

	@Override
	public boolean isDone() {
		boolean bool = Math.abs(_elevator.getError()) < 10;
		if (bool) onStop();
		return bool;
	}

	@Override
	public void onStop() {
		System.out.print("Finished elevating to ");
		System.out.print(_height);
		System.out.println(".");
	}

}
