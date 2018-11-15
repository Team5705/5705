package org.usfirst.frc.team5705.robot.commands;

import org.usfirst.frc.team5705.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DecreaseSpeed extends Command {
	double Vel = Robot.chassisSpeed;

    public DecreaseSpeed() {
    }

    protected void initialize() {
    	if (Vel <= 1.0) {
			Robot.chassisSpeed = 0.0;
		} else {
			Robot.chassisSpeed -= 0.1;
		}
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    	end();
    }
}
