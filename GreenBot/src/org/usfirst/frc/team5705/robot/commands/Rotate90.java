package org.usfirst.frc.team5705.robot.commands;

import org.usfirst.frc.team5705.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Rotate90 extends Command {
	double angleRobot;
	
    public Rotate90() {
    	requires(Robot.drivetrain);
    }
    
    protected void initialize() {
    }

    protected void execute() {
    	angleRobot = Robot.drivetrain.Gyro();
    	SmartDashboard.putNumber("Gyro auto", angleRobot);
    	
    	Robot.drivetrain.Drive(0, 0.7, 1);
    	
    }

    protected boolean isFinished() {
        if (angleRobot >= 90) { 
        	return true;
        }
		return false;
    }

    protected void end() {
    	Robot.drivetrain.Drive(0, 0, 0);
    }

    protected void interrupted() {
    }
}
