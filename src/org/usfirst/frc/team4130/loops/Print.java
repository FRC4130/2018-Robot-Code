package org.usfirst.frc.team4130.loops;

import com.ctre.phoenix.ILoopable;

public class Print implements ILoopable {
	
	private String x = "";
	
	public Print(String y) {
		x = y;
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		System.out.println(x);
	}

	@Override
	public void onLoop() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub

	}

}
