package org.usfirst.frc.team4130.subsystem;

import org.usfirst.frc.team4130.robot.RobotMap;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Servo;

/**
 * Class for the robot Elevator.
 * @author JCapp, West
 *
 */
public class Elevator {
	private TalonSRX elevator = RobotMap.elevatorMaster;
	private Servo servo = RobotMap.elevatorReleaseServo;
	private final int kTimeout = 5;
	private final int kPosBandwidth = 10;
	
	double targetHeightNativeUnits = 0;
	
	/**
	 * Constructor.  Sets all of the configuration parameters for the elevator.
	 */
	public Elevator(){
		elevator.configAllowableClosedloopError(0, 0, kTimeout);
		
		//Make sure we have full range of motion
		elevator.configPeakOutputForward(1.0, kTimeout);
		elevator.configPeakOutputReverse(-1.0, kTimeout);
		elevator.configNominalOutputForward(0.0, kTimeout);
		elevator.configNominalOutputReverse(0.0, kTimeout);
		
		//Up Gains
		elevator.config_kF(0, 0.6959, kTimeout);
		elevator.config_kP(0, 0.7, kTimeout);
		
		//Down Gains
		elevator.selectProfileSlot(1, 0);
		elevator.config_kF(1, 0.6959, kTimeout);
		elevator.config_kP(1, 0.7, kTimeout);
		elevator.config_kI(1, 0, kTimeout);
		elevator.config_kD(1, 0, kTimeout);
		elevator.config_IntegralZone(0, 0, kTimeout);
		
		//Magic Shit
		elevator.configMotionAcceleration(1322, kTimeout);
		elevator.configMotionCruiseVelocity(1322, kTimeout);
		
		//TODO: Test to see if these need to change
		elevator.setInverted(false);
		elevator.setSensorPhase(false);
		
		//Use Mag Encoder
		elevator.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, kTimeout);
		
		//Forward is up
		elevator.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, kTimeout);
		elevator.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, kTimeout);
		//Zero the position on the bottom limit switch
		elevator.configSetParameter(ParamEnum.eClearPosOnLimitR, 1, 0, 0, kTimeout);
		
		//Current Limiting to protect the 775pro
		//Current setup: no more than 3 seconds at stall before limiting.
		//TODO: Test the current limit
		elevator.configPeakCurrentLimit(30, kTimeout);
		elevator.configPeakCurrentDuration(3000, kTimeout);
		elevator.configContinuousCurrentLimit(5, kTimeout);
		elevator.enableCurrentLimit(true);
		
		//soft limits
		elevator.configReverseSoftLimitThreshold((int) chainHeightToNative(ElevatorPosition.ReverseSoftLimit.value), kTimeout);
		elevator.configReverseSoftLimitEnable(true, kTimeout);
		elevator.configForwardSoftLimitThreshold((int) chainHeightToNative(ElevatorPosition.ForwardSoftLimit.value), kTimeout);
		elevator.configForwardSoftLimitEnable(true, kTimeout);
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
	 * @param value Height in raw sensor units.
	 * @return True if at position
	 */
	public boolean setHeight(double valueNativeUnits) {
		
		if (valueNativeUnits > targetHeightNativeUnits) {
			elevator.configMotionAcceleration(2000, kTimeout);
			elevator.configMotionCruiseVelocity(1500, kTimeout);
		}
		
		else if (valueNativeUnits < targetHeightNativeUnits) {
			elevator.configMotionAcceleration(6000, kTimeout);
			elevator.configMotionCruiseVelocity(1400*6, kTimeout);
		}
		
		targetHeightNativeUnits = valueNativeUnits;
		
		elevator.set(ControlMode.MotionMagic, valueNativeUnits);
		
		//TODO: Debounce this
		if(elevator.getClosedLoopError(0) < kPosBandwidth) {
			return true;
		}
		
		return false;
		
	}
	/**
	 * Set the speed of the elevator.
	 * @param percentOutput Speed of the elevator from [-1,1].  Positive is up.
	 */
	public void driveDirect(double percentOutput) {
		elevator.set(ControlMode.PercentOutput, percentOutput);
	}
	/**
	 * Converts Inches to Native units
	 * @param Height in inches.
	 * @return Rotations in NativeUnits required to reach height.
	 */
	public double chainHeightToNative(double inches) {
		return (inches/79)*36764;
	}
	public double nativeToChainHeight(double n) {
		return (n/36764)*79;
	}
	/**
	 * Home the elevator back to zero (all the way down).
	 * @return True if homed.
	 */
	public boolean setHome() {
		elevator.set(ControlMode.PercentOutput, -0.075);
		if(elevator.getSensorCollection().isRevLimitSwitchClosed()) {
			elevator.setSelectedSensorPosition(0, 0, kTimeout);
			return true;
		}
		return false;
	}
	
	public double getError() {
		
		return elevator.getSelectedSensorPosition(0)-targetHeightNativeUnits;
		
	}
	
	public void setServo(boolean a) {
		servo.set(a ? 1 : 0);
	}
	public double getCurrent() {
		return elevator.getOutputCurrent();
	}
	public double getPos() {
		return elevator.getSelectedSensorPosition(0);
	}
	public double getHeight() {
		return nativeToChainHeight(getPos());
	}
	public double getTarget() {
		return elevator.getClosedLoopTarget(0);
	}
	public double getTargetHeight() {
		return nativeToChainHeight(getTarget());
	}
}
