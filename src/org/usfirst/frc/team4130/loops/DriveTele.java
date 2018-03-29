package org.usfirst.frc.team4130.loops;

import com.ctre.phoenix.ILoopable;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team4130.robot.RobotMap;
import org.usfirst.frc.team4130.robot.Subsystems;
import org.usfirst.frc.team4130.subsystem.DriveTrain;

public class DriveTele implements ILoopable {
	
	DriveTrain _drive;
	Joystick _gamepad;
	
	double targetNativeLeft = 0;
	double targetNativeRight = 0;
	
	public DriveTele () {
		
		_drive = Subsystems.driveTrain;
		_gamepad = RobotMap.driverJoystick;
		
	}
	
	@Override
	public void onStart() {
		
		System.out.println("[Info] Starting Driving Teleop Controls");

		_drive.setShifter(_drive.highGear);
		_drive.setNeutralMode(NeutralMode.Coast);
		
	}

	@Override
	public void onLoop() {
		
		//Tank Drive (leftY, rightY) with brake button (B)
		if (_gamepad.getRawButtonPressed(2)) {
			_drive.setNeutralMode(NeutralMode.Brake);
			_drive.setShifter(_drive.lowGear);
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
			_drive.setShifter(_drive.lowGear);
		}
		if (_gamepad.getRawButtonPressed(5)) {
			_drive.setShifter(_drive.highGear);
		}
		
		//_drive.driveDirect(_gamepad.getRawAxis(1)*01, _gamepad.getRawAxis(5));
	}

	@Override
	public boolean isDone() {
		return false;
	}

	@Override
	public void onStop() {
		
		System.out.println("[Warning] Driving Teleoporated Control was Stopped");
		
		_drive.setNeutralMode(NeutralMode.Brake);
		_drive.driveDirect(0, 0);
	}

}
