
package org.usfirst.frc.team5705.robot.subsystems;

import org.usfirst.frc.team5705.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Arm extends Subsystem {
	
	Solenoid arms = null;
	
	public Arm() {
		arms = new Solenoid(RobotMap.solenoid_arm);
	}
	
	public void open() {
		arms.set(true);
	}
	
	public void close() {
		arms.set(false);
	}
	
	public void initDefaultCommand() {
	}
}
