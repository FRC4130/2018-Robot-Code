package org.usfirst.frc.team4130.subsystem;

import org.usfirst.frc.team4130.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Drivetrain {
	
	private TalonSRX leftDrive = RobotMap.leftDriveMaster;
	private TalonSRX rightDrive = RobotMap.rightDriveMaster;
	
	public void init(){
		
		
		
	}
	
	public void tank() {
		
		leftDrive.set(ControlMode., arg1);
		
	}
	
}
