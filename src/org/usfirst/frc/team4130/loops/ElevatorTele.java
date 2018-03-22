package org.usfirst.frc.team4130.loops;
import org.usfirst.frc.team4130.robot.RobotMap;
import org.usfirst.frc.team4130.robot.Subsystems;
import org.usfirst.frc.team4130.subsystem.Elevator;
import org.usfirst.frc.team4130.subsystem.ElevatorPosition;

import com.ctre.phoenix.ILoopable;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ElevatorTele implements ILoopable{
	private Elevator _elevator;
	private Joystick _gamepad;
	private double lastUpdate = 0;
	private final double updateSpeedMilliseconds = 25;
	private final double updateMultiplier = 400;
	private double pos = 0;
	
	/**
	 * Controls the elevator motor in teleoperated.
	 */
	
	@Deprecated
	public ElevatorTele(Elevator elevator, Joystick gamepad){
		_elevator = elevator;
		_gamepad = gamepad;
	}
	
	public ElevatorTele() {
		_elevator = Subsystems.elevator;
		_gamepad = RobotMap.operatorJoystick;
	}
	
	@Override
	public void onStart() {
		
		System.out.println("[Info] Started Elevator Teleop Control");
		
		pos = _elevator.getPos();
		
		//_elevator.setHome();
	}

	@Override
	public void onLoop() {
		
		SmartDashboard.putNumber("Elevator Current", _elevator.getCurrent());
		
		if (_gamepad.getRawButton(3)) {
			
			pos = _elevator.getPos();
			
		}
		
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
	
	

	@Override
	public boolean isDone() {
		return false;
	}

	@Override
	public void onStop() {
		System.out.println("[WARNING] Stopped Elevator Teleop Control has been stopped");
	}

}

