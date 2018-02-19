package org.usfirst.frc.team4130.subsystem;

import org.usfirst.frc.team4130.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class DriveTrain {
	
	private TalonSRX left = RobotMap.leftDriveMaster;
	private TalonSRX leftf = RobotMap.leftDriveFollower;
	private TalonSRX leftf2 = RobotMap.leftDriveFollower2;
	private TalonSRX right = RobotMap.rightDriveMaster;
	private TalonSRX rightf = RobotMap.rightDriveFollower;
	private TalonSRX rightf2 = RobotMap.rightDriveFollower2;
	private PigeonIMU pigeon = RobotMap.pigeon;
	private DoubleSolenoid shifter = RobotMap.driveShift;
	private final int kTimeoutMs = 10;
	private final int highGains = 0;
	private final int lowGains = 1;
	private final Value highGear = Value.kForward;
	private final Value lowGear = Value.kReverse;
	
	public DriveTrain () {
		
		System.out.println("Drive Init has run.");
		
		left.setInverted(true);
		leftf.setInverted(true);
		leftf2.setInverted(true);
		
		right.setInverted(false);
		rightf.setInverted(false);
		rightf2.setInverted(false);
		
		leftf.follow(left);
		leftf2.follow(left);
		
		rightf.follow(right);
		rightf2.follow(right);
		
		left.set(ControlMode.PercentOutput, 0);
		right.set(ControlMode.PercentOutput, 0);
		
		setNeutralMode(NeutralMode.Brake);
		
		setShifter(highGear);
		
		pigeon.setFusedHeading(0, 10);
		
		/***Motion Magic/speed control***/
		left.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, highGains, kTimeoutMs);
		left.setSensorPhase(true);
		
		right.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, highGains, kTimeoutMs);
		right.setSensorPhase(true);
		
		left.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, lowGains, kTimeoutMs);
		left.setSensorPhase(true);
		
		right.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, lowGains, kTimeoutMs);
		right.setSensorPhase(true);
		
		left.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, kTimeoutMs);
		left.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, kTimeoutMs);
		
		right.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, kTimeoutMs);
		right.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, kTimeoutMs);
		
		left.configNominalOutputForward(0, kTimeoutMs);
		left.configNominalOutputReverse(0, kTimeoutMs);
		left.configPeakOutputForward(1, kTimeoutMs);
		left.configPeakOutputReverse(-1, kTimeoutMs);
		
		right.configNominalOutputForward(0, kTimeoutMs);
		right.configNominalOutputReverse(0, kTimeoutMs);
		right.configPeakOutputForward(1, kTimeoutMs);
		right.configPeakOutputReverse(-1, kTimeoutMs);

		/**HighGains***/
//		left.selectProfileSlot(0, 0);
//		left.config_kF(highGains, 0.2, kTimeoutMs);
//		left.config_kP(highGains, 0.2, kTimeoutMs);
//		left.config_kI(highGains, 0, kTimeoutMs);
//		left.config_kD(HighGains, 0, kTimeoutMs);
//		left.config_IntegralZone(0, 0, kTimeoutMs);
		
//		right.selectProfileSlot(0, 0);
//		right.config_kF(highGains, 0.2, kTimeoutMs);
//		right.config_kP(highGains, 0.2, kTimeoutMs);
//		right.config_kI(highGains, 0, kTimeoutMs);
//		right.config_kD(highGains, 0, kTimeoutMs);
//		right.config_IntegralZone(highGains, 0, kTimeoutMs);
		
		/***LowGains***/
//		left.selectProfileSlot(0, 0);
//		left.config_kF(lowGains, 0.2, kTimeoutMs);
//		left.config_kP(lowGains, 0.2, kTimeoutMs);
//		left.config_kI(0, 0, kTimeoutMs);
//		left.config_kD(0, 0, kTimeoutMs);
//		left.config_IntegralZone(0, 0, kTimeoutMs);
		
//		right.selectProfileSlot(0, 0);
//		right.config_kF(lowGains, 0.2, kTimeoutMs);
//		right.config_kP(lowGains, 0.2, kTimeoutMs);
//		right.config_kI(lowGains, 0, kTimeoutMs);
//		right.config_kD(lowGains, 0, kTimeoutMs);
//		right.config_IntegralZone(lowGains, 0, kTimeoutMs);
		
		/***Magic Settings***/
//		left.configMotionCruiseVelocity(0, kTimeoutMs);
//		left.configMotionAcceleration(0, kTimeoutMs);
//		right.configMotionCruiseVelocity(0, kTimeoutMs);
//		right.configMotionAcceleration(0, kTimeoutMs);
		
	}
	
	public void setNeutralMode(NeutralMode nm) {
		
		left.setNeutralMode(nm);
		right.setNeutralMode(nm);
		
	}
	
	public void setShifter(Value vl) {
		
		shifter.set(vl);
		shifter.set(vl);
		
		left.setSelectedSensorPosition(0, vl == highGear ? highGains : lowGains, kTimeoutMs);
		right.setSelectedSensorPosition(0, vl == highGear ? highGains : lowGains, kTimeoutMs);
		
	}
	
	public void toggleShifter() {
		
		shifter.set(shifter.get() == highGear ? lowGear : highGear);
		
	}
	
	public void driveDirect(double percentOutputLeft, double percentOutputRight) {
		
		left.set(ControlMode.PercentOutput, percentOutputLeft);
		right.set(ControlMode.PercentOutput, percentOutputRight);
		
	}
	
	public void setDistance(double encoderCounts) {
		
		left.set(ControlMode.MotionMagic, encoderCounts);
		right.set(ControlMode.MotionMagic, encoderCounts);
		
	}
	
	public double distanceToRotations(double inches) {
		
		double kInchesPerRotation = 1;
		return inches * kInchesPerRotation;
		
	}
	
}
