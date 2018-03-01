package org.usfirst.frc.team4130.subsystem;

import org.usfirst.frc.team4130.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Arms {
	
	VictorSPX left = RobotMap.leftIntake;
	VictorSPX right = RobotMap.rightIntake;
	
	DigitalInput leftLimit = RobotMap.leftCube;
	DigitalInput rightLimit = RobotMap.rightCube;
	
	DoubleSolenoid clamp = RobotMap.armsClamp;
	
	public final Value closed = Value.kForward;
	public final Value opened = Value.kReverse;
	
	public final int intakeSpeed = 1;
	public final int outtakeSpeed = -1;
	
	public Arms () {
		
		left.setInverted(false);
		right.setInverted(true);
		
		setNeutralMode(NeutralMode.Brake);
		setSolenoid(closed);
		
	}
	
	public void setNeutralMode(NeutralMode nm) {
		
		left.setNeutralMode(nm);
		right.setNeutralMode(nm);
		
	}
	
	public void setSolenoid(Value vl) {
		
		clamp.set(vl);
		
	}
	
	public void driveDirect(double leftThrottle, double rightThrottle) {
		
		left.set(ControlMode.PercentOutput, leftThrottle);
		right.set(ControlMode.PercentOutput, rightThrottle);
		
	}
	
	public boolean intakeHold() {
		
		setSolenoid(closed);
		
		if (leftLimit.get()) {
			left.set(ControlMode.PercentOutput, intakeSpeed);
		}
		else {
			left.set(ControlMode.PercentOutput, 0);
		}
		
		if (rightLimit.get()) {
			right.set(ControlMode.PercentOutput, intakeSpeed);
		}
		else {
			right.set(ControlMode.PercentOutput, 0);
		}
		
		return leftLimit.get() && rightLimit.get();
		
	}
	
	public boolean suck() {
		
		setSolenoid(closed);
		
		left.set(ControlMode.PercentOutput, intakeSpeed);
		right.set(ControlMode.PercentOutput, intakeSpeed);
		
		return leftLimit.get() || rightLimit.get();
		
	}
	
	public boolean spit() {
		
		setSolenoid(closed);
		
		left.set(ControlMode.PercentOutput, outtakeSpeed);
		right.set(ControlMode.PercentOutput, outtakeSpeed);
		
		return leftLimit.get() || rightLimit.get();
		
	}
	
	public void disableMotors() {
		
		driveDirect(0,0);
		
	}
	
	public boolean getClamped() {
		
		return clamp.get() == closed;
		
	}
	
}
