package org.usfirst.frc.team4130.robot;

import org.usfirst.frc.team4130.loops.Elevate;

import com.ctre.phoenix.schedulers.ConcurrentScheduler;

public class Loops {
	//Autonomous Loops
	//TODO: Figure out how to dynamically determine the auton loops
	
	//Teleop Loops
	public static void scheduleTeleop(ConcurrentScheduler teleop){
		teleop.add(new Elevate(Subsystems.elevator, RobotMap.operatorJoystick));
	}
}
