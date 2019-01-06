package org.usfirst.frc.team5705.robot.commands;

import org.usfirst.frc.team5705.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorDown extends Command {

    public ElevatorDown() {
    	requires(Robot.elevator);
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.elevator.Elev(-0.45);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.elevator.Elev(0);
    }

    protected void interrupted() {
    	end();
    }
}
