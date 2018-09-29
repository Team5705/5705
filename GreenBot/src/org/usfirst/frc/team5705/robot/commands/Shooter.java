package org.usfirst.frc.team5705.robot.commands;

import org.usfirst.frc.team5705.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Shooter extends Command {

	public Shooter() {
		requires(Robot.balls);
	}

	protected void initialize() {
	}

	protected void execute() {
		Robot.balls.Shoot(1);
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
