package org.usfirst.frc.team4130.loops;

import org.usfirst.frc.team4130.robot.RobotMap;
import org.usfirst.frc.team4130.robot.Subsystems;
import org.usfirst.frc.team4130.subsystem.Arms;

import com.ctre.phoenix.ILoopable;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class ArmsTele implements ILoopable {
	private Arms _arms;
	private Joystick _operator;
	
	@Deprecated
	public ArmsTele (Arms arms, Joystick operator) {
		_arms = arms;
		_operator = operator;
	}
	
	public ArmsTele() {
		_arms = Subsystems.arms;
		_operator = RobotMap.operatorJoystick;
	}
	
	@Override
	public void onStart() {
		
		System.out.println("[Info] Arms Teleoporated Control started");
		
	}

	@Override
	public void onLoop() {
		
		//clamp toggling logic
		if (_operator.getRawButtonPressed(5)) {
			_arms.setSolenoid(_arms.getSolenoid() == _arms.opened ? _arms.closed : _arms.opened);
		}
		
		if (_operator.getRawButton(6)) {
			_arms.setSolenoid(_arms.getSolenoid() == _arms.opened ? _arms.closed : _arms.opened);
		}
		
		//Motor control
		//Manual control
		if (Math.abs(_operator.getRawAxis(5))>0.075) {
			_arms.driveDirect(_operator.getRawAxis(5),_operator.getRawAxis(5));
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
