package org.usfirst.frc.team5705.robot.commands;

import org.usfirst.frc.team5705.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveArcade extends Command {
	double speed = Robot.chassisSpeed;

	public DriveArcade() {
		requires(Robot.drivetrain);
	}

	protected void initialize() {
	}

	protected void execute() {
		double moveSpeed = -Robot.oi.driverController.getRawAxis(1);
		double rotateSpeed = Robot.oi.driverController.getRawAxis(4);
		
		Robot.drivetrain.Drive(moveSpeed, rotateSpeed, speed);
		SmartDashboard.putNumber("chassisSpeed", speed);
		SmartDashboard.putNumber("Gyro", Robot.drivetrain.Gyro());
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.drivetrain.Drive(0, 0, 0);
	}

	protected void interrupted() {
		end();
	}
	
}
