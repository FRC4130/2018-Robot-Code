package org.usfirst.frc.team4130.robot;

import org.usfirst.frc.team4130.loops.*;

import com.ctre.phoenix.schedulers.ConcurrentScheduler;

public class Loops {
	//Autonomous Loops
	//TODO: Figure out how to dynamically determine the auton loops
	
	//Teleop Loops
	public static void scheduleTeleop(ConcurrentScheduler teleop){
		
		//Schedule all tasks for teleop
		teleop.add(new ElevatorTele(Subsystems.elevator, RobotMap.operatorJoystick));
		teleop.add(new DriveTele(Subsystems.driveTrain, RobotMap.driverJoystick));
		teleop.add(new ArmsTele(Subsystems.arms, RobotMap.driverJoystick));
		
	}
}
