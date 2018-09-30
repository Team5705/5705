package org.usfirst.frc.team5705.robot.commands;

import org.usfirst.frc.team5705.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class IncreaseSpeed extends InstantCommand {
	double Vel = Robot.chassisSpeed;

    public IncreaseSpeed() {
        super();
    }

    protected void initialize() {
    	if (Vel >= 1.0) {
    		Robot.chassisSpeed = 1.0;
    	}
    	else {
    		Robot.chassisSpeed += Vel;
    	}
    }

}
