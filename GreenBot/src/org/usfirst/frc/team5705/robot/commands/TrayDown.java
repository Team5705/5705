package org.usfirst.frc.team5705.robot.commands;

import org.usfirst.frc.team5705.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TrayDown extends Command {

    public TrayDown() {
    	requires(Robot.tray);
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.tray.Speed(-1);
    }

    protected boolean isFinished() {
        return Robot.tray.isSwitch2Set();
    }

    protected void end() {
    	Robot.tray.Speed(0);
    }

    protected void interrupted() {
    	end();
    }
}
