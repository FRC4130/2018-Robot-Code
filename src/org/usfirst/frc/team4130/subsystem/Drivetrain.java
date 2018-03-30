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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	public final Value highGear = Value.kForward;
	public final Value lowGear = Value.kReverse;
	public double kPosError = 15;
	double highRampRate = 1;
	double lowRampRate = 1;
	
	public DriveTrain () {
		
		System.out.println("[Info] Drive Init has run.");
		
		left.configAllowableClosedloopError(0, 0, kTimeoutMs);
		left.configAllowableClosedloopError(0, 0, kTimeoutMs);
		
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
		
		pigeon.setFusedHeading(0, 10);
		
		/***Motion Magic/speed control***/
		left.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, kTimeoutMs);
		left.setSensorPhase(false);
		
		right.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, kTimeoutMs);
		right.setSensorPhase(false);
		
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
		
		//Slot 0 Low
		left.selectProfileSlot(0, 0);
		left.config_kF(0, 0.096121, kTimeoutMs);
		left.config_kP(0, 0.12, kTimeoutMs);
		left.config_kI(0, 0, kTimeoutMs);
		left.config_kD(0, 0, kTimeoutMs);
		left.config_IntegralZone(0, 0, kTimeoutMs);
		
		right.selectProfileSlot(0, 0);
		right.config_kF(0, 0.087976, kTimeoutMs);
		right.config_kP(0, 0.075, kTimeoutMs);
		right.config_kI(0, 0, kTimeoutMs);
		right.config_kD(0, 0, kTimeoutMs);
		right.config_IntegralZone(0, 0, kTimeoutMs);
		
		//Slot 1 High
		left.selectProfileSlot(1, 0);
		left.config_kF(1, 0, kTimeoutMs);
		left.config_kP(1, 0, kTimeoutMs);
		left.config_kI(2, 0, kTimeoutMs);
		left.config_kD(2, 0, kTimeoutMs);
		left.config_IntegralZone(0, 0, kTimeoutMs);
		
		right.selectProfileSlot(0, 0);
		right.config_kF(2, 0, kTimeoutMs);
		right.config_kP(2, 0, kTimeoutMs);
		right.config_kI(2, 0, kTimeoutMs);
		right.config_kD(2, 0, kTimeoutMs);
		right.config_IntegralZone(2, 0, kTimeoutMs);
		
		left.set(ControlMode.PercentOutput, 0);
		right.set(ControlMode.PercentOutput, 0);
		
		setNeutralMode(NeutralMode.Coast);
		
		setShifter(highGear);
		
	}
	
	public void setMagicLowDefault() {
		
		left.configMotionCruiseVelocity(8000, kTimeoutMs);
		left.configMotionAcceleration(8000, kTimeoutMs);
		
		right.configMotionCruiseVelocity(8000, kTimeoutMs);
		right.configMotionAcceleration(8000, kTimeoutMs);
		
	}
	
	public void setMagicHighDefault() {
		
		left.configMotionCruiseVelocity(30000, kTimeoutMs);
		left.configMotionAcceleration(15000, kTimeoutMs);
		
		right.configMotionCruiseVelocity(30000, kTimeoutMs);
		right.configMotionAcceleration(15000, kTimeoutMs);
		
	}
	
	public void setMagic(int cruiseVelocity, int acceleration) {
		
		left.configMotionCruiseVelocity(cruiseVelocity, kTimeoutMs);
		left.configMotionAcceleration(acceleration, kTimeoutMs);
		
		right.configMotionCruiseVelocity(cruiseVelocity, kTimeoutMs);
		right.configMotionAcceleration(acceleration, kTimeoutMs);
		
	}
	
	public void putDash() {
//		
//		SmartDashboard.putNumber("Left Vel", left.getSelectedSensorVelocity(0));
//		SmartDashboard.putNumber("Left Tarjectory Vel", left.getActiveTrajectoryVelocity());
//		SmartDashboard.putNumber("Left Tarjectory Pos",left.getActiveTrajectoryPosition());
//		SmartDashboard.putNumber("Left Target Pos", left.getClosedLoopTarget(0));
//		SmartDashboard.putNumber("Left Pos", left.getSelectedSensorPosition(0));
//		SmartDashboard.putNumber("Left Closed loop error", left.getClosedLoopError(0));
//		
//		SmartDashboard.putNumber("Right Vel", right.getSelectedSensorVelocity(0));
//		SmartDashboard.putNumber("Right Tarjectory Vel", right.getActiveTrajectoryVelocity());
//		SmartDashboard.putNumber("Right Tarjectory Pos",right.getActiveTrajectoryPosition());
//		SmartDashboard.putNumber("Right Target Pos", right.getClosedLoopTarget(0));
//		SmartDashboard.putNumber("Right Pos", right.getSelectedSensorPosition(0));
//		SmartDashboard.putNumber("Right Closed loop error", right.getClosedLoopError(0));
//		
	}
	
	public double inchesToNative(double inches) {
		
		return ( ( 286899 * inches ) / 169.5 );
		
	}
	
	public void setNeutralMode(NeutralMode nm) {
		
		left.setNeutralMode(nm);
		right.setNeutralMode(nm);
		
	}
	
	public void setShifter(Value vl) {
		
		shifter.set(vl);
		shifter.set(vl);
		
		System.out.print("[Info] Shifted to ");
		System.out.println(vl == highGear ? "High" : "Low");
		
		double secondsFromNeutralToFull;
		
		if (vl == highGear) {
			
			right.selectProfileSlot(1, 0);
			left.selectProfileSlot(1, 0);
			
			setMagicHighDefault();
			
			secondsFromNeutralToFull = highRampRate;
			
		}
		
		else {
			
			right.selectProfileSlot(0, 0);
			left.selectProfileSlot(0, 0);
			
			setMagicLowDefault();
			
			secondsFromNeutralToFull = lowRampRate;
			
		}
		
		System.out.print("[Info] Set ramp rate to ");
		System.out.println(secondsFromNeutralToFull);
		
		right.configOpenloopRamp(secondsFromNeutralToFull, kTimeoutMs);
		left.configOpenloopRamp(secondsFromNeutralToFull, kTimeoutMs);
		
	}
	
	public void toggleShifter() {
		
		shifter.set(shifter.get() == highGear ? lowGear : highGear);
		
	}
	
	public void driveDirect(double percentOutputLeft, double percentOutputRight) {
		
		left.set(ControlMode.PercentOutput, percentOutputLeft);
		right.set(ControlMode.PercentOutput, percentOutputRight);
		
	}
	
	public void setPosLeft(double nativeUnits) {
		
		left.set(ControlMode.MotionMagic, nativeUnits);
		
	}
	
	public void setPosRight(double nativeUnits) {
		
		right.set(ControlMode.MotionMagic, nativeUnits);
		
	}
	
//	public double distanceToRotations(double inches) {
//		
//		double kInchesPerRotation = 1;
//		return inches * kInchesPerRotation;
//		
//	}
	
	public double getLeftPos() {
		
		return left.getSelectedSensorPosition(0);
		
	}
	
	public double getRightPos() {
		
		return right.getSelectedSensorPosition(0);
		
	}

	public double getHeading() {
		
		return pigeon.getFusedHeading();
		
	}
	
	public void resetHeading() {
		
		pigeon.setFusedHeading(0, kTimeoutMs);
		
	}
	
	public void arcade(double throttle, double turn) {
		
		driveDirect(throttle+turn, throttle-turn);
		
	}

	public Value getShifter() {
		
		return shifter.get();
		
	}
	
	public void resetSensors() {
		
		right.setSelectedSensorPosition(0, 0, 0);
		left.setSelectedSensorPosition(0, 0, 0);
		
	}
	
}
