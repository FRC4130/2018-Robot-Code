/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4130.robot;

import com.ctre.phoenix.schedulers.ConcurrentScheduler;
import com.ctre.phoenix.schedulers.SequentialScheduler;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	ConcurrentScheduler teleop;
	SequentialScheduler auton1;
	SequentialScheduler auton2;
	String gameData;

	@Override
	public void robotInit() {
		RobotMap.init();
		Subsystems.init();
		
		teleop = new ConcurrentScheduler();
		Loops.scheduleTeleop(teleop);
		
		auton1 = new SequentialScheduler(0);
		Loops.scheduleAuton1(auton1);
		
	}

	@Override
	public void autonomousInit() {
		auton1.start();
	}

	@Override
	public void autonomousPeriodic() {
		
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		auton1.process();
		
		//SmartDashboard.putNumber("TargetVelocity", RobotMap.leftDriveMaster.getActiveTrajectoryVelocity());
		//SmartDashboard.putNumber("Velocity", RobotMap.leftDriveMaster.getSelectedSensorVelocity(0));
		//SmartDashboard.putNumber("TargetPosition", RobotMap.leftDriveMaster.getActiveTrajectoryPosition());
		//SmartDashboard.putNumber("Position", RobotMap.leftDriveMaster.getSelectedSensorPosition(0));
		
		//SmartDashboard.putNumber("TargetVelocityR", RobotMap.rightDriveMaster.getActiveTrajectoryVelocity());
		//SmartDashboard.putNumber("VelocityR", RobotMap.rightDriveMaster.getSelectedSensorVelocity(0));
		//SmartDashboard.putNumber("TargetPositionR", RobotMap.rightDriveMaster.getActiveTrajectoryPosition());
		//SmartDashboard.putNumber("PositionR", RobotMap.rightDriveMaster.getSelectedSensorPosition(0));
		
	}

	public void teleopInit(){
		teleop.startAll();
		
	}

	@Override
	public void teleopPeriodic() {
		teleop.process();
		
	}

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
