/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4130.robot;

import com.ctre.phoenix.schedulers.ConcurrentScheduler;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	ConcurrentScheduler teleop;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		RobotMap.init();
		Subsystems.init();
		
		teleop = new ConcurrentScheduler();
		Loops.scheduleTeleop(teleop);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {

	}

	public void teleopInit(){
		teleop.startAll();
	}
	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		teleop.process();
		
		SmartDashboard.putNumber("Pos", RobotMap.elevatorMaster.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("TargetVelocity", RobotMap.elevatorMaster.getActiveTrajectoryPosition());
		SmartDashboard.putNumber("Velocity", RobotMap.elevatorMaster.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("TargetVelocity", RobotMap.elevatorMaster.getActiveTrajectoryVelocity());
		
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		
		
	}
	
	@Override
	public void disabledInit(){
		teleop.stopAll();
	}
	@Override
	public void disabledPeriodic(){
		
	}
}
