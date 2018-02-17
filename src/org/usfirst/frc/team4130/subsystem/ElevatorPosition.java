package org.usfirst.frc.team4130.subsystem;

/**
 * Enum for the Elevator positions.
 * @author JCapp
 *
 */
public enum ElevatorPosition {
	//TODO: Determine these positions
	/**
	 * Height of the "home" position.
	 * This should always be zero (since it's at the limit switch)
	 */
	Home(0),
	/**
	 * Height to put a power cube into the exchange.
	 */
	Exchange(0),
	/**
	 * Height we want to drive around at.
	 */
	Travel(0),
	/**
	 * Height to place a cube on the switch.
	 */
	Switch(0),
	/**
	 * Height to receive a cube from the portal.
	 * This may not be practical, need to test.
	 */
	Portal(0),
	/**
	 * Height to place a cube on a balanced/owned scale.
	 */
	Scale(0),
	/**
	 * Height to place a cube on a scale that's not owned (so we need to go higher).
	 */
	ScaleMax(0);
	
	public final int value;
	ElevatorPosition(int initValue)
	{
		this.value = initValue;
	}
};
