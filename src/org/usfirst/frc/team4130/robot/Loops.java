package org.usfirst.frc.team4130.robot;

import org.usfirst.frc.team4130.loops.*;
import org.usfirst.frc.team4130.subsystem.ElevatorEmulator;
import org.usfirst.frc.team4130.subsystem.ElevatorPosition;

import com.ctre.phoenix.schedulers.*;


public class Loops {
	
	//Teleop Loops
	public static void sTeleop(ConcurrentScheduler teleop){
		
		System.out.println("Scheduling Teleop.");
		
		//Schedule all tasks for teleop
		teleop.add(new ElevatorTele());
		teleop.add(new DriveTele());
		teleop.add(new ArmsTele());
		teleop.add(new ElevatorRelease());
		
		System.out.println("Scheduled.");
		
		
	}
	
	//Testing Loops
	public static void sTest(SequentialScheduler auton) {
		
		//s2RL(auton, "ALT FS Double", false);
		
		ConcurrentSchedulerDone cst1 = new ConcurrentSchedulerDone();
		ConcurrentSchedulerDone cst2 = new ConcurrentSchedulerDone();
		ConcurrentSchedulerDone cst3 = new ConcurrentSchedulerDone();
		
		cst1.add(new DriveRotate(90));
		cst1.add(new Elevate(ElevatorPosition.Switch.value));
		
		SequentialScheduler sst1 = new SequentialScheduler(0);
		
		sst1.add(new Elevate(ElevatorPosition.Home.value));
		sst1.add(new Elevate(ElevatorPosition.Switch.value));
		
		cst2.add(new DriveRotate(-180));
		cst2.add(sst1);
		
		cst3.add(new DriveRotate(90));
		cst3.add(new Elevate(ElevatorPosition.Home.value));
		
		auton.add(cst1);
		auton.add(cst2);
		auton.add(cst3);
		
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
		auton.add(new Elevate(3));
		
//		ConcurrentScheduler eleRelease = new ConcurrentScheduler();
//		ConcurrentScheduler elevate = new ConcurrentScheduler();
//		
//		auton.add(new Print("Evevator Releasing..."));
//		
//		eleRelease.add(new ElevatorRelease());
//		
//		elevate.add(new Delay(250));
//		elevate.add(new Elevate(ElevatorPosition.Travel.value));
//		
//		eleRelease.add(elevate);
//		
//		auton.add(eleRelease);
//		
//		auton.add(new Print("Elevator Released!"));
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
									
		case "Front Switch Late":	auton.add(new DriveDistance(8));
									auton.add(new DriveRotate(-36.193*mInvert));
									auton.add(new DriveDistance(103));
									auton.add(new DriveRotate(36.193*mInvert));
									auton.add(new DriveDistance(8));
									auton.add(new Outtake());
									auton.add(new DriveDistance(-9));
									auton.add(new ElevatorRelease());
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
									
		case "FS Second High":		auton.add(new DriveRotate(-90*mInvert));
									auton.add(new DriveDistance(39.5, Subsystems.driveTrain.highGear));
									auton.add(new Intake());
									auton.add(new DriveDistance(-39.5, Subsystems.driveTrain.highGear));
									auton.add(new DriveRotate(90*mInvert));
									auton.add(new Elevate(ElevatorPosition.Switch.value));
									auton.add(new DriveDistance(9, Subsystems.driveTrain.highGear));
									auton.add(new Outtake());
									auton.add(new DriveDistance(-9, Subsystems.driveTrain.highGear));
									auton.add(new Elevate(ElevatorPosition.Home.value));
									break;
																
		case "Front Switch Double":	s1LL(auton, "Front Switch Late", invert);
									s1LL(auton, "Front Switch Second", invert);
									break;
									
		case "FS Double High":		s1LL(auton, "Front Switch Late", invert);
									s1LL(auton, "FS Second High", invert);
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
									auton.add(new Delay(5000));
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
		
		case "Front Switch Late":	s1LL(auton, target, invert);
									break;
								
		case "Front Switch Double": s1LL(auton, target, invert);
									break;
									
		case "FS Double High":		s1LL(auton, target, invert);
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
									auton.add(new Elevate(ElevatorPosition.Home.value));
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
									
		case "Front Switch Late":	auton.add(new DriveDistance(60.5));
									auton.add(new DriveRotate(-90*mInvert));
									auton.add(new DriveDistance(168));
									auton.add(new DriveRotate(90*mInvert));
									auton.add(new DriveDistance(39));
									auton.add(new Outtake());
									auton.add(new DriveDistance(-9));
									auton.add(new ElevatorRelease());
									break;
								
		case "Front Switch Double": s1RL(auton, "Front Switch Late", invert);
									s1LL(auton, "Front Switch Second", !invert);
									break;
									
		case "FS Double High": 		s1RL(auton, "Front Switch Late", invert);
									s1LL(auton, "FS Second High", !invert);
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
									
		case "Front Switch Late":	s1RL(auton, target, invert);
									break;
									
		case "FS Double High":		s1RL(auton, target, invert);
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
									
		case "Front Switch Late":	auton.add(new DriveDistance(29.3));
									auton.add(new DriveRotate(40*mInvert));
									auton.add(new DriveDistance(81));
									auton.add(new DriveRotate(-40*mInvert));
									auton.add(new DriveDistance(8));
									auton.add(new Outtake());
									auton.add(new DriveDistance(-9));
									auton.add(new ElevatorRelease());
									break;
								
		case "Front Switch Double":	s2LL(auton, "Front Switch", invert);
									s1LL(auton, "Front Switch Second", invert);
									break;
									
		case "FS Double Late":		s2LL(auton, "Front Switch Late", invert);
									s1LL(auton, "FS Second High", invert);
									break;
		
		case "Cross The Line":		sEleRelease(auton);
									auton.add(new DriveDistance(46.5));
									auton.add(new DriveRotate(-40*mInvert));
									auton.add(new DriveDistance(59));
									auton.add(new DriveRotate(170*mInvert));
									break;
									
		case "ALT FS Double":		auton.add(new ElevatorRelease());
									ConcurrentSchedulerDone cAFD1 = new ConcurrentSchedulerDone();
									cAFD1.add(new Elevate(65));
									cAFD1.add(new DriveDistance(104));
									auton.add(cAFD1);
									auton.add(new Outtake());
									ConcurrentSchedulerDone cAFD2 = new ConcurrentSchedulerDone();
									cAFD2.add(new Elevate(ElevatorPosition.Home.value));
									cAFD2.add(new DriveDistance(-55));
									auton.add(cAFD2);
									auton.add(new DriveRotate(-45*mInvert));
									auton.add(new DriveDistance(15));
									auton.add(new Intake());
									ConcurrentSchedulerDone cAFD3 = new ConcurrentSchedulerDone();
									SequentialScheduler sAFD3 = new SequentialScheduler(0);
									cAFD3.add(new Elevate(65));
									sAFD3.add(new DriveDistance(-15));
									sAFD3.add(new DriveRotate(45*mInvert));
									sAFD3.add(new DriveDistance(55, Subsystems.driveTrain.lowGear, 11000, 11000));
									cAFD3.add(sAFD3);
									auton.add(cAFD3);
									auton.add(new Outtake());
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
		
		case "Front Switch":		s2LL(auton, target, invert);
									break;
								
		case "Front Switch Late":	s2LL(auton, target, invert);
									break;
		
		case "Cross The Line":		s2LL(auton, target, invert);
									break;
									
		case "Front Switch Double": s2LL(auton, target, invert);
									break;
									
		case "FS Double High":		s2LL(auton, target, invert);
									break;
									
		case "ALT FS Double":		s2LL(auton, target, invert);
									break;
		
		case "Nothing":				sEleRelease(auton);
									break;
		
		default:					System.out.println("[WARNING] Scheduling Default, recieved target: \""+target+"\" for 2LR");
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
									
		case "Front Switch Late":	auton.add(new DriveDistance(46.5));
									auton.add(new DriveRotate(-40*mInvert));
									auton.add(new DriveDistance(59));
									auton.add(new DriveRotate(40*mInvert));
									auton.add(new DriveDistance(8));
									auton.add(new Outtake());
									auton.add(new DriveDistance(-9));
									auton.add(new ElevatorRelease());
									break;
									
		case "Front Switch Double":	s2RL(auton, "Front Switch", invert);
									s1LL(auton, "Front Switch Second", !invert);
									break;
									
		case "FS Double High":		s2RL(auton, "Front Switch Late", invert);
									s1LL(auton, "FS Second High", !invert);
									break;
		
		case "Cross The Line":		sEleRelease(auton);
									auton.add(new DriveDistance(29.3));
									auton.add(new DriveRotate(40*mInvert));
									auton.add(new DriveDistance(81));
									auton.add(new DriveRotate(-170*mInvert));
									break;
									
		case "ALT FS Double":		auton.add(new ElevatorRelease());
									auton.add(new DriveRotate(-45));
									ConcurrentSchedulerDone cAFD1 = new ConcurrentSchedulerDone();
									cAFD1.add(new Elevate(62));
									cAFD1.add(new DriveDistance(95.25));
									auton.add(cAFD1);
									auton.add(new Outtake());
									ConcurrentSchedulerDone cAFD2 = new ConcurrentSchedulerDone();
									cAFD2.add(new DriveDistance(-50.75));
									cAFD2.add(new Elevate(ElevatorPosition.Home.value));
									auton.add(cAFD2);
									auton.add(new DriveRotate(45*mInvert));
									auton.add(new DriveDistance(20));
									auton.add(new Intake());
									ConcurrentSchedulerDone cAFD3 = new ConcurrentSchedulerDone();
									SequentialScheduler sAFD3 = new SequentialScheduler(0);
									cAFD3.add(new Elevate(62));
									sAFD3.add(new DriveDistance(-20));
									sAFD3.add(new DriveRotate(-45*mInvert));
									sAFD3.add(new DriveDistance(50));
									cAFD3.add(sAFD3);
									auton.add(cAFD3);
									auton.add(new Outtake());
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
									
		case "ALT FS Double":		s2RL(auton, target, invert);
									break;
									
		case "Front Switch Late":	s2RL(auton, target, invert);
									break;
								
		case "Front Switch Double": s2RL(auton, target, invert);
									break;
									
		case "FS Double High":		s2RL(auton, target, invert);
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
