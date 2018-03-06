package org.usfirst.frc.team4130.robot;

import java.util.concurrent.ConcurrentMap;

import org.usfirst.frc.team4130.loops.*;
import org.usfirst.frc.team4130.subsystem.ElevatorPosition;

import com.ctre.phoenix.schedulers.*;

public class Loops {
	
	private static double m = 1;
	
	//Teleop Loops
	public static void scheduleTeleop(ConcurrentScheduler teleop){
		
		System.out.println("Scheduling Teleop.");
		
		//Schedule all tasks for teleop
		//teleop.add(new ElevatorTele(Subsystems.elevator, RobotMap.operatorJoystick));
		teleop.add(new DriveTele(Subsystems.driveTrain, RobotMap.driverJoystick));
		//teleop.add(new ArmsTele(Subsystems.arms, RobotMap.driverJoystick, RobotMap.operatorJoystick));
		
		System.out.println("Scheduled.");
		
		
	}
	
	//Testing Loops
	public static void hokeyPokey(SequentialScheduler auton) {
		
		System.out.println("Preparing to get down...");
		
		auton.add(new DriveDistance(Subsystems.driveTrain, 2*12, Subsystems.driveTrain.highGear));
		auton.add(new DriveDistance(Subsystems.driveTrain, -2*12, Subsystems.driveTrain.highGear));
		auton.add(new DriveRotate(Subsystems.driveTrain, 90));
		auton.add(auton);
		
		System.out.println("Prepared.");
	}

	public static void scheduleTest(SequentialScheduler test) {
		
		System.out.println("Scheduling Test");
		
		//hokeyPokey(test);
		test.add(new DriveDistance(Subsystems.driveTrain, m*250, Subsystems.driveTrain.highGear));
		
		System.out.println("Scheduled Test");
		
	}
	
	//Auton Loops
	public static void scheduleEleRelease(SequentialScheduler auton) {
		
		System.out.println("Scheduling Elevator Init.");
		
//		ConcurrentScheduler elevatorInit = new ConcurrentScheduler();
//		
//		elevatorInit.add(new ElevatorRelease(Subsystems.elevator));
//		elevatorInit.add(new Elevate(Subsystems.elevator, ElevatorPosition.home.value));
//		
//		auton.add(elevatorInit);
		
		System.out.println("Scheduled.");
		
	}
	
	public static void schedule1RR(SequentialScheduler auton) {
		
		System.out.println("Scheduling Right Right from Left.");
		
		scheduleEleRelease(auton);
		
		auton.add(new DriveDistance(Subsystems.driveTrain, m*283.621, Subsystems.driveTrain.highGear));
		auton.add(new DriveRotate(Subsystems.driveTrain, 75));
		auton.add(new DriveDistance(Subsystems.driveTrain, m*23.604, Subsystems.driveTrain.highGear));
		auton.add(new DriveDistance(Subsystems.driveTrain, m*-23.604, Subsystems.driveTrain.highGear));
		auton.add(new DriveRotate(Subsystems.driveTrain, 80));
		auton.add(new DriveDistance(Subsystems.driveTrain, m*91.502, Subsystems.driveTrain.highGear));
		
		System.out.print("Scheduled.");
		
	}
	
	public static void schedule1RL(SequentialScheduler auton) {
		
		System.out.println("Scheduling Right Left from Left.");
		
		scheduleEleRelease(auton);
		
		System.out.print("Scheduled.");
		
		
	}
	
	public static void schedule1LL(SequentialScheduler auton) {
		
		System.out.println("Scheduling Left Left from Left.");
		
		scheduleEleRelease(auton);
		
		System.out.print("Scheduled.");
		
	}

	public static void schedule1LR(SequentialScheduler auton) {
		
		System.out.println("Scheduling Right Left from Left.");
		
		scheduleEleRelease(auton);
		
		System.out.print("Scheduled.");
		
	}

	public static void schedule2RR(SequentialScheduler auton) {
		
		System.out.println("Scheduling Right Right from Center.");
		
		scheduleEleRelease(auton);
		
		auton.add(new DriveDistance(Subsystems.driveTrain, m*47.9, Subsystems.driveTrain.highGear));
		auton.add(new DriveRotate(Subsystems.driveTrain, -40));
		auton.add(new DriveDistance(Subsystems.driveTrain, m*59, Subsystems.driveTrain.highGear));
		auton.add(new DriveRotate(Subsystems.driveTrain, 40));
		auton.add(new DriveDistance(Subsystems.driveTrain, m*7.5, Subsystems.driveTrain.highGear));
		
		System.out.print("Scheduled.");
		
	}

	public static void schedule2RL(SequentialScheduler auton) {
		
		System.out.println("Scheduling Right Left from Center.");
		
		schedule2RR(auton);
		
		System.out.print("Scheduled.");
		
	}

	public static void schedule2LR(SequentialScheduler auton) {
		
		System.out.println("Scheduling Left Right from Center.");
		
		schedule2LL(auton);
		
		System.out.print("Scheduled.");
		
	}

	public static void schedule2LL(SequentialScheduler auton) {
		
		System.out.println("Scheduling Left Left from Center.");
		
		scheduleEleRelease(auton);
		
		auton.add(new DriveDistance(Subsystems.driveTrain, m*29.3, Subsystems.driveTrain.highGear));
		auton.add(new DriveRotate(Subsystems.driveTrain, 40));
		auton.add(new DriveDistance(Subsystems.driveTrain, m*83.4, Subsystems.driveTrain.highGear));
		auton.add(new DriveRotate(Subsystems.driveTrain, -40));
		auton.add(new DriveDistance(Subsystems.driveTrain, m*7.5, Subsystems.driveTrain.highGear));
		
		System.out.print("Scheduled Left Left from Center.");
		
	}

	public static void schedule3RR(SequentialScheduler auton) {

		System.out.println("Scheduling Right Right from Right.");
		
		
		
		System.out.print("Scheduled.");
		
	}

	public static void schedule3RL(SequentialScheduler auton) {

		System.out.println("Scheduling Right Left from Right.");
		
		scheduleEleRelease(auton);
		
		System.out.print("Scheduled Right Right from Right.");
		
	}

	public static void schedule3LR(SequentialScheduler auton) {

		System.out.println("Scheduling Left Right from Right.");
		
		scheduleEleRelease(auton);
		
		System.out.print("Scheduled Left Right from Right.");
		
	}

	public static void schedule3LL(SequentialScheduler auton) {

		System.out.println("Scheduling Left Left from Right");
		
		scheduleEleRelease(auton);
		
		System.out.print("Scheduled Left Left from Right.");
		
	}
	
}
