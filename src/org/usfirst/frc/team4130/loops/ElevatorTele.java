package org.usfirst.frc.team4130.loops;
import org.usfirst.frc.team4130.subsystem.Elevator;
import org.usfirst.frc.team4130.subsystem.ElevatorPosition;

import com.ctre.phoenix.ILoopable;

import edu.wpi.first.wpilibj.Joystick;

public class ElevatorTele implements ILoopable{
	private Elevator _elevator;
	private Joystick _gamepad;
	private double lastUpdate = 0;
	private final double updateSpeedMilliseconds = 25;
	private final double updateMultiplier = 340;
	private boolean manual = false;
	private double pos = 0;
	
	public ElevatorTele(Elevator elevator, Joystick gamepad){
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
		
		if(_gamepad.getRawButtonPressed(3)) {
			
			manual = !manual;
			
			System.out.println(manual);
			
		}
		
		if(_gamepad.getRawButton(1)) {
			
			_elevator.setHome();
			System.out.println("Homing");
			
			pos = 0;
			
		}
		
		else if (manual && System.currentTimeMillis() > lastUpdate + updateSpeedMilliseconds) {
				
			if (Math.abs(_gamepad.getRawAxis(1)) >= .075 ) {
				pos-=_gamepad.getRawAxis(1)*updateMultiplier;
				pos = pos > 36759 ? 36759 : pos < 300 ? 300 : pos;
				
			}
			
			lastUpdate = System.currentTimeMillis();
				
			_elevator.setHeight(pos);
				
		}

		else if (!manual) {
				
				if (_gamepad.getRawButton(2)) {
					pos = _elevator.chainHeightToNative(ElevatorPosition.Switch.value);
				}
				else {
					pos = _elevator.chainHeightToNative(ElevatorPosition.Home.value);
				}
				
				_elevator.setHeight(pos);
				
			}
		
		//System.out.println(pos);
		
		//_elevator.setHeight(pos);
		
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
