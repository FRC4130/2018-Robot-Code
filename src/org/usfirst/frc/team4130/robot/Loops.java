package org.usfirst.frc.team4130.robot;

import org.usfirst.frc.team4130.loops.*;
import org.usfirst.frc.team4130.subsystem.ElevatorPosition;

import com.ctre.phoenix.schedulers.*;


public class Loops {
	
	//Teleop Loops
	public static void sTeleop(ConcurrentScheduler teleop){
		
		System.out.println("Scheduling Teleop.");
		
		//Schedule all tasks for teleop
		teleop.add(new ElevatorTele());
		teleop.add(new DriveTest());//Tele());
		teleop.add(new ArmsTele());
		
		System.out.println("Scheduled.");
		
		
	}
	
	//Testing Loops
	public static void sTest(SequentialScheduler auton) {
		
		//auton.add(new Delay(5000));
		auton.add(new DriveDistance(150, Subsystems.driveTrain.highGear, 30000, 30000));
		//auton.add(new DriveDistance(10));
		//auton.add(new Delay(1000));
		//auton.add(auton);
		
	}
	
	public static void sHokeyPokey(SequentialScheduler auton) {
		
		System.out.println("Preparing to get down...");
		
		sEleRelease(auton);
		
		auton.add(new DriveDistance(2*12));
		auton.add(new DriveRotate(45));
		
		SequentialScheduler h = new SequentialScheduler(0);
		
		h.add(new DriveDistance(2*12));
		h.add(new DriveDistance(-2*12));
		h.add(new DriveRotate(90));
		h.add(h);
		
		auton.add(h);
		
		System.out.println("Prepared.");
		
	}
	
	//Multi-Use Loops
	public static void sEleRelease(SequentialScheduler auton) {
		auton.add(new ElevatorRelease());
		auton.add(new Elevate(ElevatorPosition.Travel.value));
	}
	
	//1LL
	public static void s1LL(SequentialScheduler auton, String target, boolean invert) {
		
		double mInvert = invert ? -1 : 1;
		
		System.out.println("[Info] Scheduling "+target+" for 1LL");
		
		auton.add(new Print("[Warning] Running "+target+" for 1LL"));
		
		switch (target) {
		
		case "Outside Switch":		sEleRelease(auton);
									auton.add(new DriveDistance(152.3));
									auton.add(new DriveRotate(-90*mInvert));
									auton.add(new Elevate(ElevatorPosition.Switch.value));
									auton.add(new DriveDistance(16.5));
									auton.add(new Outtake());
									auton.add(new DriveDistance(-16.5));
									auton.add(new Elevate(ElevatorPosition.Home.value));
									break;
		
		case "Front Switch":		sEleRelease(auton);
									auton.add(new DriveDistance(8));
									auton.add(new DriveRotate(-36.193*mInvert));
									auton.add(new DriveDistance(103));
									auton.add(new DriveRotate(36.193*mInvert));
									auton.add(new Elevate(ElevatorPosition.Switch.value));
									auton.add(new DriveDistance(8));
									auton.add(new Outtake());
									auton.add(new DriveDistance(-9));
									auton.add(new Elevate(ElevatorPosition.Home.value));
									break;
								
		case "Front Switch Second":	auton.add(new DriveRotate(-90*mInvert));
									auton.add(new DriveDistance(39.5));
									auton.add(new Intake());
									auton.add(new DriveDistance(-39.5));
									auton.add(new DriveRotate(90*mInvert));
									auton.add(new Elevate(ElevatorPosition.Switch.value));
									auton.add(new DriveDistance(9));
									auton.add(new Outtake());
									auton.add(new DriveDistance(-9));
									auton.add(new Elevate(ElevatorPosition.Home.value));
									break;
									
		case "Front Switch Double":	s1LL(auton, "Front Switch", invert);
									s1LL(auton, "Front Switch Second", invert);
									break;
									
		case "Scale":				sEleRelease(auton);
									auton.add(new DriveDistance(258.02));
									auton.add(new DriveRotate(-45*mInvert));
									auton.add(new Elevate(ElevatorPosition.ScaleMax.value));
									auton.add(new DriveDistance(35.54,Subsystems.driveTrain.lowGear, 10000, 3000));
									auton.add(new Outtake());
									auton.add(new DriveDistance(-35.54,Subsystems.driveTrain.lowGear, 10000, 3000));
									auton.add(new Elevate(ElevatorPosition.Home.value));
									break;
		
		case "Cross The Line":		sEleRelease(auton);
									auton.add(new DriveDistance(10.5*12));
									break;
		
		case "Nothing":				sEleRelease(auton);
									break;
		
		default:					System.out.println("[WARNING] Scheduling Default, recieved target: \""+target+"\" for 1LL");
									s1LL(auton, "Outside Switch", invert);
									break;
		
		}
		
	}
	
	public static void s1LR(SequentialScheduler auton, String target, boolean invert) {
		
		double mInvert = invert ? -1 : 1;
		
		System.out.println("[Info] Scheduling "+target+" for 1LR");
		
		auton.add(new Print("[Warning] Running "+target+" for 1LR"));
		
		switch (target) {
		
		case "Outside Switch":		s1LL(auton, target, invert);
									break;
		
		case "Front Switch":		s1LL(auton, target, invert);
									break;
								
		case "Front Switch Double": s1LL(auton, target, invert);
									break;
		
		case "Scale":				sEleRelease(auton);
									auton.add(new DriveDistance(217));
									auton.add(new DriveRotate(-90*mInvert));
									auton.add(new DriveDistance(200.5));
									auton.add(new DriveRotate(90*mInvert));
									auton.add(new Elevate(ElevatorPosition.ScaleMax.value));
									auton.add(new DriveDistance(63,Subsystems.driveTrain.lowGear, 10000, 3000));
									auton.add(new Outtake());
									auton.add(new DriveDistance(-32,Subsystems.driveTrain.lowGear, 10000, 3000));
									auton.add(new Elevate(ElevatorPosition.Switch.value));
									break;
		
		case "Cross The Line":		s1LL(auton, target, invert);
									break;
		
		case "Nothing":				sEleRelease(auton);
									break;
		
		default:					System.out.println("[WARNING] Scheduling Default, recieved target: \""+target+"\" for 1LR");
									s1LR(auton, "Outside Switch", invert);
									break;
		
		}
		
	}
	
	public static void s1RL(SequentialScheduler auton, String target, boolean invert) {
		
		double mInvert = invert ? -1 : 1;
		
		System.out.println("[Info] Scheduling "+target+" for 1RL");
		
		auton.add(new Print("[Warning] Running "+target+" for 1RL"));
		
		switch (target) {
		
		case "Outside Switch":		sEleRelease(auton);
									auton.add(new DriveDistance(217));
									auton.add(new DriveRotate(-90*mInvert));
									auton.add(new DriveDistance(227));
									auton.add(new DriveRotate(-90*mInvert));
									auton.add(new DriveDistance(64));
									auton.add(new DriveRotate(-90*mInvert));
									auton.add(new Elevate(ElevatorPosition.Switch.value));
									auton.add(new DriveDistance(11.5));
									auton.add(new Outtake());
									auton.add(new DriveDistance(-11.5));
									auton.add(new Elevate(ElevatorPosition.Home.value));
									break;
		
		case "Front Switch":		sEleRelease(auton);
									auton.add(new DriveDistance(60.5));
									auton.add(new DriveRotate(-90*mInvert));
									auton.add(new DriveDistance(168));
									auton.add(new DriveRotate(90*mInvert));
									auton.add(new Elevate(ElevatorPosition.Switch.value));
									auton.add(new DriveDistance(39));
									auton.add(new Outtake());
									auton.add(new DriveDistance(-9));
									auton.add(new Elevate(ElevatorPosition.Home.value));
									break;
								
		case "Front Switch Double": s1RL(auton, "Front Switch", invert);
									s1LL(auton, "Front Switch Second", !invert);
									break;
		
		case "Scale":				s1LL(auton, target, invert);
									break;
		
		case "Cross The Line":		s1LL(auton, target, invert);
									break;
		
		case "Nothing":				sEleRelease(auton);
									break;
		
		default:					System.out.println("[WARNING] Scheduling Default, recieved target: \""+target+"\" for 1RL");
									s1RL(auton, "Cross The Line", invert);
									break;
		
		}
		
	}
	
	public static void s1RR(SequentialScheduler auton, String target, boolean invert) {
		
		double mInvert = invert ? -1 : 1;
		
		System.out.println("[Info] Scheduling "+target+" for 1RR");
		
		auton.add(new Print("[Warning] Running "+target+" for 1RL"));
		
		switch (target) {
		
		case "Outside Switch":		s1RL(auton, target, invert);
									break;
		
		case "Front Switch":		s1RL(auton, target, invert);
									break;
		
		case "Front Switch Double":	s1RL(auton, target, invert);
									break;
		
		case "Scale":				s1LR(auton, target, invert);
									break;
		
		case "Cross The Line":		s1LL(auton, target, invert);
									break;
		
		case "Nothing":				sEleRelease(auton);
									break;
		
		default:					System.out.println("[WARNING] Scheduling Default, recieved target: \""+target+"\" for 1RR");
									s1RR(auton, "Outside Switch", invert);
									break;
		
		}
		
	}
	
	public static void s2LL(SequentialScheduler auton, String target, boolean invert) {
		
		double mInvert = invert ? -1 : 1;
		
		System.out.println("[Info] Scheduling "+target+" for 2LL");
		
		auton.add(new Print("[Warning] Running "+target+" for 2LL"));
		
		switch (target) {
		
		case "Front Switch":		sEleRelease(auton);
									auton.add(new DriveDistance(29.3));
									auton.add(new DriveRotate(40*mInvert));
									auton.add(new DriveDistance(81));
									auton.add(new DriveRotate(-40*mInvert));
									auton.add(new Elevate(ElevatorPosition.Switch.value));
									auton.add(new DriveDistance(8));
									auton.add(new Outtake());
									auton.add(new DriveDistance(-9));
									auton.add(new Elevate(ElevatorPosition.Home.value));
									break;
								
		case "Front Switch Double":	s2LL(auton, "Front Switch", invert);
									s1LL(auton, "Front Switch Second", invert);
									break;
		
		case "Cross The Line":		sEleRelease(auton);
									auton.add(new DriveDistance(46.5));
									auton.add(new DriveRotate(-40*mInvert));
									auton.add(new DriveDistance(59));
									auton.add(new DriveRotate(170*mInvert));
									break;
		
		case "Nothing":				sEleRelease(auton);
									break;
		
		default:					System.out.println("[WARNING] Scheduling Default, recieved target: \""+target+"\" for 2LL");
									s2LL(auton, "Front Switch", invert);
									break;
		
		}
		
	}
	
	public static void s2LR(SequentialScheduler auton, String target, boolean invert) {
		
		double mInvert = invert ? -1 : 1;
		
		System.out.println("[Info] Scheduling "+target+" for 2LR");
		
		auton.add(new Print("[Warning] Running "+target+" for 2LR"));
		
		switch (target) {
		
		case "Front Switch":	s2LL(auton, target, invert);
								break;
		
		case "Cross The Line":	s2LL(auton, target, invert);
								break;
		
		case "Nothing":			sEleRelease(auton);
								break;
		
		default:				System.out.println("[WARNING] Scheduling Default, recieved target: \""+target+"\" for 2LR");
								s2LR(auton, "Front Switch", invert);
								break;
		
		}
		
	}
	
	public static void s2RL(SequentialScheduler auton, String target, boolean invert) {
		
		double mInvert = invert ? -1 : 1;
		
		System.out.println("[Info] Scheduling "+target+" for 2RL");
		
		auton.add(new Print("[Warning] Running "+target+" for 2RL"));
		
		switch (target) {
		
		case "Front Switch":		sEleRelease(auton);
									auton.add(new DriveDistance(46.5));
									auton.add(new DriveRotate(-40*mInvert));
									auton.add(new DriveDistance(59));
									auton.add(new DriveRotate(40*mInvert));
									auton.add(new Elevate(ElevatorPosition.Switch.value));
									auton.add(new DriveDistance(8));
									auton.add(new Outtake());
									auton.add(new DriveDistance(-9));
									auton.add(new Elevate(ElevatorPosition.Home.value));
									break;
									
		case "Front Switch Double":	s2RL(auton, "Front Switch", invert);
									s1LL(auton, "Front Switch Second", !invert);
									break;
		
		case "Cross The Line":		sEleRelease(auton);
									auton.add(new DriveDistance(29.3));
									auton.add(new DriveRotate(40*mInvert));
									auton.add(new DriveDistance(81));
									auton.add(new DriveRotate(-170*mInvert));
									break;
		
		case "Nothing":				sEleRelease(auton);
									break;
		
		default:					System.out.println("[WARNING] Scheduling Default, recieved target: \""+target+"\" for 2RL");
									s2RL(auton, "Front Switch", invert);
									break;
		
		}
		
	}
	
	public static void s2RR(SequentialScheduler auton, String target, boolean invert) {
		
		double mInvert = invert ? -1 : 1;
		
		System.out.println("[Info] Scheduling "+target+" for 2RR");
		
		auton.add(new Print("[Warning] Running "+target+" for 2RR"));
		
		switch (target) {
		
		case "Front Switch":		s2RL(auton, target, invert);
									break;
								
		case "Front Switch Double": s2RL(auton, target, invert);
									break;
		
		case "Cross The Line":		s2LR(auton, target, invert);
									break;
		
		case "Nothing":				sEleRelease(auton);
									break;
		
		default:					System.out.println("[WARNING] Scheduling Default, recieved target: \""+target+"\" for 2RR");
									s2RR(auton, "Front Switch", invert);
									break;
		
		}
		
	}
	
	public static void s3LL(SequentialScheduler auton, String target, boolean invert) {
		
		double mInvert = invert ? -1 : 1;
		
		System.out.println("[Info] Scheduling "+target+" for 3LL");
		
		auton.add(new Print("[Warning] Running "+target+" for 3LL"));
		
		s1RR(auton, target, !invert);
	
	}
	
	public static void s3LR(SequentialScheduler auton, String target, boolean invert) {
		
		double mInvert = invert ? -1 : 1;
		
		System.out.println("[Info] Scheduling "+target+" for 3LR");
		
		auton.add(new Print("[Warning] Running "+target+" for 3LR"));
		
		s1RL(auton, target, !invert);
	
	}
	
	public static void s3RL(SequentialScheduler auton, String target, boolean invert) {
		
		double mInvert = invert ? -1 : 1;
		
		System.out.println("[Info] Scheduling "+target+" for 3RL");
		
		auton.add(new Print("[Warning] Running "+target+" for 3RL"));
		
		s1LR(auton, target, !invert);
	
	}
	
	public static void s3RR(SequentialScheduler auton, String target, boolean invert) {
		
		double mInvert = invert ? -1 : 1;
		
		System.out.println("[Info] Scheduling "+target+" for 3RR");
		
		auton.add(new Print("[Warning] Running "+target+" for 3RR"));
		
		s1LL(auton, target, !invert);
	
	}
	
}
