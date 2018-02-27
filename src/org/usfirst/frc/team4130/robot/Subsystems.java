package org.usfirst.frc.team4130.robot;

import org.usfirst.frc.team4130.subsystem.*;

/**
 * Class to create objects for each subsystem.
 * @author West, JCapp
 */
public class Subsystems {
	//public static Elevator elevator;
	public static DriveTrain driveTrain;
	//public static Arms arms;
	
	public static void init(){
		//elevator = new Elevator();
		driveTrain = new DriveTrain();
		//arms = new Arms();
	}
}
