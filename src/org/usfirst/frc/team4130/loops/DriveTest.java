package org.usfirst.frc.team4130.loops;

import com.ctre.phoenix.ILoopable;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

import org.usfirst.frc.team4130.robot.RobotMap;
import org.usfirst.frc.team4130.robot.Subsystems;
import org.usfirst.frc.team4130.subsystem.DriveTrain;
import org.usfirst.frc.team4130.subsystem.ElevatorPosition;

public class DriveTest implements ILoopable {
	
	DriveTrain _drive;
	Joystick _gamepad;
	
	double targetNativeLeft = 0;
	double targetNativeRight = 0;
	
	boolean rampRateLimited = false;
	
	Value gearBeforeBrake = Subsystems.driveTrain.getShifter();
	
	DriveDistance dd = new DriveDistance(100);
	
	double startTimeMs = 0;
	
	boolean driveDone = true;
	
	public DriveTest () {
		
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
		
		//Drive Calibration code
		if (_gamepad.getRawButtonPressed(8)) {
			_drive.resetSensors();
		}
		else if (_gamepad.getRawButton(8)) {
			_drive.setPosLeft(2048*75);
			_drive.setPosRight(2048*75);
		}
		
		//Drive Distance test code
		else if (_gamepad.getRawButtonPressed(3)) {
			dd = new DriveDistance(200, _drive.getShifter(), 0, 0);
			dd.onStart();
			startTimeMs = System.currentTimeMillis();
			driveDone = false;
		}
		else if (_gamepad.getRawButtonPressed(4)) {
			dd = new DriveDistance(200, _drive.getShifter(), 0, 0);
			dd.onStart();
			startTimeMs = System.currentTimeMillis();
			driveDone = false;
		}
		else if (_gamepad.getRawButton(3) || _gamepad.getRawButton(4)) {
			_drive.putDash();
			if (!dd.isDone() && !driveDone) {
				dd.onLoop();
				driveDone = true;
				System.out.println(System.currentTimeMillis()-startTimeMs);
			}
		}
		else if (_gamepad.getRawButtonReleased(3) || _gamepad.getRawButtonReleased(4)) {
			_drive.putDash();
			if (!dd.isDone() && !driveDone) {
				dd.onStop();
				driveDone = true;
			}
		}
		
		else {
			
			//Manage ramp rate
			if (Subsystems.elevator.getHeight() > ElevatorPosition.MaxStable.value && !rampRateLimited) {
				System.out.println("[Info] Ramp rate is limited");
				_drive.setRampRate(1,1);
				rampRateLimited = true;
			}
			else if (Subsystems.elevator.getHeight() < ElevatorPosition.MaxStable.value && rampRateLimited) {
				System.out.println("[Info] Ramp rate limit removed");
				_drive.setRampRate(0,0);
				rampRateLimited = false;
			}
			
			//Driver speed input
			if (_gamepad.getRawButton(1)) {
				_drive.driveDirect(_gamepad.getRawAxis(1)*-1,_gamepad.getRawAxis(1)*-1);
			}
			else {
				_drive.driveDirect(_gamepad.getRawAxis(1)*-1, _gamepad.getRawAxis(5)*-1);
			}
			
			//Shifting input
			if (_gamepad.getRawButtonPressed(6)) {
				_drive.setShifter(_drive.lowGear);
				gearBeforeBrake = _drive.lowGear;
			}
			else if (_gamepad.getRawButtonPressed(5)) {
				_drive.setShifter(_drive.highGear);
				gearBeforeBrake = _drive.highGear;
			}
			else if (Math.round(DriverStation.getInstance().getMatchTime()) == 1) {
				_drive.setShifter(_drive.highGear);
			}
		
		}
		
		//Brake input
		if (_gamepad.getRawButtonPressed(2)) {
			_drive.setNeutralMode(NeutralMode.Brake);
			_drive.setShifter(_drive.lowGear);
		}
		else if (_gamepad.getRawButton(2)){
			_drive.driveDirect(0, 0);
		}
		else if (_gamepad.getRawButtonReleased(2)) {
			_drive.setNeutralMode(NeutralMode.Coast);
			_drive.setShifter(gearBeforeBrake);
		}
		
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
		_drive.setRampRate(0, 0);
		System.out.println("[Info] Ramp rate limit removed");
		
		rampRateLimited = false;
		
	}

}
