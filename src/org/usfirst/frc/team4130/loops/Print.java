package org.usfirst.frc.team4130.loops;

import com.ctre.phoenix.ILoopable;

/**
 *   This Class provides a way to schedule
 * prints and debug autonomous.
 * @author West
 */
public class Print implements ILoopable {
	
	private String x = "";
	
	public Print(String y) {
		x = y;
	}
	
	@Override
	public void onStart() {
		System.out.println(x);
	}

	@Override
	public void onLoop() {

	}

	@Override
	public boolean isDone() {
		return true;
	}

	@Override
	public void onStop() {
		System.out.println(x);
	}

}
