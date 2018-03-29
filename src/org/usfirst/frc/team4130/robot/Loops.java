package org.usfirst.frc.team4130.robot;



import org.usfirst.frc.team4130.loops.*;
import org.usfirst.frc.team4130.subsystem.ElevatorPosition;

import com.ctre.phoenix.schedulers.*;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Loops {
	
	private static double m = 1;
	
	//Teleop Loops
	public static void sTeleop(ConcurrentScheduler teleop){
		
		System.out.println("Scheduling Teleop.");
		
		//Schedule all tasks for teleop
		teleop.add(new ElevatorTele());
		teleop.add(new DriveTele());
		teleop.add(new ArmsTele());
		
		System.out.println("Scheduled.");
		
	}
	
	//Testing Loops
	public static void sHokeyPokey(SequentialScheduler auton) {
		
		System.out.println("Preparing to get down...");
		
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
		
		sEleRelease(auton);
		
		System.out.println("[Info] Scheduling "+target+" for 1LL");
		
		auton.add(new Print("[Warning] Running "+target+" for 1LL"));
		
		switch (target) {
		
		case "Outside Switch":	auton.add(new DriveDistance(152.3));
								auton.add(new DriveRotate(-90*mInvert));
								auton.add(new Elevate(ElevatorPosition.Switch.value));
								auton.add(new DriveDistance(16.5));
								auton.add(new Outtake());
								auton.add(new DriveDistance(-16.5));
								auton.add(new Elevate(ElevatorPosition.Home.value));
								break;
		
		case "Front Switch":	auton.add(new DriveDistance(8));
								auton.add(new DriveRotate(-39.193*mInvert));
								auton.add(new DriveDistance(105.94));
								auton.add(new DriveRotate(39.193*mInvert));
								auton.add(new Elevate(ElevatorPosition.Switch.value));
								auton.add(new DriveDistance(8));
								auton.add(new Outtake());
								auton.add(new DriveDistance(-8));
								auton.add(new Elevate(ElevatorPosition.Home.value));
								auton.add(new DriveRotate(-45*mInvert));
								break;
		
		case "Scale":			auton.add(new DriveDistance(258.02));
								auton.add(new DriveRotate(-45*mInvert));
								auton.add(new Elevate(ElevatorPosition.ScaleMax.value));
								auton.add(new DriveDistance(35.54));
								auton.add(new Outtake());
								auton.add(new DriveDistance(-35.54));
								auton.add(new Elevate(ElevatorPosition.Home.value));
								auton.add(new DriveRotate(-45*mInvert));
								break;
		
		case "Cross The Line":	auton.add(new DriveDistance(10.5*12));
								break;
		
		case "Nothing":			break;
		
		default:				System.out.println("[WARNING] Scheduling Default, recieved target: \""+target+"\" for 1LL");
								s1LL(auton, "Outside Switch", invert);
								break;
		
		}
		
	}
	
	public static void s1LR(SequentialScheduler auton, String target, boolean invert) {
		
		double mInvert = invert ? -1 : 1;
		
		sEleRelease(auton);
		
		System.out.println("[Info] Scheduling "+target+" for 1LR");
		
		auton.add(new Print("[Warning] Running "+target+" for 1LR"));
		
		switch (target) {
		
		case "Outside Switch":	s1LL(auton, target, invert);
								break;
		
		case "Front Switch":	s1LL(auton, target, invert);
								break;
		
		case "Scale":			auton.add(new DriveDistance(218));
								auton.add(new DriveRotate(-90*mInvert));
								auton.add(new DriveDistance(188));
								auton.add(new DriveRotate(90*mInvert));
								auton.add(new Elevate(ElevatorPosition.ScaleMax.value));
								auton.add(new DriveDistance(58.75));
								auton.add(new Outtake());
								auton.add(new DriveDistance(-58.75));
								auton.add(new Elevate(ElevatorPosition.Home.value));
								break;
		
		case "Cross The Line":	s1LL(auton, target, invert);
								break;
		
		case "Nothing":			break;
		
		default:				System.out.println("[WARNING] Scheduling Default, recieved target: \""+target+"\" for 1LR");
								s1LR(auton, "Outside Switch", invert);
								break;
		
		}
		
	}
	
	public static void s1RL(SequentialScheduler auton, String target, boolean invert) {
		
		double mInvert = invert ? -1 : 1;
		
		sEleRelease(auton);
		
		System.out.println("[Info] Scheduling "+target+" for 1RL");
		
		auton.add(new Print("[Warning] Running "+target+" for 1RL"));
		
		switch (target) {
		
		case "Outside Switch":	auton.add(new DriveDistance(224));
								auton.add(new DriveRotate(-90));
								auton.add(new DriveDistance(221.5));
								auton.add(new DriveRotate(-90));
								auton.add(new DriveDistance(59));
								auton.add(new DriveRotate(-90));
								auton.add(new Elevate(ElevatorPosition.Switch.value));
								auton.add(new DriveDistance(16));
								auton.add(new Outtake());
								auton.add(new DriveDistance(-16));
								auton.add(new Elevate(ElevatorPosition.Home.value));
								break;
		
		case "Front Switch":	auton.add(new DriveDistance(60.5));
								auton.add(new DriveRotate(-90*mInvert));
								auton.add(new DriveDistance(168));
								auton.add(new DriveRotate(90*mInvert));
								auton.add(new Elevate(ElevatorPosition.Switch.value));
								auton.add(new DriveDistance(39));
								auton.add(new Outtake());
								break;
		
		case "Scale":			s1LL(auton, target, invert);
								break;
		
		case "Cross The Line":	s1LL(auton, target, invert);
								break;
		
		case "Nothing":			break;
		
		default:				System.out.println("[WARNING] Scheduling Default, recieved target: \""+target+"\" for 1RL");
								s1RL(auton, "Cross The Line", invert);
								break;
		
		}
		
	}
	
	public static void s1RR(SequentialScheduler auton, String target, boolean invert) {
		
		double mInvert = invert ? -1 : 1;
		
		sEleRelease(auton);
		
		System.out.println("[Info] Scheduling "+target+" for 1RR");
		
		auton.add(new Print("[Warning] Running "+target+" for 1RL"));
		
		switch (target) {
		
		case "Outside Switch":	auton.add(new DriveDistance(217));
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
		
		case "Front Switch":	s1RL(auton, target, invert);
								break;
		
		case "Scale":			s1LR(auton, target, invert);
								break;
		
		case "Cross The Line":	s1LL(auton, target, invert);
								break;
		
		case "Nothing":			break;
		
		default:				System.out.println("[WARNING] Scheduling Default, recieved target: \""+target+"\" for 1RR");
								s1RR(auton, "Outside Switch", invert);
								break;
		
		}
		
	}
	
	public static void s2LL(SequentialScheduler auton, String target, boolean invert) {
		
		double mInvert = invert ? -1 : 1;
		
		sEleRelease(auton);
		
		System.out.println("[Info] Scheduling "+target+" for 2LL");
		
		auton.add(new Print("[Warning] Running "+target+" for 2LL"));
		
		switch (target) {
		
		case "Front Switch":	auton.add(new DriveDistance(29.3));
								auton.add(new DriveRotate(40*mInvert));
								auton.add(new DriveDistance(83.4));
								auton.add(new DriveRotate(-40*mInvert));
								auton.add(new Elevate(ElevatorPosition.Switch.value));
								auton.add(new DriveDistance(8));
								auton.add(new Outtake());
								auton.add(new DriveDistance(-8));
								auton.add(new Elevate(ElevatorPosition.Home.value));
								auton.add(new DriveRotate(-40*mInvert));
								break;
		
		case "Cross The Line":	s2RL(auton, target, invert);
								break;
		
		case "Nothing":			break;
		
		default:				System.out.println("[WARNING] Scheduling Default, recieved target: \""+target+"\" for 2LL");
								s2LL(auton, "Front Switch", invert);
								break;
		
		}
		
	}
	
	public static void s2LR(SequentialScheduler auton, String target, boolean invert) {
		
		double mInvert = invert ? -1 : 1;
		
		sEleRelease(auton);
		
		System.out.println("[Info] Scheduling "+target+" for 2LR");
		
		auton.add(new Print("[Warning] Running "+target+" for 2LR"));
		
		switch (target) {
		
		case "Front Switch":	s2LL(auton, target, invert);
								break;
		
		case "Cross The Line":	s2LL(auton, target, invert);
								break;
		
		case "Nothing":			break;
		
		default:				System.out.println("[WARNING] Scheduling Default, recieved target: \""+target+"\" for 2LR");
								s2LR(auton, "Front Switch", invert);
								break;
		
		}
		
	}
	
	public static void s2RL(SequentialScheduler auton, String target, boolean invert) {
		
		double mInvert = invert ? -1 : 1;
		
		sEleRelease(auton);
		
		System.out.println("[Info] Scheduling "+target+" for 2RL");
		
		auton.add(new Print("[Warning] Running "+target+" for 2RL"));
		
		switch (target) {
		
		case "Front Switch":	auton.add(new DriveDistance(47.9));
								auton.add(new DriveRotate(-40));
								auton.add(new DriveDistance(59));
								auton.add(new Outtake());
								auton.add(new Elevate(ElevatorPosition.Switch.value));
								auton.add(new DriveDistance(8));
								auton.add(new Outtake());
								auton.add(new DriveDistance(-8));
								auton.add(new Elevate(ElevatorPosition.Home.value));
								auton.add(new DriveRotate(-90));
								break;
		
		case "Cross The Line":	auton.add(new DriveDistance(29.3));
								auton.add(new DriveRotate(-40));
								auton.add(new DriveDistance(83.4));
								auton.add(new DriveRotate(-140));
								auton.add(new DriveDistance(-8));
								break;
		
		case "Nothing":			break;
		
		default:				System.out.println("[WARNING] Scheduling Default, recieved target: \""+target+"\" for 2RL");
								s2RL(auton, "Front Switch", invert);
								break;
		
		}
		
	}
	
	public static void s2RR(SequentialScheduler auton, String target, boolean invert) {
		
		double mInvert = invert ? -1 : 1;
		
		sEleRelease(auton);
		
		System.out.println("[Info] Scheduling "+target+" for 2RR");
		
		auton.add(new Print("[Warning] Running "+target+" for 2RR"));
		
		switch (target) {
		
		case "Front Switch":	s2RL(auton, target, invert);
								break;
		
		case "Cross The Line":	s2LR(auton, target, invert);
								break;
		
		case "Nothing":			break;
		
		default:				System.out.println("[WARNING] Scheduling Default, recieved target: \""+target+"\" for 2RR");
								s2RR(auton, "Front Switch", invert);
								break;
		
		}
		
	}
	
	public static void s3LL(SequentialScheduler auton, String target, boolean invert) {
		
		double mInvert = invert ? -1 : 1;
		
		sEleRelease(auton);
		
		System.out.println("[Info] Scheduling "+target+" for 3LL");
		
		auton.add(new Print("[Warning] Running "+target+" for 3LL"));
		
		s1RR(auton, target, true);
	
	}
	
	public static void s3LR(SequentialScheduler auton, String target, boolean invert) {
		
		double mInvert = invert ? -1 : 1;
		
		sEleRelease(auton);
		
		System.out.println("[Info] Scheduling "+target+" for 3LR");
		
		auton.add(new Print("[Warning] Running "+target+" for 3LR"));
		
		s1RL(auton, target, true);
	
	}
	
	public static void s3RL(SequentialScheduler auton, String target, boolean invert) {
		
		double mInvert = invert ? -1 : 1;
		
		sEleRelease(auton);
		
		System.out.println("[Info] Scheduling "+target+" for 3RL");
		
		auton.add(new Print("[Warning] Running "+target+" for 3RL"));
		
		s1LR(auton, target, true);
	
	}
	
	public static void s3RR(SequentialScheduler auton, String target, boolean invert) {
		
		double mInvert = invert ? -1 : 1;
		
		sEleRelease(auton);
		
		System.out.println("[Info] Scheduling "+target+" for 3RR");
		
		auton.add(new Print("[Warning] Running "+target+" for 3RR"));
		
		s1LL(auton, target, true);
	
	}
	
}
