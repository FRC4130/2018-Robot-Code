package org.usfirst.frc.team4130.loops;

import org.usfirst.frc.team4130.subsystem.DriveTrain;

import com.ctre.phoenix.ILoopable;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * Loopable Class to make the robot rotate on a fixed point by controlling the drive train.
 * @author West
 */
public class DriveRotate implements ILoopable {
	
	DriveTrain drive;
	
	double diff;
	double target;
	double error;
	double lastErr;
	
	double pGain = 0.022;
	double iGain = 0.00225;
	double dGain = 0;
	
	double iZone = 10;
	double iAccum = 0;
	
	double acceptableErr = 0.8;
	
	int debounced = 0;
	int debouncedTarget = 30;
	
	double startMS = 0;
	
	Value originalGear;
	
	public DriveRotate(DriveTrain drv, double dif) {
		
		diff = dif;
		drive = drv;
		
	}
	
	@Override
	public void onStart() {
		
		startMS = System.currentTimeMillis();
		
		debounced = 0;
		target = drive.getHeading() + diff;
		originalGear = drive.getShifter();
		
		drive.setShifter(drive.lowGear);
		
		System.out.print("Turning ");
		System.out.print(diff);
		System.out.print(" degrees. Current Heading: ");
		System.out.print(drive.getHeading());
		System.out.print(". Target Heading: ");
		System.out.println(target);
		
	}
	
	@Override
	public void onLoop() {
		
		error = drive.getHeading() - target;
		
		if (Math.abs(error) < iZone) {
			
			iAccum+= error;
			
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
			
			drive.setShifter(originalGear);
			drive.driveDirect(0, 0);
			System.out.print("Finished Turning in ");
			System.out.print(System.currentTimeMillis()-startMS);
			System.out.print(" milliseconds with an error of ");
			System.out.print(error);
			System.out.print(" degrees. Stopped at position: ");
			System.out.println(drive.getHeading());
			
			
		}
		
		return debounced > debouncedTarget;
		
	}
	
	@Override
	public void onStop() {
		
		System.out.println("TURNING STOPPED");
		drive.setShifter(originalGear);
		
	}

}
