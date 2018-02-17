package org.usfirst.frc.team4130.loops;
import org.usfirst.frc.team4130.robot.RobotMap;
import org.usfirst.frc.team4130.robot.Subsystems;
import org.usfirst.frc.team4130.subsystem.Elevator;

import com.ctre.phoenix.ILoopable;

import edu.wpi.first.wpilibj.Joystick;

public class Elevate implements ILoopable{
	private Elevator _elevator;
	private Joystick _gamepad;
	
	public Elevate(Elevator elevator, Joystick gamepad){
		_elevator = elevator;
		_gamepad = gamepad;
	}
	
	@Override
	public void onStart() {
		//Home the elevator. TODO: Make sure this function works.
		//Do we want to do home at the start of Teleop?
		//_elevator.setHome();
	}

	@Override
	public void onLoop() {
		//This is code to test the elevator.  Use the left Y-axis of the operator joystick to drive the elevator up and down.
		//TODO: Use this to determine MM velocity/acceleration and confirm motor direction/sensor phase.
		_elevator.driveDirect(_gamepad.getRawAxis(1));
		if(_gamepad.getRawButton(1)){
			_elevator.setHome();
		}
	}

	@Override
	public boolean isDone() {

		return false;
	}

	@Override
	public void onStop() {
		_elevator.driveDirect(0);
		
	}

}
