package org.usfirst.frc.team4130.loops;

import org.usfirst.frc.team4130.robot.Subsystems;
import org.usfirst.frc.team4130.subsystem.Elevator;

import com.ctre.phoenix.ILoopable;

public class ElevatorRelease implements ILoopable {
	
	Elevator _elevator;
	double durrationMS = 500;
	double stopTime = 0;
	
	@Deprecated
	public ElevatorRelease(Elevator ele) {
		
		_elevator = ele;
		
	}
	
	public ElevatorRelease() {
		
		_elevator = Subsystems.elevator;
		
	}
	
	public ElevatorRelease(double durrationMS1) {
		
		durrationMS = durrationMS1;
		
	}
	
	@Override
	public void onStart() {
		
		System.out.println("[Info] Relasing Elevator");
		
		_elevator.setServo(false);
		stopTime = durrationMS + System.currentTimeMillis();
		
	}

	@Override
	public void onLoop() {
		
		_elevator.setServo(false);
		
	}

	@Override
	public boolean isDone() {
		
		if (System.currentTimeMillis() > stopTime) {
			
			_elevator.setServo(true);
			
			return true;
			
		}
		
		return false;
		
	}

	@Override
	public void onStop() {
		
		System.out.println("[WARNING] Release has been stopped");
		
	}

}
