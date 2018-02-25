package org.usfirst.frc.team4130.robot;

import java.util.concurrent.ConcurrentMap;

import org.usfirst.frc.team4130.loops.*;
import org.usfirst.frc.team4130.subsystem.ElevatorPosition;

import com.ctre.phoenix.schedulers.*;

public class Loops {
	//Autonomous Loops
	//TODO: Figure out how to dynamically determine the auton loops
	
	//Teleop Loops
	public static void scheduleTeleop(ConcurrentScheduler teleop){
		
		System.out.println("Scheduling Teleop");
		
		//Schedule all tasks for teleop
		//teleop.add(new ElevatorTele(Subsystems.elevator, RobotMap.operatorJoystick));
		teleop.add(new DriveTele(Subsystems.driveTrain, RobotMap.driverJoystick));
		//teleop.add(new ArmsTele(Subsystems.arms, RobotMap.driverJoystick, RobotMap.operatorJoystick));
		
		System.out.println("Scheduled Teleop.");
		
	}
	
	//Auton Loops
	public static void scheduleAuton1(SequentialScheduler auton) {
	
		System.out.println("Scheduling auton 1.");
		
//		auton.add(new DriveDistance(Subsystems.driveTrain, 4096*10));
	
		auton.add(new DriveRotateNew(Subsystems.driveTrain, 180));
		auton.add(new Delay(500));
		auton.add(new DriveRotateNew(Subsystems.driveTrain, -180));
	
//		ConcurrentScheduler elevatorInit = new ConcurrentScheduler();
//		
//		elevatorInit.add(new ElevatorRelease(Subsystems.elevator));
//		elevatorInit.add(new Elevate(Subsystems.elevator, ElevatorPosition.ScaleMax.value));
//		
//		SequentialScheduler driveToScale = new SequentialScheduler(0);
//		
//		driveToScale.add(new DriveDistance(Subsystems.driveTrain, 4096*10));
//		driveToScale.add(new DriveRotate(Subsystems.driveTrain, 45));
//		driveToScale.add(new DriveDistance(Subsystems.driveTrain, 4096*10));
//		
//		ConcurrentScheduler ScaleGroup = new ConcurrentScheduler();
//		
//		ScaleGroup.add(elevatorInit);
//		ScaleGroup.add(driveToScale);
//		
//		SequentialScheduler driveToCube = new SequentialScheduler(0);
//		
//		ConcurrentScheduler driveToCube1 = new ConcurrentScheduler();
//		
//		driveToCube1.add(new DriveDistance(Subsystems.driveTrain, 4096*10));
//		driveToCube1.add(new Elevate(Subsystems.elevator, ElevatorPosition.Home.value));
//		
//		driveToCube.add(new DriveRotate(Subsystems.driveTrain, 90));
//		driveToCube.add(driveToCube);
//		
//		//Final Scheduling
//		auton.add(ScaleGroup);
//		//Eject Cube and Back up
//		auton.add(new Outtake(Subsystems.arms));
//		auton.add(new DriveDistance(Subsystems.driveTrain, 4096*-10));
//		auton.add(driveToCube);
//		//Grab Cube
//		auton.add(new Intake(Subsystems.arms));
//		auton.add(new Elevate(Subsystems.elevator, ElevatorPosition.Switch.value));
//		//approach
//		auton.add(new DriveDistance(Subsystems.driveTrain, 4096*10));
//		auton.add(new Outtake(Subsystems.arms));
//		auton.add(new DriveDistance(Subsystems.driveTrain, 4096*-10));
//		driveToCube.add(new Elevate(Subsystems.elevator, ElevatorPosition.Home.value));
		
		System.out.println("Scheduled auton.");
		
	}
	
}
