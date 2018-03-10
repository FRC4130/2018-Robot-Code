package org.usfirst.frc.team4130.loops;

import org.usfirst.frc.team4130.subsystem.Elevator;

import com.ctre.phoenix.ILoopable;

public class SetHome implements ILoopable {
	
	Elevator ele;
	
	public SetHome(Elevator _ele) {
		ele = _ele;
	}
	
	@Override
	public void onStart() {
		System.out.println("Setting Home.");
	}

	@Override
	public void onLoop() {
		ele.setHome();
	}

	@Override
	public boolean isDone() {
		if (ele.setHome()) {
			System.out.println("Set Home.");
			return true;
		}
		return false;
	}

	@Override
	public void onStop() {
		System.out.println("[WARNING] SetHome has been stopped!");
	}

}
