package org.usfirst.frc.team4130.loops;

import org.usfirst.frc.team4130.robot.Subsystems;
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
	
	double pGain = 0.0125;   //.015;
	double iGain = 0.0011;       //0.001;		//0.005;    //.001;
	double dGain = 0.01;       //.001;		//.03;    //.025;
	
	double iZone = 10;
	double iAccum = 0;
	
	double acceptableErr = 3.5;
	
	int debounced = 0;
	
	int debouncedTarget = 15;
	
	double maxThrottle = 1;
	
	double startMS = 0;
	
	@Deprecated
	public DriveRotate(DriveTrain drv, double dif) {
		
		diff = dif;
		drive = drv;
		
	}
	
	public DriveRotate(double dif) {
		
		diff = dif;
		drive = Subsystems.driveTrain;
		
	}
	
	public DriveRotate(double dif, double acceptableError, int debouncedtarget) {
		
		diff = dif;
		drive = Subsystems.driveTrain;
		debouncedTarget = debouncedtarget;
		acceptableErr = acceptableError;
		
	}

	@Override
	public void onStart() {
		
		System.out.println("[Info] Started Drive Rotate");
			
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
		
		double turnThrottle =	( pGain   *   error ) +
			    				( iAccum  *   iGain ) +
			    				( dGain   * ( error - lastErr ) );
		
		turnThrottle = turnThrottle > maxThrottle ? maxThrottle : turnThrottle < maxThrottle*-1 ? maxThrottle*-1 : turnThrottle;
		
		drive.arcade(0, turnThrottle );
		
		lastErr = error;
		
	}

	@Override
	public boolean isDone() {
		
		debounced+= Math.abs(error) < acceptableErr ? 1 : -1;
		
		debounced = debounced > debouncedTarget  ? debouncedTarget : debounced < 0 ? 0 : debounced;
		
		if (debounced > debouncedTarget) {
			
			System.out.print("[Info] Finished Turning in ");
			System.out.print(System.currentTimeMillis()-startMS);
			System.out.print(" milliseconds with an error of ");
			System.out.print(error);
			System.out.println(" degrees.");
			drive.driveDirect(0, 0);
			
		}
		
		return debounced >= debouncedTarget;
		
	}

	@Override
	public void onStop() {
		
		System.out.println("[WARNING] Drive Rotate has been stopped");
		drive.driveDirect(0, 0);
		
	}

}
