package org.usfirst.frc.team5705.robot.subsystems;

import org.usfirst.frc.team5705.robot.RobotMap;
import org.usfirst.frc.team5705.robot.commands.DriveArcade;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class Drivetrain extends Subsystem {

	ADXRS450_Gyro gyro = null;

	Spark leftmotor = null;
	Spark rightmotor = null;

	DifferentialDrive Robotdrive = null;

	public Drivetrain() {
		gyro = new ADXRS450_Gyro(RobotMap.drivetrain_gyro);
		gyro.reset();
		gyro.calibrate();
		leftmotor = new Spark(RobotMap.drivetrain_leftmotor);
		rightmotor = new Spark(RobotMap.drivetrain_rightmotor);

		Robotdrive = new DifferentialDrive(leftmotor, rightmotor);
	}

	public void Drive(double moveSpeed, double rotateSpeed, double speed) {
		Robotdrive.arcadeDrive(moveSpeed * speed, rotateSpeed * speed);
	}

	public double Gyro() {
		return gyro.getAngle();
	}

	public void initDefaultCommand() {
		setDefaultCommand(new DriveArcade());

	}
}
