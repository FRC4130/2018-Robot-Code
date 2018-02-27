package org.usfirst.frc.team4130.loops;

import org.usfirst.frc.team4130.subsystem.Elevator;

import com.ctre.phoenix.ILoopable;

/**
 * Used at the start of the match to release
 * the intake from it's starting position.
 * @author West
 */
public class ElevatorRelease implements ILoopable {
	
	Elevator _elevator;
	double durrationMS = 500;
	double endTime = 0;
	
	public ElevatorRelease(Elevator ele) {
		
		_elevator = ele;
		
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		_elevator.setServo(0);
		endTime = System.currentTimeMillis() + durrationMS;
		System.out.println("Releasing Elevator.");
	}

	@Override
	public void onLoop() {
		
	}

	@Override
	public boolean isDone() {
		if (System.currentTimeMillis() >= endTime) {
			_elevator.setServo(1);
			System.out.println("Finished Releasing.");
			return true;
		}
		return false;
	}

	@Override
	public void onStop() {
		System.out.println("Elevator release has been stopped!");
	}

}
