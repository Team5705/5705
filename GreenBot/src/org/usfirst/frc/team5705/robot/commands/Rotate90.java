package org.usfirst.frc.team5705.robot.commands;

import org.usfirst.frc.team5705.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Rotate90 extends Command {
	boolean finished;
	
    public Rotate90() {
    	requires(Robot.drivetrain);
    }
    
    protected void initialize() {
    }

    protected void execute() {
    	double angleRobot = Robot.drivetrain.Gyro();
		while (angleRobot <= 90) {
    		Robot.drivetrain.Drive(0, 1, 1);
    	}
    	finished = true;
    }

    protected boolean isFinished() {
        return finished;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
