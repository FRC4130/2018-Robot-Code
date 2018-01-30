/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4130.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	TalonSRX leftMotor = new TalonSRX(7);
	TalonSRX rightMotor = new TalonSRX(8);
	
	Joystick primaryJoy = new Joystick(0);
	
	DoubleSolenoid arm1 = new DoubleSolenoid(0, 1);
	DoubleSolenoid arm2 = new DoubleSolenoid(2, 3);
	
	boolean intakeState = false;
	
	boolean buttonLast = false;
	boolean button = false;
	
	double intakeSpeed = 0.1;
	double outtakeSpeed = -0.1;

	@Override
	public void robotInit() {
		
		leftMotor.set(ControlMode.PercentOutput, 0);
		rightMotor.set(ControlMode.PercentOutput, 0);	
		
	}

	@Override
	public void teleopPeriodic() {
		
		if (primaryJoy.getRawButtonPressed(1)) {
			
			intakeSpeed-=.1;
			
			if (intakeSpeed < -1) {
				
				intakeSpeed = -1;
				
			}
			
		}
		
		else if (primaryJoy.getRawButtonPressed(4)) {
			
			intakeSpeed+=.1;
			
			if (intakeSpeed > 1) {
				
				intakeSpeed = 1;
				
			}
			
		}
		
		else if (primaryJoy.getRawButtonPressed(2)) {
			
			outtakeSpeed-=.1;
			
			if (outtakeSpeed < -1) {
				
				outtakeSpeed = -1;
				
			}
			
		}
		
		else if (primaryJoy.getRawButtonPressed(3)) {
			
			outtakeSpeed+=.1;
			
			if (outtakeSpeed > 1) {
				
				outtakeSpeed = 1;
				
			}
			
		}
		
		button = RobotController.getUserButton() || primaryJoy.getRawButton(5) || primaryJoy.getRawButton(6);
		
		if (button == true && buttonLast == false) {
			
			intakeState = !intakeState;
			
			if (intakeState) {
				
				arm1.set(DoubleSolenoid.Value.kForward);
				arm2.set(DoubleSolenoid.Value.kForward);
				
			}
			
		}
		
		else if (button == false && buttonLast == true && !intakeState) {
			
			arm1.set(DoubleSolenoid.Value.kReverse);
			arm2.set(DoubleSolenoid.Value.kReverse);
			
		}
		
		leftMotor.set(ControlMode.PercentOutput, button ? intakeState ? intakeSpeed : outtakeSpeed : 0);
		rightMotor.set(ControlMode.PercentOutput, button ? intakeState ? intakeSpeed : outtakeSpeed : 0);
		
		buttonLast = RobotController.getUserButton() || primaryJoy.getRawButton(5) || primaryJoy.getRawButton(6);
		
	}

	@Override
	public void testPeriodic() {
		
		if (primaryJoy.getRawButton(5)) {
			
			arm1.set(DoubleSolenoid.Value.kForward);
			
		}
		
		else {
			
			arm1.set(DoubleSolenoid.Value.kReverse);
			
		}
		
		if (primaryJoy.getRawButton(6)) {
			
			arm2.set(DoubleSolenoid.Value.kForward);
			
		}
		
		else {
			
			arm2.set(DoubleSolenoid.Value.kReverse);
			
		}

		leftMotor.set(ControlMode.PercentOutput, primaryJoy.getRawAxis(1));
		rightMotor.set(ControlMode.PercentOutput, primaryJoy.getRawAxis(5));
			
	}
}
