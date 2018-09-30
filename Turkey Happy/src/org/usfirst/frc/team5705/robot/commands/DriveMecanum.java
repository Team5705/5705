package org.usfirst.frc.team5705.robot.commands;

import org.usfirst.frc.team5705.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveMecanum extends Command {
	double speed = Robot.chassisSpeed;
	double angle;

    public DriveMecanum() {
    	requires(Robot.drivetrain);
    }

    protected void initialize() {
    }

    protected void execute() {
    	angle = Robot.drivetrain.Gyro();
    	
    	double moveXSpeed = Robot.oi.driverController.getLeftStickX();
    	double moveYSpeed = Robot.oi.driverController.getLeftStickY();
    	double rotateSpeed = Robot.oi.driverController.getRightStickX();
    	
    	Robot.drivetrain.Drive(moveXSpeed, moveYSpeed, rotateSpeed, angle, speed);
    	
    	SmartDashboard.putNumber("Gyro", angle);
    	SmartDashboard.putNumber("chassisSpeed", speed);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.drivetrain.Drive(0, 0, 0, angle, 0);
    }

    protected void interrupted() {
    	end();
    }
}
