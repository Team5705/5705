package org.usfirst.frc.team5705.robot.commands;

import org.usfirst.frc.team5705.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TakeCube extends Command {

	public TakeCube() {
		requires(Robot.arm);
	}

	protected void initialize() {
	}

	protected void execute() {
		Robot.arm.arm(-0.7);
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.arm.arm(0);
	}

	protected void interrupted() {
		end();
	}
}
