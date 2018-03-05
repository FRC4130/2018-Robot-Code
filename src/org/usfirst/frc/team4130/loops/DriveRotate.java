package org.usfirst.frc.team4130.loops;

import org.usfirst.frc.team4130.subsystem.DriveTrain;

import com.ctre.phoenix.ILoopable;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveRotate implements ILoopable {
	
	DriveTrain drive;
	
	double diff;
	double target;
	double error;
	double lastErr;
	
	double pGain = 0.022;
	double iGain = 0.00235;
	double dGain = 0;
	
	double iZone = 7.5;
	double iAccum = 0;
	
	double acceptableErr = 0.75;
	
	int debounced = 0;
	int debouncedTarget = 25;
	
	double startMS = 0;
	
	public DriveRotate(DriveTrain drv, double dif) {
		
		diff = dif;
		drive = drv;
		
	}

	@Override
	public void onStart() {
		
		startMS = System.currentTimeMillis();
		
		debounced = 0;
		target = drive.getHeading() + diff;
		
		drive.setShifter(drive.lowGear);
		
		System.out.print("Turning ");
		System.out.print(diff);
		System.out.println(" degrees.");
		System.out.print("Current Heading: ");
		System.out.println(drive.getHeading());
		System.out.print("Target Heading: ");
		System.out.println(target);
		
		drive.setNeutralMode(NeutralMode.Brake);
		
	}

	@Override
	public void onLoop() {
		
		error = drive.getHeading()-target;
		
		SmartDashboard.putNumber("Turn Error", error);
		
		if (Math.abs(error) < iZone) {
			
			iAccum = iAccum + error;
			
		}
		else {
			
			iAccum = 0;
			
		}
		
		drive.arcade(0, ( pGain   *   error ) +
					    ( iAccum  *   iGain ) +
					    ( dGain   * ( error - lastErr ) ) );
		
		lastErr = error;
		
	}

	@Override
	public boolean isDone() {
		
		debounced+= Math.abs(error) < acceptableErr ? 1 : -1;
		
		debounced = debounced > debouncedTarget*2  ? debouncedTarget*2 : debounced < 0 ? 0 : debounced;
		
		if (debounced > debouncedTarget) {
			
			System.out.print("Finished Turning in ");
			System.out.print(System.currentTimeMillis()-startMS);
			System.out.print(" milliseconds with an error of ");
			System.out.print(error);
			System.out.println(" degrees.");
			System.out.print("Stopped at position: ");
			System.out.print(drive.getHeading());
			drive.driveDirect(0, 0);
			
		}
		
		SmartDashboard.putNumber("Debounced", debounced);
		
		return debounced > debouncedTarget;
		
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		
		System.out.println("TURNING STOPPED");
		drive.driveDirect(0, 0);
		
	}

}
