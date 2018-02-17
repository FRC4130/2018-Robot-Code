package org.usfirst.frc.team4130.subsystem;

import org.usfirst.frc.team4130.robot.RobotMap;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * Class for the robot Elevator.
 * @author JCapp
 *
 */
public class Elevator {
	//TODO: Should the elevator contain the object for the intake?  Or should they be completely separate?
	private TalonSRX elevator = RobotMap.elevatorMaster;
	private final int kTimeout = 5;
	//TODO: Determine acceptable position range for elevator set point.
	private final int kPosBandwidth = 10;
	
	/**
	 * Constructor.  Sets all of the configuration parameters for the elevator.
	 */
	public void Elevator(){
		elevator.configAllowableClosedloopError(0, 0, kTimeout);
		
		//Make sure we have full range of motion
		elevator.configPeakOutputForward(1.0, kTimeout);
		elevator.configPeakOutputReverse(-1.0, kTimeout);
		elevator.configNominalOutputForward(0.0, kTimeout);
		elevator.configNominalOutputReverse(0.0, kTimeout);
		
		//Closed-loop Constants
//		TODO: Uncomment once gains are determined
//		elevator.config_kF(0, 0, kTimeout);
//		elevator.config_kP(0, 0, kTimeout);
//		elevator.config_kI(0, 0, kTimeout);
//		elevator.config_kD(0, 0, kTimeout);
//		TODO: Measure Elevator Speed
//		elevator.configMotionAcceleration(0, kTimeout);
//		elevator.configMotionCruiseVelocity(0, kTimeout);
		
		//TODO: Test to see if these need to change
		elevator.setInverted(false);
		elevator.setSensorPhase(false);
		
		//Use Mag Encoder
		elevator.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, kTimeout);
		
		//Forward is up
		elevator.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, kTimeout);
		elevator.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, kTimeout);
		//Zero the position on the bottom limit switch
		elevator.configSetParameter(ParamEnum.eClearPositionOnLimitR, 1, 0, 0, kTimeout);
	}
	/**
	 * Set the height of the elevator (along the chain) 
	 * in inches using the position enum.
	 * @param pos ElevatorPosition Enum
	 * @return True if at position
	 */
	public boolean setHeightInches(ElevatorPosition pos){
		return setHeightInches(pos.value);
	}
	/**
	 * Set the height of the elevator in inches.
	 * @param pos Height along the chain in inches.
	 * @return True if at position
	 */
	public boolean setHeightInches(double inches)
	{
		return setHeight(chainHeightToNative(inches));
	}
	/**
	 * Set the height of the elevator in encoder counts.
	 * @param encoderCounts Height in raw sensor units.
	 * @return True if at position
	 */
	public boolean setHeight(int encoderCounts){
		
		elevator.set(ControlMode.MotionMagic, encoderCounts);
		
		//TODO: Debounce this
		if(elevator.getClosedLoopError(0) < kPosBandwidth)
			return true;
		
		return false;
	}
	/**
	 * Set the speed of the elevator.
	 * @param percentOutput Speed of the elevator from [-1,1].  Positive is up.
	 */
	public void driveDirect(double percentOutput){
		elevator.set(ControlMode.PercentOutput, percentOutput);
	}
	/**
	 * Converts rotations to native units for the elevator encoder (CTRE Mag Encoder).
	 * @param rotations
	 * @return native sensor units
	 */
	private int rotationsToNative(double rotations)
	{
		int kCountsPerRotation = 4096;
		return (int)(rotations * kCountsPerRotation);
	}
	/**
	 * Converts chain height in inches to number of rotations.
	 * @param inches
	 * @return rotations of gearbox output
	 */
	private double chainHeightToRotations(double inches)
	{
		double kInchesPerRotation = 1;
		return inches * kInchesPerRotation;
	}
	/**
	 * Converts chain height to native sensor units of the elevator encoder.
	 * @param inches Height of the Chain
	 * @return native encoder units
	 */
	private int chainHeightToNative(double inches)
	{
		return rotationsToNative(chainHeightToRotations(inches));
	}
	/**
	 * Home the elevator back to zero (all the way down).
	 * @return True if homed.
	 */
	public boolean setHome(){
		//TODO: Adjust output for homing
		elevator.set(ControlMode.PercentOutput, -0.1);
		if(elevator.getSensorCollection().isRevLimitSwitchClosed()) return true;
		return false;
	}
}
