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

/**
 * Class to control the drive train subsystem.
 * @author West
 */
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
	private final int highGainsIdx = 0;
	private final int lowGains = 1;
	public final Value highGear = Value.kForward;
	public final Value lowGear = Value.kReverse;
	public double kPosError = 15;
	
	public double turnAcceptableError = 0.75;
	private double turnLowPGain = 0.021;//0.011;
	private double turnHighPGain = 0.00;
	
	/**
	 * Constructor
	 */
	public DriveTrain () {
		
		System.out.println("Drive Init has run.");
		
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
		
		left.set(ControlMode.PercentOutput, 0);
		right.set(ControlMode.PercentOutput, 0);
		
		setNeutralMode(NeutralMode.Brake);
		
		setShifter(highGear);
		
		pigeon.setFusedHeading(0, 10);
		
		/***Motion Magic/speed control***/
		left.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, highGainsIdx, kTimeoutMs);
		left.setSensorPhase(false);
		
		right.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, highGainsIdx, kTimeoutMs);
		right.setSensorPhase(false);
		
		//left.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, lowGains, kTimeoutMs);
		//left.setSensorPhase(true);
		
		//right.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, lowGains, kTimeoutMs);
		//right.setSensorPhase(true);
		
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
		left.config_kF(highGainsIdx, 0.030356, kTimeoutMs);
		left.config_kP(highGainsIdx, 0.087976, kTimeoutMs);
//		left.config_kI(highGains, 0, kTimeoutMs);
//		left.config_kD(HighGains, 0, kTimeoutMs);
//		left.config_IntegralZone(0, 0, kTimeoutMs);
		
//		right.selectProfileSlot(0, 0);
		right.config_kF(highGainsIdx, 0.030356, kTimeoutMs);
		right.config_kP(highGainsIdx, 0.087976, kTimeoutMs);
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
		left.configMotionCruiseVelocity(30330, kTimeoutMs);
		left.configMotionAcceleration(15000, kTimeoutMs);
		
		right.configMotionCruiseVelocity(30330, kTimeoutMs);
		right.configMotionAcceleration(15000, kTimeoutMs);
		
	}
	
	/**
	 * Sets the neutral mode of the drive train to a NeutralMode
	 * @param nm a NeutralMode
	 */
	public void setNeutralMode(NeutralMode nm) {
		
		left.setNeutralMode(nm);
		right.setNeutralMode(nm);
		
	}
	
	/**
	 * Sets the state of the solenoid to a specified Value
	 * @param value a Value Enum
	 */
	public void setShifter(Value value) {
		
		shifter.set(value);
		shifter.set(value);
		
		//left.setSelectedSensorPosition(0, vl == highGear ? highGainsIdx : lowGainsIdx, kTimeoutMs);
		//right.setSelectedSensorPosition(0, vl == highGear ? highGainsIdx : lowGainsIdx, kTimeoutMs);
		
	}
	
	/**
	 * Shifts gears by setting the shifter solenoid to what it is not.
	 */
	public void toggleShifter() {
		
		shifter.set(shifter.get() == highGear ? lowGear : highGear);
		
	}
	
	/**
	 * Controls the voltage of the drive directly
	 * @param percentOutputLeft
	 * @param percentOutputRight
	 */
	public void driveDirect(double percentOutputLeft, double percentOutputRight) {
		
		left.set(ControlMode.PercentOutput, percentOutputLeft);
		right.set(ControlMode.PercentOutput, percentOutputRight);
		
		SmartDashboard.putNumber("Velocity Left",left.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Velocity Right",right.getSelectedSensorVelocity(0));
		
	}
	
	/**
	 * Set the drive to motion magic with the same target
	 * @param nativeUnits target position
	 */
	public void setPosBoth(double encoderCounts) {
		
		left.set(ControlMode.MotionMagic, encoderCounts);
		right.set(ControlMode.MotionMagic, encoderCounts);
		
	}
	
	/**
	 * Sets left drive to motion magic
	 * @param nativeUnits target position
	 */
	public void setPosLeft(double nativeUnits) {
		
		left.set(ControlMode.MotionMagic, nativeUnits);
		
	}
	
	/**
	 * Sets right drive to motion magic
	 * @param nativeUnits target position
	 */
	public void setPosRight(double nativeUnits) {
		
		right.set(ControlMode.MotionMagic, nativeUnits);
		
	}
	
	/**
	 * Converts inches to rotations of the encoder
	 * @param inches a distance to be converted
	 * @return the distance in rotations of the encoder
	 */
	public double distanceToRotations(double inches) {
		
		double kInchesPerRotation = 1;
		return inches * kInchesPerRotation;
		
	}
	
	/**
	 * Converts rotations to native units
	 * @param rotations the number of rotations
	 * @return native units/encoder counts
	 */
	public double rotationsToNative(double rotations) {
		
		double countsPerRotation = 4096;
		return rotations * countsPerRotation;
		
	}
	
	/**
	 * @return the position of the left drive in encoder counts
	 */
	public double getLeftPos() {
		
		return left.getSelectedSensorPosition(0);
		
	}
	
	/**
	 * @return the position of the right drive in encoder counts
	 */
	public double getRightPos() {
		
		return right.getSelectedSensorPosition(0);
		
	}
	
	/**
	 * @return the heading of the robot compared to where it started in degrees
	 */
	public double getHeading() {
		return pigeon.getFusedHeading();
	}
	
	/**
	 * Sets the heading of the pigeon to zero.
	 */
	public void resetHeading() {
		
		pigeon.setFusedHeading(0, kTimeoutMs);
		
	}
	
	/**
	 * Separates the turning throttle and drive throttle
	 * @param throttle The forward (1) and reverse (-1) throttle
	 * @param turn the right (1) and left (-1) throttle
	 */
	public void arcade(double throttle, double turn) {
		
		driveDirect(throttle+turn, throttle-turn);
		
	}
	
	/**
	 * @return the value of the shifter
	 */
	public Value getShifter() {
		
		return shifter.get();
		
	}
	
}
