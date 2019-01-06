package org.usfirst.frc.team5705.robot.subsystems;

import org.usfirst.frc.team5705.robot.RobotMap;
import org.usfirst.frc.team5705.robot.commands.DriveMecanum;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

/**
 *
 */
public class Drivetrain extends Subsystem {

	ADXRS450_Gyro gyro = null;

	Spark leftFrontMotor = null, rightFrontMotor = null, leftRearMotor = null, rightRearMotor = null;

	MecanumDrive MecanumDrive = null;

	public Drivetrain() {
		gyro = new ADXRS450_Gyro(RobotMap.drivetrain_gyro);
		gyro.reset();
		gyro.calibrate();
		leftFrontMotor = new Spark(RobotMap.drivetrain_leftFrontMotor);
		rightFrontMotor = new Spark(RobotMap.drivetrain_rigthFrontMotor);
		leftRearMotor = new Spark(RobotMap.drivetrain_leftRearMotor);
		rightRearMotor = new Spark(RobotMap.drivetrain_rightRearMotor);

		MecanumDrive = new MecanumDrive(leftFrontMotor, leftRearMotor, rightFrontMotor, rightRearMotor);
	}

	public double Gyro() {
		double angle = gyro.getAngle();
		return angle;
	}

	public void Drive(double moveXSpeed, double moveYSpeed, double rotateSpeed, double gyro, double speed) {
		MecanumDrive.driveCartesian(moveYSpeed * speed, moveXSpeed * speed, rotateSpeed * speed, gyro);
	}

	public void Polar(double magnitude, double angle, double rotation) {
		MecanumDrive.drivePolar(magnitude, angle, rotation);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new DriveMecanum());
	}
}
