package org.usfirst.frc.team4130.robot;

import org.usfirst.frc.team4130.subsystem.Elevator;

public class Subsystems {
	public static Elevator elevator;
	
	public static void init(){
		elevator = new Elevator();
	}
}
