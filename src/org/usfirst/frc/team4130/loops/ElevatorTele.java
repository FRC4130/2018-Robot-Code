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
	private double pos = 0;
	private boolean manual = true;
	
	/**
	 * Controls the elevator motor in teleoporated.
	 */
	public ElevatorTele(Elevator elevator, Joystick gamepad){
		_elevator = elevator;
		_gamepad = gamepad;
	}
	
	@Override
	public void onStart() {
		System.out.println("ElevatorTele has been started!");
	}

	@Override
	public void onLoop() {
		
		if (_gamepad.getRawButtonPressed(3)) {
			
			manual = !manual;
			
		}
		
		if (!manual) {
		
			if (System.currentTimeMillis() > lastUpdate + updateSpeedMilliseconds) {
				if (Math.abs(_gamepad.getRawAxis(1)) >= .075 ) {
					pos-=_gamepad.getRawAxis(1)*updateMultiplier;
					pos = pos > 36759 ? 36759 : pos < 300 ? 300 : pos;
				}
				lastUpdate = System.currentTimeMillis();
			}
					
			if (_gamepad.getRawButton(2)) {
				pos = _elevator.chainHeightToNative(ElevatorPosition.Switch.value);
			}
			
			else if (_gamepad.getRawButton(4)){
				pos = _elevator.chainHeightToNative(ElevatorPosition.ScaleMax.value);
			}
			
			if(_gamepad.getRawButton(1)) {
				_elevator.setHome();
				pos = 0;
			}
			
			_elevator.setHeight(pos);
		
		}
		
		else {
			
			_elevator.driveDirect(_gamepad.getRawAxis(1)*-1);
			
		}
		
	}
	
	

	@Override
	public boolean isDone() {
		return false;
	}

	@Override
	public void onStop() {
		System.out.println("ElevatorTele has been stopped!");
		_elevator.driveDirect(0);
	}

}
