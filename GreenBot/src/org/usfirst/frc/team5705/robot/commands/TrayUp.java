package org.usfirst.frc.team5705.robot.commands;

import org.usfirst.frc.team5705.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TrayUp extends Command {

    public TrayUp() {
    	requires(Robot.tray);
    }

    protected void initialize() {

    }

    protected void execute() {
    	Robot.tray.Speed(1);
    }

    protected boolean isFinished() {
        return Robot.tray.isSwitch1Set();
    }

    protected void end() {
    	Robot.tray.Speed(0);
    }

    protected void interrupted() {
    	end();
    }
}
