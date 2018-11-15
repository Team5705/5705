package org.usfirst.frc.team5705.robot.commands;

import org.usfirst.frc.team5705.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class MovePolar extends Command {
	double b;
	public MovePolar(double a) {
		requires(Robot.drivetrain);
		b = a;
	}

	protected void initialize() {
	}

	protected void execute() {
		Robot.drivetrain.Polar(0.6, 30, b);
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.drivetrain.Polar(0.01, 0, 0);
	}

	protected void interrupted() {
		end();
	}
}
