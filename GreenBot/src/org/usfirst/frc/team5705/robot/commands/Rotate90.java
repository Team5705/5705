package org.usfirst.frc.team5705.robot.commands;

import org.usfirst.frc.team5705.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Rotate90 extends Command {
	
    public Rotate90() {
    	requires(Robot.drivetrain);
    }
    
    protected void initialize() {
    }

    protected void execute() {
    	double angleRobot = Robot.drivetrain.Gyro();
    	SmartDashboard.putNumber("Gyro auto", angleRobot);
		while (angleRobot < 90) {
    		Robot.drivetrain.Drive(0, 0.7, 1);
    	}
    	
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    	Robot.drivetrain.Drive(0, 0, 0);
    }

    protected void interrupted() {
    }
}
