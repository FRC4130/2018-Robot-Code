package org.usfirst.frc.team4130.subsystem;

import org.usfirst.frc.team4130.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Drivetrain {
	
	private TalonSRX leftDrive = RobotMap.leftDriveMaster;
	private TalonSRX rightDrive = RobotMap.rightDriveMaster;
	private PigeonIMU pigeon = RobotMap.pigeon;
	//private DoubleSolenoid shifter = RobotMap.driveShift;
	
	public void init(){
		
		
		
	}
	
}
