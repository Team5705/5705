
package org.usfirst.frc.team5705.robot.subsystems;

import org.usfirst.frc.team5705.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Arm extends Subsystem {
	
	DoubleSolenoid arms = null;
	
	public Arm() {
		arms = new DoubleSolenoid(RobotMap.solenoid_deploy, RobotMap.solenoid_retract);
	}
	
	public void open() {
		arms.set(Value.kForward);
	}
	
	public void close() {
		arms.set(Value.kReverse);
	}
	
	public void initDefaultCommand() {
	}
}
