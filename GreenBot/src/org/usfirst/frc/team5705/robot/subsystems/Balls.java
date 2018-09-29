package org.usfirst.frc.team5705.robot.subsystems;

import org.usfirst.frc.team5705.robot.RobotMap;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Balls extends Subsystem {
	
	Talon shooter = null;
	
	public Balls() {
		shooter = new Talon(RobotMap.motor_shooter);
	}
	
	public void Shoot(double speed) {
		shooter.set(speed);
	}

	protected void initDefaultCommand() {
	}
}

