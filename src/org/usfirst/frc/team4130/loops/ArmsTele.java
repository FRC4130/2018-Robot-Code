package org.usfirst.frc.team4130.loops;

import org.usfirst.frc.team4130.robot.RobotMap;
import org.usfirst.frc.team4130.subsystem.Arms;

import com.ctre.phoenix.ILoopable;

import edu.wpi.first.wpilibj.Joystick;

public class ArmsTele implements ILoopable {
	private Arms _arms;
	private Joystick _gamepad;
	
	private boolean clamped = true;
	
	public ArmsTele (Arms arms, Joystick gamepad) {
		_arms = arms;
		_gamepad = gamepad;
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		
		clamped = _arms.getClamped();
		
	}

	@Override
	public void onLoop() {
		// TODO Auto-generated method stub
		
		if (_gamepad.getRawButtonPressed(5)) {
			clamped =!clamped;
		}
		
		if (clamped) {
			_arms.intake();
		}
		else if (!clamped && _gamepad.getRawButton(5)) {
			_arms.spit();
		}
		else if (!clamped && _gamepad.getRawButtonReleased(5)) {
			_arms.setSolenoid(_arms.opened);
			_arms.disableMotors();
		}
		
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		
		_arms.disableMotors();
		
	}

}
