/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4130.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();
	
	int leftDrive1ID = 1;
	int leftDrive2ID = 2;
	int leftDrive3ID = 3;
	
	int rightDrive1ID = 4;
	int rightDrive2ID = 5;
	int rightDrive3ID = 6;
	
	int driveSolenoidID = 0;
	
	TalonSRX leftDrive1 = new TalonSRX(leftDrive1ID);
	TalonSRX leftDrive2 = new TalonSRX(leftDrive2ID);
	TalonSRX leftDrive3 = new TalonSRX(leftDrive3ID);
	
	TalonSRX rightDrive1 = new TalonSRX(rightDrive1ID);
	TalonSRX rightDrive2 = new TalonSRX(rightDrive2ID);
	TalonSRX rightDrive3 = new TalonSRX(rightDrive3ID);
	
	Solenoid driveSolenoid = new Solenoid(driveSolenoidID);
	Compressor mainComp = new Compressor();
	
	Joystick primaryJoy = new Joystick(0);
	Joystick secondaryJoy = new Joystick(1);
	
	int autoState = 0;
	
		@Override
	public void robotInit() {
			
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
		
		mainComp.setClosedLoopControl(true);
		
		leftDrive1.set(ControlMode.PercentOutput, 0);
		leftDrive2.follow(leftDrive1);
		leftDrive3.follow(leftDrive1);
		
		rightDrive1.set(ControlMode.PercentOutput, 0);
		rightDrive2.follow(rightDrive1);
		rightDrive3.follow(rightDrive1);
		
		driveSolenoid.set(false);
		
	}
	
	@Override
	public void autonomousInit() {
		
		m_autoSelected = m_chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);
		
		switch (m_autoSelected) {
		
		case kCustomAuto:
			autoState = 0;
			break;
		case kDefaultAuto:
		default:
			autoState = 0;
			break;
			
	}
		
	}
	
	@Override
	public void autonomousPeriodic() {
		
		//commonLoop();
		
		switch (autoState) {
		
		case 0:
			autoState = 1;
		case 1:
			autoState = 0;
		}
		
	}
	
	@Override
	public void teleopPeriodic() {
		
		//commonLoop();
		
		leftDrive1.set(ControlMode.PercentOutput, primaryJoy.getRawAxis(1));
		rightDrive1.set(ControlMode.PercentOutput, primaryJoy.getRawAxis(5));
		
		if (primaryJoy.getRawButtonReleased(6)) {
			
			driveSolenoid.set(!driveSolenoid.get());
			System.out.println(driveSolenoid.get());
			
		}
		
	}
	
	@Override
	public void testPeriodic() {
		
		//commonLoop();
		
	}
	
	public void commonLoop() {
		
		
		
	}
	
	public void setDriveNeutral(NeutralMode driveMode) {
		
		leftDrive1.setNeutralMode(driveMode);
		leftDrive2.setNeutralMode(driveMode);
		leftDrive2.setNeutralMode(driveMode);
		
		rightDrive1.setNeutralMode(driveMode);
		rightDrive2.setNeutralMode(driveMode);
		rightDrive2.setNeutralMode(driveMode);
		
	}
}
