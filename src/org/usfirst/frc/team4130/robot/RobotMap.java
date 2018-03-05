package org.usfirst.frc.team4130.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;

public class RobotMap {
	
	public static TalonSRX leftDriveMaster;
	public static TalonSRX leftDriveFollower;
	public static TalonSRX leftDriveFollower2;
	
	public static PigeonIMU pigeon;
	
	public static TalonSRX rightDriveMaster;
	public static TalonSRX rightDriveFollower;
	public static TalonSRX rightDriveFollower2;
	
	public static TalonSRX elevatorMaster;
	
	public static VictorSPX leftIntake;
	public static VictorSPX rightIntake;
	
	public static DoubleSolenoid driveShift;
	public static DoubleSolenoid armsClamp;
	
	public static Joystick driverJoystick;
	public static Joystick operatorJoystick;
	
	public static DigitalInput leftCube;
	public static DigitalInput rightCube;
	public static Servo elevatorReleaseServo;
	
	public static void init(){
		
		elevatorReleaseServo = new Servo(0);
		
		leftCube = new DigitalInput(0);
		rightCube = new DigitalInput(1);
		
		leftDriveMaster = new TalonSRX(1);
		leftDriveFollower = new TalonSRX(2);
		leftDriveFollower2 = new TalonSRX(3);
		
		rightDriveMaster = new TalonSRX(4);
		rightDriveFollower = new TalonSRX(5);
		rightDriveFollower2 = new TalonSRX(6);
		
//		elevatorMaster = new TalonSRX(9);
//		
//		leftIntake = new VictorSPX(1);
//		rightIntake = new VictorSPX(2);
//		
		driveShift = new DoubleSolenoid(0,1);
//		armsClamp = new DoubleSolenoid(2,3);
		
		driverJoystick = new Joystick(0);
		operatorJoystick = new Joystick(1);
		
		pigeon = new PigeonIMU(0);//rightDriveFollower2);
		
	}
}
