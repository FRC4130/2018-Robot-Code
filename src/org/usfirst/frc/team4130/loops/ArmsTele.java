package org.usfirst.frc.team4130.loops;

import org.usfirst.frc.team4130.subsystem.Arms;

import com.ctre.phoenix.ILoopable;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class ArmsTele implements ILoopable {
	private Arms _arms;
	private Joystick _operator;
	
	private boolean clamped = true;
	
	public ArmsTele (Arms arms, Joystick operator) {
		_arms = arms;
		_operator = operator;
	}
	
	@Override
	public void onStart() {
		
		System.out.println("[Info] Arms Teleoporated Control started");
		
		clamped = _arms.getClamped();
		
	}

	@Override
	public void onLoop() {
		
		//clamp toggling logic
		if (_operator.getRawButton(5)) {
			_arms.setSolenoid(Value.kReverse);
			
		}
		
		else if (_operator.getRawButton(6)) {
			_arms.setSolenoid(Value.kForward);
		}
		
		//Motor control
		//Manual control
		if (Math.abs(_operator.getRawAxis(2)*-1+_operator.getRawAxis(3)) > 0.1) {
			_arms.driveDirect(_operator.getRawAxis(2)*-1+_operator.getRawAxis(3),
							  _operator.getRawAxis(2)*-1+_operator.getRawAxis(3));
		}
		
		else {
			_arms.disableMotors();
		}
		
	}

	@Override
	public boolean isDone() {
		return false;
	}

	@Override
	public void onStop() {
		
		_arms.disableMotors();
		
		System.out.println("[WARNING] Arms Teleoprated has been stopped");
		
	}

}
