package org.usfirst.frc.team4130.subsystem;

import org.usfirst.frc.team4130.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Intake {
	
	private VictorSPX leftIntake = RobotMap.leftIntake;
	private VictorSPX rightIntake = RobotMap.rightIntake;
	private DoubleSolenoid intakeClamp = RobotMap.intakeClamp;
	
	public void init() {
		leftIntake.set(ControlMode.PercentOutput, 0);
		rightIntake.set(ControlMode.PercentOutput, 0);
	}
	public void clampClose() {
		intakeClamp.set();
	}
	public void clampOpen() {
		intakeClamp.set();
	}
	public void intakeIn() {
		
	}
	public void intakeOut() {
		
	}
}
