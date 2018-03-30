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
public class ElevatorEmulator extends Elevator {
	
	private double pos = 0;
	
	double targetHeight = 0;
	
	/**
	 * Constructor.  Sets all of the configuration parameters for the elevator.
	 */
	public ElevatorEmulator(){
		
		
		
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
		
		pos = valueNativeUnits;
		
		return true;
		
	}
	/**
	 * Set the speed of the elevator.
	 * @param percentOutput Speed of the elevator from [-1,1].  Positive is up.
	 */
	public void driveDirect(double percentOutput) {
		
	}
	/**
	 * Converts Inches to Native units
	 * @param Height in inches.
	 * @return Rotations in NativeUnits required to reach height.
	 */
	public double chainHeightToNative(double inches) {
		return (inches/79)*36764;
	}
	/**
	 * Home the elevator back to zero (all the way down).
	 * @return True if homed.
	 */
	public boolean setHome() {
		return true;
	}
	
	public double getError() {
		
		return 0;
		
	}
	
	public void setServo(boolean a) {
		System.out.print("SET SERVO TO ");
		System.out.println(a ? 1 : 0);
	}
	public double getCurrent() {
		return 0;
	}
	public double getPos() {
		return pos;
	}
}
