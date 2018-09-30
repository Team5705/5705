package org.usfirst.frc.team5705.robot.commands;

import org.usfirst.frc.team5705.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class DecreaseSpeed extends InstantCommand {
	double Vel = Robot.chassisSpeed;

    public DecreaseSpeed() {
        super();
    }

    protected void initialize() {
    	if (Vel <= 0.0) {
    		Robot.chassisSpeed = 0.0;
    	}
    	else {
    		Robot.chassisSpeed += Vel;
    	}
    }

}
