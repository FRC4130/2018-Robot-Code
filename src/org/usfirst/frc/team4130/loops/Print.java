package org.usfirst.frc.team4130.loops;

import com.ctre.phoenix.ILoopable;

public class Print implements ILoopable {
	
	private String x = "";
	
	public Print(String y) {
		x = y;
	}
	
	@Override
	public void onStart() {
		System.out.println("[Info] (Print) "+x);
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
	}

}
