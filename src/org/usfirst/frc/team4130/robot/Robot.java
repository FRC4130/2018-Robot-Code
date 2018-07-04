/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4130.robot;

import edu.wpi.first.wpilibj.CameraServer;
import java.util.ArrayList;

import com.ctre.phoenix.schedulers.ConcurrentScheduler;
import com.ctre.phoenix.schedulers.SequentialScheduler;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	String[] pos = {"Test","Left","Center","Right"};
	
	ConcurrentScheduler teleop;
	
	SequentialScheduler autonRR;
	SequentialScheduler autonRL;
	SequentialScheduler autonLR;
	SequentialScheduler autonLL;
	
	SequentialScheduler autonTest;
	
	int target1i = 0;
	int target2i = 0;
	int target3i = 0;
	int target4i = 0;
	
	ArrayList<String> target1 = new ArrayList<String>();
	ArrayList<String> target2 = new ArrayList<String>();
	ArrayList<String> target3 = new ArrayList<String>();
	ArrayList<String> target4 = new ArrayList<String>();
	
	String gameData;
	
	int posi = 0;

	@Override
	public void robotInit() {
		
		target1.add("Targets not set (targetLL)");
		target2.add("Targets not set (targetLR)");
		target3.add("Targets not set (targetRL)");
		target4.add("Targets not set (targetRR)");
		
		RobotMap.init();
		Subsystems.init();
		
		CameraServer.getInstance().startAutomaticCapture();
		
	}

	@Override
	public void autonomousInit() {
		
		autonTest = new SequentialScheduler(0);
		
		autonRR = new SequentialScheduler(0);
		autonRL = new SequentialScheduler(0);
		autonLR = new SequentialScheduler(0);
		autonLL = new SequentialScheduler(0);
		
		switch (posi) {
		
		case 0:		Loops.sTest(autonTest);
			
		case 1:		Loops.s1RR(autonRR, target1.get(target1i), false);
					Loops.s1RL(autonRL, target2.get(target2i), false);
					Loops.s1LR(autonLR, target3.get(target3i), false);
					Loops.s1LL(autonLL, target4.get(target4i), false);
					break;
		
		case 2:		Loops.s2RR(autonRR, target1.get(target1i), false);
					Loops.s2RL(autonRL, target2.get(target2i), false);
					Loops.s2LR(autonLR, target3.get(target3i), false);
					Loops.s2LL(autonLL, target4.get(target4i), false);
					break;
				
		case 3:		Loops.s3RR(autonRR, target1.get(target1i), false);
					Loops.s3RL(autonRL, target2.get(target2i), false);
					Loops.s3LR(autonLR, target3.get(target3i), false);
					Loops.s3LL(autonLL, target4.get(target4i), false);
					break;
		
		default:	Loops.sEleRelease(autonRR);
					Loops.sEleRelease(autonRL);
					Loops.sEleRelease(autonLR);
					Loops.sEleRelease(autonLL);
					break;
		
		}
		
		autonTest.start();
		
		autonRR.start();
		autonRL.start();
		autonLR.start();
		autonLL.start();
		
	}
	
	@Override
	public void autonomousPeriodic() {
		
		gameData = DriverStation.getInstance().getGameSpecificMessage().toLowerCase();
		
		if (posi == 0) {
			autonTest.process();
		}
		else if (gameData.length() > 0) {
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
		Loops.sTeleop(teleop);
		teleop.startAll();
		
	}

	@Override
	public void teleopPeriodic() {
		teleop.process();
	}
	
	@Override
	public void disabledInit(){
		setPos(DriverStation.getInstance().getLocation());
	}
	
	@Override
	public void disabledPeriodic(){
		if (RobotMap.driverJoystick.getRawButtonPressed(4)) {
			target1i++;
			target1i = target1i >= target1.size() ? 0 : target1i;
		}
		if (RobotMap.driverJoystick.getRawButtonPressed(2)) {
			target2i++;
			target2i = target2i >= target2.size() ? 0 : target2i;
		}
		if (RobotMap.driverJoystick.getRawButtonPressed(3)) {
			target3i++;
			target3i = target3i >= target3.size() ? 0 : target3i;
		}
		if (RobotMap.driverJoystick.getRawButtonPressed(1)) {
			target4i++;
			target4i = target4i >= target4.size() ? 0 : target4i;
		}
		
		if (RobotMap.driverJoystick.getRawButtonPressed(8)) {
			setPos(posi+1);
		}
		
	}
	
	@Override
	public void robotPeriodic() {
		
		SmartDashboard.putString("Position", pos[posi]);
		SmartDashboard.putString("Shifter", Subsystems.driveTrain.getShifter() == Subsystems.driveTrain.highGear ? "High Gear" : "Low Gear");
		SmartDashboard.putString("Left Left", target1.get(target4i));
		SmartDashboard.putString("Left Right", target1.get(target3i));
		SmartDashboard.putString("Right Left", target1.get(target2i));
		SmartDashboard.putString("Right Right", target1.get(target1i));
		SmartDashboard.putBoolean("Arms Closed?", Subsystems.arms.getSolenoid() == Subsystems.arms.closed);
		SmartDashboard.putNumber("Current Height", Subsystems.elevator.getHeight());
		SmartDashboard.putNumber("Target Height", Subsystems.elevator.getTargetHeight());
		SmartDashboard.putNumber("Match Number", DriverStation.getInstance().getMatchNumber());
		SmartDashboard.putNumber("Match Time", DriverStation.getInstance().getMatchTime());
		
		Subsystems.driveTrain.putDash();
		
	}
	
	public void setPos(int p) {
		
		target1i = 0;
		target2i = 0;
		target3i = 0;
		target4i = 0;
		
		ArrayList<ArrayList<String>> targets = new ArrayList<ArrayList<String>>();
		
		targets.add(target1);
		targets.add(target2);
		targets.add(target3);
		targets.add(target4);
		
		posi = p;
		
		posi = posi >= pos.length ? 0 : posi;
		
		for (ArrayList<String> target : targets) {
			
			target.clear();
			target.add("Nothing");
			target.add("Cross The Line");
			target.add("Front Switch");
			target.add("Front Switch Late");
			target.add("Front Switch Double");
			target.add("FS Double High");
			
		}
		
		switch (posi) {
		
		case 0:		for (ArrayList<String> target : targets) {
						
						target.clear();
						target.add("Test");
						
					}
					break;
		
		case 1:		for (ArrayList<String> target : targets) {
						
						target.add("Outside Switch");
						target.add("Scale");
						
					}
					break;
		
		case 2:		for (ArrayList<String> target : targets) {
						
						target.add("ALT FS Double");
					
					}
					break;
				
		case 3: 	for (ArrayList<String> target : targets) {
						
						target.add("Outside Switch");
						target.add("Scale");
						
					}
					break;
		
		default:	for (ArrayList<String> target : targets) {
					
						target.clear();
						target.add("Pos out of bounds");
						
					}
					break;
		
		}
		
	}
}
