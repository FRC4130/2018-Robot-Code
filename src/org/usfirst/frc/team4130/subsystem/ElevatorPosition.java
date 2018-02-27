package org.usfirst.frc.team4130.subsystem;

/**
 * Enum to store the Elevator positions in inches.
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
	Switch(19),
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
	ScaleMax(72);
	
	public final double value;
	ElevatorPosition(double initValue)
	{
		this.value = initValue;
	}
};
