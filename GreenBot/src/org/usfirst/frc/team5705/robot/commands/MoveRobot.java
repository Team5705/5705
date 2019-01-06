package org.usfirst.frc.team5705.robot.commands;

import org.usfirst.frc.team5705.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveRobot extends Command {
	double mov, rotat, spee;
    public MoveRobot(double move, double rotate, double speed) {
        requires(Robot.drivetrain);
        mov = move;
        rotat = rotate;
        spee = speed;
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.drivetrain.Drive(mov, rotat, spee);
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
