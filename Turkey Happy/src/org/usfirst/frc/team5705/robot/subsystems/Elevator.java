package org.usfirst.frc.team5705.robot.subsystems;

import org.usfirst.frc.team5705.robot.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Elevator extends Subsystem {

	Spark rM, lM;

	public Elevator() {
		rM = new Spark(RobotMap.elevator_rM);
		lM = new Spark(RobotMap.elevator_lM);
	}
	
	public void Elev(double speed) {
		rM.set(speed);
		lM.set(speed);
	}

	public void initDefaultCommand() {
		
	}
}
