package org.usfirst.frc.team4130.loops;

import org.usfirst.frc.team4130.subsystem.Arms;

import com.ctre.phoenix.ILoopable;

import edu.wpi.first.wpilibj.Joystick;

public class ArmsTele implements ILoopable {
	private Arms _arms;
	private Joystick _driver;
	private Joystick _operator;
	
	private boolean clamped = true;
	
	public ArmsTele (Arms arms, Joystick driver, Joystick operator) {
		_arms = arms;
		_driver = driver;
		_operator = operator;
	}
	
	@Override
	public void onStart() {
		
		clamped = _arms.getClamped();
		
	}

	@Override
	public void onLoop() {
		
		if (_driver.getRawButtonPressed(5)) {
			clamped = !clamped;
		}
		
		if (_operator.getRawAxis(2)*-1+_operator.getRawAxis(3) != 0) {
			_arms.driveDirect(_operator.getRawAxis(2)*-1+_operator.getRawAxis(3),
							  _operator.getRawAxis(2)*-1+_operator.getRawAxis(3));
		}
		
		else if (clamped && _driver.getRawButton(5)) {
			_arms.suck();
		}
		
		else if (!clamped && _driver.getRawButton(5)) {
			_arms.spit();
		}
		
		else {
			_arms.disableMotors();
		}
		
		if (clamped) {
			_arms.setSolenoid(_arms.closed);
		}
		
		else if (!clamped && _driver.getRawButtonReleased(5)) {
			_arms.setSolenoid(_arms.opened);
		}
		
	}

	@Override
	public boolean isDone() {
		return false;
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		
		_arms.disableMotors();
		
	}

}
