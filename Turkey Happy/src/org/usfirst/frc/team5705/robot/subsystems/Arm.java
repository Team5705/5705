package org.usfirst.frc.team5705.robot.subsystems;

import org.usfirst.frc.team5705.robot.RobotMap;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Arm extends Subsystem {
	
	Victor rM, lM;

	public Arm() {
		rM = new Victor(RobotMap.arm_rM);
		lM = new Victor(RobotMap.arm_lM);
 
		
	}

	public void arm(double speed) {
		rM.set(speed);
		lM.set(speed);
	}

	public void initDefaultCommand() {
	}
}
