package org.usfirst.frc.team4130.subsystem;

/**
 * Enum for the Elevator positions.
 * @authors JCapp, West
 *
 */
public enum ElevatorPosition {
	/**
	 * Height of the "home" position.
	 * This should always be zero (since it's at the limit switch)
	 */
	Home(0),
	/**
	 * Height to put a power cube into the exchange.
	 */
	Exchange(20),
	/**
	 * Height we want to drive around at.
	 */
	Travel(3),
	/**
	 * Height to place a cube on the switch.
	 */
	Switch(22),
	/**
	 * Height to receive a cube from the portal.
	 * This may not be practical, need to test.
	 */
	Portal(14),
	/**
	 * Height to place a cube on a balanced/owned scale.
	 */
	Scale(60),
	/**
	 * Height to place a cube on a scale that's not owned (so we need to go higher).
	 */
	ScaleMax(73),
	/**
	 * Height where we should change the ramp rate
	 */
	MaxStable(73);
	
	public final double value;
	ElevatorPosition(double initValue)
	{
		this.value = initValue;
	}
};
