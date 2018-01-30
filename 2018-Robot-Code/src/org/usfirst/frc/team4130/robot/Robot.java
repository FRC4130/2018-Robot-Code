/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

// DIABLO VII 2018 1/29/2018 9:21 AM - West
// List of enhancements, bug fixes, and new features
//
//  - Minor bug fixes
//
//                   _ooOoo_ 
//                  o8888888o 
//                  88" . "88 
//                  (| -_- |) 
//                  O\  =  /O 
//               ____/`---'\____ 
//             .'  \\|     |//  `. 
//            /  \\|||  :  |||//  \ 
//           /  _||||| -:- |||||-  \ 
//           |   | \\\  -  /// |   | 
//           | \_|  ''\---/''  |   | 
//           \  .-\__  `-`  ___/-. /
//         ___`. .'  /--.--\  `. . __ 
//      ."" '<  `.___\_<|>_/___.'  >'"". 
//     | | :  `- \`.;`\ _ /`;.`/ - ` : | | 
//     \  \ `-.   \_ __\ /__ _/   .-` /  / 
//======`-.____`-.___\_____/___.-`____.-'====== 
//                   `=---=' 
//          佛祖保佑                           永无BUG 
//         God Bless         Never Crash 
//

package org.usfirst.frc.team4130.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.time.StopWatch;

public class Robot extends IterativeRobot {
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();
	
	private final int leftDrive1ID = 1;
	private final int leftDrive2ID = 2;
	private final int leftDrive3ID = 3;
	
	final int rightDrive1ID = 4;
	final int rightDrive2ID = 5;
	final int rightDrive3ID = 6;
	
	final int driveSolenoidID[] = {0, 1};
	final double driveSolenoidSleepDelay = 500;
	
	DoubleSolenoid.Value driveGear = DoubleSolenoid.Value.kForward;
	
	double driveSolenoidSleepTime = 500;
	
	String teleopDriveMode = "None";
	
	int autoState = 0;
	
	TalonSRX leftDrive1 = new TalonSRX(leftDrive1ID);
	TalonSRX leftDrive2 = new TalonSRX(leftDrive2ID);
	TalonSRX leftDrive3 = new TalonSRX(leftDrive3ID);
	
	TalonSRX rightDrive1 = new TalonSRX(rightDrive1ID);
	TalonSRX rightDrive2 = new TalonSRX(rightDrive2ID);
	TalonSRX rightDrive3 = new TalonSRX(rightDrive3ID);
	
	DoubleSolenoid driveSolenoid = new DoubleSolenoid(driveSolenoidID[0],driveSolenoidID[1]);
	
	Joystick primaryJoy = new Joystick(0);
	Joystick secondaryJoy = new Joystick(1);
	
	@Override
	public void robotInit() {
			
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
		
		leftDrive1.set(ControlMode.PercentOutput, 0);
		leftDrive2.follow(leftDrive1);
		leftDrive3.follow(leftDrive1);
		
		leftDrive1.setInverted(true);
		leftDrive2.setInverted(true);
		leftDrive3.setInverted(true);
		
		rightDrive1.set(ControlMode.PercentOutput, 0);
		rightDrive2.follow(rightDrive1);
		rightDrive3.follow(rightDrive1);
		
		driveSolenoid.set(driveGear);
		driveSolenoidSleepTime = System.currentTimeMillis() + driveSolenoidSleepDelay;
		
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
		
		commonLoop();
		
		switch (autoState) {
		
		case 0:
			autoState = 1;
			
		case 1:
			autoState = 0;
			
		}
		
	}
	
	@Override
	public void teleopPeriodic() {
		
		commonLoop();
		
		if (primaryJoy.getRawButton(2)) {
			
			if (teleopDriveMode != "StraightManual") { 
				
				teleopDriveMode = "StraightManual";
				System.out.println("teleopDriveMode is now set to StraightManual");
				
			}
			
			leftDrive1.set(ControlMode.PercentOutput, primaryJoy.getRawAxis(1));
			rightDrive1.set(ControlMode.PercentOutput, primaryJoy.getRawAxis(1));
			
		}
		
		else if (Math.abs(primaryJoy.getRawAxis(1)-primaryJoy.getRawAxis(5)) <= .1) {
			
			if (teleopDriveMode != "StraightAuto") {
				
				teleopDriveMode = "StraightAuto";
				System.out.println("teleopDrivemode is now set to StraightAuto");
				
			}
			
			leftDrive1.set(ControlMode.PercentOutput, (primaryJoy.getRawAxis(1)+primaryJoy.getRawAxis(5))/2);
			rightDrive1.set(ControlMode.PercentOutput, (primaryJoy.getRawAxis(1)+primaryJoy.getRawAxis(5))/2);
			
		}
		
		else {
			
			if (teleopDriveMode != "TankDefault") {
				
				teleopDriveMode = "TankDefault";
				System.out.println("teleopDriveMode is now set to TankDefault");
				
			}
			
			leftDrive1.set(ControlMode.PercentOutput, primaryJoy.getRawAxis(5));
			rightDrive1.set(ControlMode.PercentOutput, primaryJoy.getRawAxis(1));
			
		}
		
		if (primaryJoy.getRawButtonPressed(6)) {
			
			driveGear = driveGear == DoubleSolenoid.Value.kForward ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward;
			driveSolenoid.set(driveGear);
			System.out.println(driveGear == DoubleSolenoid.Value.kForward ? "driveSolenoid is now set to kForward" : "driveSolenoid is now set to kReverse");
			driveSolenoidSleepTime = System.currentTimeMillis() + driveSolenoidSleepDelay;
			
		}
		
	}
	
	@Override
	public void testInit() {
		
		
		
	}
	
	@Override
	public void testPeriodic() {
		
		commonLoop();
		
	}
	
	
	@Override
	public void disabledInit() {
		
		
		
	}
	
	@Override
	public void disabledPeriodic() {
		
		
		
	}
	
	public void commonLoop() {
		
		if (driveSolenoid.get() != DoubleSolenoid.Value.kOff) {
			
			if (System.currentTimeMillis() >= driveSolenoidSleepTime) {
				
				driveSolenoid.set(DoubleSolenoid.Value.kOff);
				System.out.println("driveSolenoid is now set to kOff");
				
			}
			
		}
		
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
