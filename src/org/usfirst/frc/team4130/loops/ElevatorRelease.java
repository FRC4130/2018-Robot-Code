package org.usfirst.frc.team4130.loops;

import org.usfirst.frc.team4130.subsystem.Elevator;

import com.ctre.phoenix.ILoopable;

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
		System.out.println("Releasing Elevator");
	}

	@Override
	public void onLoop() {
		
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		boolean bool = System.currentTimeMillis() >= endTime;
		if (bool) {
			onStop();
		}
		return bool;
	}

	@Override
	public void onStop() {
		_elevator.setServo(1);
		System.out.println("Finished Releasing.");
	}

}
