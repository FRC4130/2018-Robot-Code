package org.usfirst.frc.team4130.subsystem;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class ArmsEmulator extends Arms {
	
	public ArmsEmulator() {
		
	}
	
	public void setNeutralMode(NeutralMode nm) {
		
	}
	
	public void setSolenoid(Value vl) {
		
	}
	
	public void driveDirect(double leftThrottle, double rightThrottle) {
		
	}
	
//	public boolean intakeHold() {
//		
//		setSolenoid(closed);
//		
//		if (leftLimit.get()) {
//			left.set(ControlMode.PercentOutput, intakeSpeed);
//		}
//		else {
//			left.set(ControlMode.PercentOutput, 0);
//		}
//		
//		if (rightLimit.get()) {
//			right.set(ControlMode.PercentOutput, intakeSpeed);
//		}
//		else {
//			right.set(ControlMode.PercentOutput, 0);
//		}
//		
//		return leftLimit.get() && rightLimit.get();
//		
//	}
	
	public void suck() {
		
	}
	
	public void spit() {
		
	}
	
	public void disableMotors() {
		
	}
	
}
