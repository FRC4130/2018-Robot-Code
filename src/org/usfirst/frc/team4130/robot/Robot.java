/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4130.robot;

import org.usfirst.frc.team4130.loops.Delay;
import org.usfirst.frc.team4130.loops.DriveDistance;
import org.usfirst.frc.team4130.loops.DriveRotate;
import org.usfirst.frc.team4130.loops.Elevate;
import org.usfirst.frc.team4130.loops.ElevatorRelease;

import com.ctre.phoenix.schedulers.ConcurrentScheduler;
import com.ctre.phoenix.schedulers.SequentialScheduler;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	String[] positions = {"Do Nothing","Left","Center","Right"};
	
	ConcurrentScheduler teleop;
	
	SequentialScheduler autonRR;
	SequentialScheduler autonRL;
	SequentialScheduler autonLR;
	SequentialScheduler autonLL;
	
	SequentialScheduler test = new SequentialScheduler(0);
	
	String gameData;
	
	int pos = 0;

	@Override
	public void robotInit() {
		RobotMap.init();
		Subsystems.init();
		
	}

	@Override
	public void autonomousInit() {
		
		autonRR = new SequentialScheduler(0);
		autonRL = new SequentialScheduler(0);
		autonLR = new SequentialScheduler(0);
		autonLL = new SequentialScheduler(0);
		
		switch (pos) {
			
		case 1:		Loops.schedule1RR(autonRR);
					Loops.schedule1RL(autonRL);
					Loops.schedule1LR(autonLR);
					Loops.schedule1LL(autonLL);
					break;
		
		case 2:		Loops.schedule2RR(autonRR);
					Loops.schedule2RL(autonRL);
					Loops.schedule2LR(autonLR);
					Loops.schedule2LL(autonLL);
					break;
				
		case 3: 	Loops.schedule3RR(autonRR);
					Loops.schedule3RL(autonRL);
					Loops.schedule3LR(autonLR);
					Loops.schedule3LL(autonLL);
					break;
		
		default:	Loops.scheduleEleRelease(autonRR);
					Loops.scheduleEleRelease(autonRL);
					Loops.scheduleEleRelease(autonLR);
					Loops.scheduleEleRelease(autonLL);
					break;
		
		}
		
		autonRR.start();
		autonRL.start();
		autonLR.start();
		autonLL.start();
		
	}
	
	@Override
	public void autonomousPeriodic() {
		
		gameData = DriverStation.getInstance().getGameSpecificMessage().toLowerCase();
		
		if(gameData.length() > 0)
        {
			if (gameData.charAt(0) == 'r') {
				if (gameData.charAt(1) == 'r') {
					autonRR.process();
				}
				else {
					autonRL.process();
				}
			}
			else {
				if (gameData.charAt(1) == 'r') {
					autonLR.process();
				}
				else {
					autonLL.process();
				}
			}
        }
		else {
			System.out.println("Waiting for game data...");
		}
		
	}

	public void teleopInit(){
		
		teleop = new ConcurrentScheduler();
		Loops.scheduleTeleop(teleop);
		teleop.startAll();
		
	}

	@Override
	public void teleopPeriodic() {
		teleop.process();
		
	}
	
	@Override
	public void disabledInit(){
		pos = DriverStation.getInstance().getLocation();
	}
	
	@Override
	public void disabledPeriodic(){
		if (RobotMap.driverJoystick.getRawButtonPressed(1)) {
			pos = 2;
		}
		if (RobotMap.driverJoystick.getRawButton(3)) {
			pos = 1;
		}
		if (RobotMap.driverJoystick.getRawButton(2)) {
			pos = 3;
		}
		if (RobotMap.driverJoystick.getRawButton(4)) {
			pos = 0;
		}
		pos = pos > 3 ? 0 : pos;
	}
	
	@Override
	public void robotPeriodic() {
		
		SmartDashboard.putString("Position", positions[pos]);
		SmartDashboard.putString("Shifter", Subsystems.driveTrain.getShifter() == Subsystems.driveTrain.highGear ? "High Gear" : "Low Gear");
		
	}
}
