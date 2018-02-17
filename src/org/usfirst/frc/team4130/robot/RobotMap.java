package org.usfirst.frc.team4130.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class RobotMap {
	public static TalonSRX leftDriveMaster = new TalonSRX(1);
	public static TalonSRX leftDriveFollower = new TalonSRX(2);
	public static TalonSRX leftDriveFollower2 = new TalonSRX(3);
	
	public static TalonSRX rightDriveMaster = new TalonSRX(4);
	public static TalonSRX rightDriveFollower = new TalonSRX(5);
	public static TalonSRX rightDriveFollower2 = new TalonSRX(6);
	
	public static TalonSRX elevatorMaster = new TalonSRX(7);
	
	public static VictorSPX leftIntake = new VictorSPX(1);
	public static VictorSPX rightIntake = new VictorSPX(2);
	
	public static DoubleSolenoid driveShift = new DoubleSolenoid(0,1);
	public static DoubleSolenoid intakeClamp = new DoubleSolenoid(2,3);
	
	
	public static void init(){
		
	}
}
