package org.usfirst.frc.team5705.robot.commands;

import org.usfirst.frc.team5705.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveArcade extends Command {
	double speed = Robot.chassisSpeed;

	public DriveArcade() {
		requires(Robot.drivetrain);
	}

	// Llamado justo antes de que este comando se ejecute la primera vez.
	protected void initialize() {
	}

	// Llamado repetidamente cuando este comando está programado para ejecutarse
	protected void execute() {
		double moveSpeed = -Robot.oi.driverController.getLeftStickY();
		double rotateSpeed = Robot.oi.driverController.getRightStickX();
		
		Robot.drivetrain.Drive(moveSpeed, rotateSpeed, speed);
		SmartDashboard.putNumber("chassisSpeed", speed);
		SmartDashboard.putNumber("Gyro", Robot.drivetrain.Gyro());
	}

	// Haga que este retorno sea verdadero cuando este comando ya no necesite
	// ejecutar execute ()
	protected boolean isFinished() {
		return false;
	}

	// Llamado una vez después de isFinished devuelve true
	protected void end() {
		Robot.drivetrain.Drive(0, 0, 0);
	}

	// Se llama cuando otro comando que requiere uno o más de los mismos
	// subsistemas está programado para ejecutarse
	protected void interrupted() {
		end();
	}
	
}
