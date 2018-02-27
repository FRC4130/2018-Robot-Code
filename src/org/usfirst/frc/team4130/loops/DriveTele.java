package org.usfirst.frc.team4130.loops;

import com.ctre.phoenix.ILoopable;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import org.usfirst.frc.team4130.robot.Subsystems;
import org.usfirst.frc.team4130.subsystem.DriveTrain;

/**
 * Loopable class for driving in Teleoporated.
 * @author West
 */
public class DriveTele implements ILoopable {
	
	DriveTrain _drive;
	Joystick _gamepad;
	
	public DriveTele (DriveTrain driveTrain, Joystick gamepad) {
		
		_drive = driveTrain;
		_gamepad = gamepad;
		
	}

	@Override
	public void onStart() {
		
		//initialize the driveTrain
		_drive.setShifter(Value.kForward);
		_drive.setNeutralMode(NeutralMode.Coast);
		
	}

	@Override
	public void onLoop() {
		// TODO Auto-generated method stub
		
		//Tank Drive (leftY, rightY) with brake button (B)
		if (_gamepad.getRawButtonPressed(2)) {
			_drive.setNeutralMode(NeutralMode.Brake);
		}
		else if (_gamepad.getRawButtonReleased(2)) {
			_drive.setNeutralMode(NeutralMode.Coast);
		}
		else if (_gamepad.getRawButton(2)){
			_drive.driveDirect(0, 0);
		}
		else if (_gamepad.getRawButton(1)) {
			_drive.driveDirect(_gamepad.getRawAxis(1)*-1,_gamepad.getRawAxis(1)*-1);
		}
		else {
			_drive.driveDirect(_gamepad.getRawAxis(1)*-1, _gamepad.getRawAxis(5)*-1);
		}
		
		//Shift with right button
		if (_gamepad.getRawButtonPressed(6)) {
			_drive.toggleShifter();
		}
		
		//_drive.driveDirect(_gamepad.getRawAxis(1)*01, _gamepad.getRawAxis(5));
	}

	@Override
	public boolean isDone() {
		return false;
	}

	@Override
	public void onStop() {
		//Kill throttle and set NeutralMode to brake
		_drive.setNeutralMode(NeutralMode.Brake);
		_drive.driveDirect(0, 0);
	}

}
