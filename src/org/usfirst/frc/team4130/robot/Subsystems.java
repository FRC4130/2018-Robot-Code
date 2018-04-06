package org.usfirst.frc.team4130.robot;

import org.usfirst.frc.team4130.subsystem.*;

public class Subsystems {
	
	public static Elevator elevator;
	public static DriveTrain driveTrain;
	public static Arms arms;
	
	public static void init(){
		
		elevator = new Elevator();//Emulator();
		driveTrain = new DriveTrain();
		arms = new Arms();//Emulator();
		
	}
	
}
