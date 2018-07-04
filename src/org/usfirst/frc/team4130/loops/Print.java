package org.usfirst.frc.team4130.loops;

import com.ctre.phoenix.ILoopable;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Print implements ILoopable {
	
	private String x = "";
	
	public Print(String y) {
		x = y;
	}
	
	@Override
	public void onStart() {
		String toPrint = x;
		System.out.println("[Print] "+toPrint);
		SmartDashboard.putString("[Print]", toPrint);
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
