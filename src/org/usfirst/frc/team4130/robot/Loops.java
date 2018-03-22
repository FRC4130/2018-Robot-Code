package org.usfirst.frc.team4130.robot;



import org.usfirst.frc.team4130.loops.*;
import org.usfirst.frc.team4130.subsystem.ElevatorPosition;

import com.ctre.phoenix.schedulers.*;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Loops {
	
	private static double m = 1;
	
	//Teleop Loops
	public static void scheduleTeleop(ConcurrentScheduler teleop){
		
		System.out.println("Scheduling Teleop.");
		
		//Schedule all tasks for teleop
		teleop.add(new ElevatorTele(Subsystems.elevator, RobotMap.operatorJoystick));
		teleop.add(new DriveTele(Subsystems.driveTrain, RobotMap.driverJoystick));
		teleop.add(new ArmsTele(Subsystems.arms, RobotMap.operatorJoystick));
		
		System.out.println("Scheduled.");
		
	}
	
	//Testing Loops
	public static void hokeyPokey(SequentialScheduler auton) {
		
		System.out.println("Preparing to get down...");
		
		auton.add(new DriveDistance(Subsystems.driveTrain, 2*12));
		auton.add(new DriveRotate(Subsystems.driveTrain, 45));
		
		SequentialScheduler h = new SequentialScheduler(0);
		
		h.add(new DriveDistance(Subsystems.driveTrain, 2*12));
		h.add(new DriveDistance(Subsystems.driveTrain, -2*12));
		h.add(new DriveRotate(Subsystems.driveTrain, 90));
		h.add(h);
		
		auton.add(h);
		
		System.out.println("Prepared.");
		
	}

	public static void scheduleTest(SequentialScheduler test) {
		
		System.out.println("Scheduling Test");
		
		//hokeyPokey(test);
		test.add(new DriveDistance(Subsystems.driveTrain, m*250));
		
		System.out.println("Scheduled Test");
		
	}
	
	//Auton Loops
	public static void scheduleEleRelease(SequentialScheduler auton) {
		
		System.out.println("Scheduling Elevator Init.");
		
		auton.add(new ElevatorRelease(Subsystems.elevator));
		auton.add(new Elevate(Subsystems.elevator, ElevatorPosition.Travel.value));
		
		System.out.println("Scheduled.");
		
	}
	
	//Done 1
	public static void schedule1RR(SequentialScheduler auton) {
		
		System.out.println("Scheduling Right Right from Left.");
		
		scheduleEleRelease(auton);
		
		auton.add(new DriveDistance(Subsystems.driveTrain, 12*11));
		
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*218.606));
//		auton.add(new DriveRotate(Subsystems.driveTrain, -90));
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*197.245));
//		auton.add(new DriveRotate(Subsystems.driveTrain, 90));
//		auton.add(new Elevate(Subsystems.elevator, ElevatorPosition.ScaleMax.value));
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*60.136));
//		auton.add(new Outtake(Subsystems.arms));
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*-60.136));
//		auton.add(new Elevate(Subsystems.elevator, ElevatorPosition.Home.value));
//		auton.add(new DriveRotate(Subsystems.driveTrain, -165));
//		auton.add(new DriveDistance(Subsystems.driveTrain, 13.692));
//		auton.add(new Intake(Subsystems.arms));
//		auton.add(new Elevate(Subsystems.elevator, ElevatorPosition.Switch.value));
//		auton.add(new Outtake(Subsystems.arms));
//		auton.add(new Elevate(Subsystems.elevator, ElevatorPosition.Home.value));
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*-13.692));
		
		System.out.print("Scheduled.");
		
	}
	
	public static void schedule1RL(SequentialScheduler auton) {
		
		System.out.println("Scheduling Right Left from Left.");
		
		scheduleEleRelease(auton);

		auton.add(new DriveDistance(Subsystems.driveTrain, m*283.621));
		auton.add(new DriveRotate(Subsystems.driveTrain, -75));
		auton.add(new DriveDistance(Subsystems.driveTrain, m*-17));
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*17));
//		auton.add(new Outtake(Subsystems.arms));
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*-17));
//		auton.add(new Elevate(Subsystems.elevator, ElevatorPosition.Home.value));
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*10));
//		auton.add(new DriveRotate(Subsystems.driveTrain, -105));		
		
		System.out.print("Scheduled.");
		
	}
	
	public static void schedule1LL(SequentialScheduler auton) {
		
		System.out.println("Scheduling Left Left from Left.");
		
		schedule1LR(auton);
		
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*283.621));
//		auton.add(new DriveRotate(Subsystems.driveTrain, -75));
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*-17));
//		auton.add(new Elevate(Subsystems.elevator, ElevatorPosition.ScaleMax.value));
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*17));
//		auton.add(new Outtake(Subsystems.arms));
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*-17));
//		auton.add(new Elevate(Subsystems.elevator, ElevatorPosition.Home.value));
//		auton.add(new DriveRotate(Subsystems.driveTrain, 80));
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*91.502));
//		auton.add(new Intake(Subsystems.arms));
//		auton.add(new Elevate(Subsystems.elevator, 30));
//		auton.add(new Outtake(Subsystems.arms));
//		auton.add(new Elevate(Subsystems.elevator, ElevatorPosition.Home.value));
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*-13.692));
		
		System.out.print("Scheduled.");
		
	}
	
	//Done
	public static void schedule1LR(SequentialScheduler auton) {
		
		System.out.println("Scheduling Right Left from Left.");
		
		scheduleEleRelease(auton);
		
		auton.add(new DriveDistance(Subsystems.driveTrain, m*152.338));
		auton.add(new DriveRotate(Subsystems.driveTrain, -90));
		auton.add(new Elevate(Subsystems.elevator, ElevatorPosition.Switch.value));
		auton.add(new DriveForTime(Subsystems.driveTrain, 2000, 0.5, 0));
		auton.add(new Outtake(Subsystems.arms));
		auton.add(new DriveDistance(Subsystems.driveTrain, m*-16.5));
		auton.add(new Elevate(Subsystems.elevator, ElevatorPosition.Home.value));
		auton.add(new DriveRotate(Subsystems.driveTrain, 90));
		
		System.out.print("Scheduled.");
		
	}
	
	//Done 3
	public static void schedule2RR(SequentialScheduler auton) {
		
		System.out.println("Scheduling Right Right from Center.");
		
		scheduleEleRelease(auton);
		
		auton.add(new DriveDistance(Subsystems.driveTrain, m*47.9));
		auton.add(new DriveRotate(Subsystems.driveTrain, -40));
		auton.add(new DriveDistance(Subsystems.driveTrain, m*59));
		auton.add(new DriveRotate(Subsystems.driveTrain, 40));
		auton.add(new Elevate(Subsystems.elevator, ElevatorPosition.Switch.value));
		auton.add(new DriveForTime(Subsystems.driveTrain, 1000, 0.5, 0));
		auton.add(new Outtake(Subsystems.arms));
		auton.add(new DriveDistance(Subsystems.driveTrain, m*-50));
		auton.add(new Elevate(Subsystems.elevator, ElevatorPosition.Home.value));
		
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
	
	//Done 3
	public static void schedule2LL(SequentialScheduler auton) {
		
		System.out.println("Scheduling Left Left from Center.");
		
		scheduleEleRelease(auton);
		
		auton.add(new DriveDistance(Subsystems.driveTrain, m*29.3));
		auton.add(new DriveRotate(Subsystems.driveTrain, 40));
		auton.add(new DriveDistance(Subsystems.driveTrain, m*83.4));
		auton.add(new DriveRotate(Subsystems.driveTrain, -40));
		auton.add(new Elevate(Subsystems.elevator, ElevatorPosition.Switch.value));
		auton.add(new DriveForTime(Subsystems.driveTrain, 1000, 0.5, 0));
		auton.add(new Outtake(Subsystems.arms));
		auton.add(new DriveDistance(Subsystems.driveTrain, m*-50));
		auton.add(new Elevate(Subsystems.elevator, ElevatorPosition.Home.value));
		
		System.out.print("Scheduled Left Left from Center.");
		
	}
	
	//Done 2
	public static void schedule3RR(SequentialScheduler auton) {

		System.out.println("Scheduling Right Right from Right.");
		
		schedule3RL(auton);
		
//		scheduleEleRelease(auton);
		
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*283.621));
//		auton.add(new DriveRotate(Subsystems.driveTrain, 75));
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*-17));
//		auton.add(new Elevate(Subsystems.elevator, ElevatorPosition.ScaleMax.value));
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*17));
//		auton.add(new Outtake(Subsystems.arms));
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*-17));
//		auton.add(new Elevate(Subsystems.elevator, ElevatorPosition.Home.value));
		
//		auton.add(new DriveRotate(Subsystems.driveTrain, 80));
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*91.502));
//		auton.add(new Intake(Subsystems.arms));
//		auton.add(new Elevate(Subsystems.elevator, 30));
//		auton.add(new Outtake(Subsystems.arms));
//		auton.add(new Elevate(Subsystems.elevator, ElevatorPosition.Home.value));
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*-13.692));
		
		System.out.print("Scheduled.");
		
	}
	
	//done
	public static void schedule3RL(SequentialScheduler auton) {

		System.out.println("Scheduling Right Left from Right.");
		
		scheduleEleRelease(auton);
		
		auton.add(new DriveDistance(Subsystems.driveTrain, m*152.338));
		auton.add(new DriveRotate(Subsystems.driveTrain, 90));
		auton.add(new Elevate(Subsystems.elevator, ElevatorPosition.Switch.value));
		auton.add(new DriveForTime(Subsystems.driveTrain, 2000, 0.5, 0));
		auton.add(new Outtake(Subsystems.arms));
		auton.add(new DriveDistance(Subsystems.driveTrain, m*-16.5));
		auton.add(new Elevate(Subsystems.elevator, ElevatorPosition.Home.value));
		auton.add(new DriveRotate(Subsystems.driveTrain, -90));
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*65.786));
//		auton.add(new DriveRotate(Subsystems.driveTrain, 90));
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*138.6));
//		auton.add(new DriveRotate(Subsystems.driveTrain, 60));
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*13.1));
//		auton.add(new Intake(Subsystems.arms));
//		auton.add(new Elevate(Subsystems.elevator, 3)
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*-13.1));
		
		System.out.print("Scheduled Right Left from Right.");
		
	}

	public static void schedule3LR(SequentialScheduler auton) {

		System.out.println("Scheduling Left Right from Right.");
		
		scheduleEleRelease(auton);

		auton.add(new DriveDistance(Subsystems.driveTrain, m*283.621));
		auton.add(new DriveRotate(Subsystems.driveTrain, 75));
		auton.add(new DriveDistance(Subsystems.driveTrain, m*-17));
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*17));
//		auton.add(new Outtake(Subsystems.arms));
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*-17));
//		auton.add(new Elevate(Subsystems.elevator, ElevatorPosition.Home.value));
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*10));
//		auton.add(new DriveRotate(Subsystems.driveTrain, 105));
		
		System.out.print("Scheduled Left Right from Right.");
		
	}
	
	//Done 1
	public static void schedule3LL(SequentialScheduler auton) {

		System.out.println("Scheduling Left Left from Right");
		
		scheduleEleRelease(auton);
		
		auton.add(new DriveDistance(Subsystems.driveTrain, 12*11));
		
//		scheduleEleRelease(auton);
		
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*218.606));
//		auton.add(new DriveRotate(Subsystems.driveTrain, 90));
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*197.245));
//		auton.add(new DriveRotate(Subsystems.driveTrain, -90));
//		auton.add(new Elevate(Subsystems.elevator, ElevatorPosition.ScaleMax.value));
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*60.136));
//		auton.add(new Outtake(Subsystems.arms));
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*-60.136));
//		auton.add(new Elevate(Subsystems.elevator, ElevatorPosition.Home.value));
//		auton.add(new DriveRotate(Subsystems.driveTrain, -165));
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*13.692));
//		auton.add(new Intake(Subsystems.arms));
//		auton.add(new Elevate(Subsystems.elevator, 30));
//		auton.add(new Outtake(Subsystems.arms));
//		auton.add(new Elevate(Subsystems.elevator, ElevatorPosition.Home.value));
//		auton.add(new DriveDistance(Subsystems.driveTrain, m*-13.692));
//		auton.add(new DriveRotate(Subsystems.driveTrain, 30));
		
		System.out.print("Scheduled Left Left from Right.");
		
	}
	
}
