/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5705.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team5705.robot.commands.MovePolar;
import org.usfirst.frc.team5705.robot.subsystems.*;

public class Robot extends TimedRobot {
	public static double chassisSpeed;
	public static Drivetrain drivetrain;
	public static Elevator elevator;
	public static Arm arm;
	public static Compressor c;
	public static OI oi;

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	public void robotInit() {
		
		chassisSpeed = 0.7;
		drivetrain = new Drivetrain();
		elevator = new Elevator();
		arm = new Arm();
		oi = new OI();
		c = new Compressor(0);
		chooser.addDefault("Default Auto", new MovePolar(0));
		SmartDashboard.putData("Auto mode", chooser);
		
		UsbCamera cam0 = CameraServer.getInstance().startAutomaticCapture(RobotMap.cam);
		cam0.setFPS(30);
		cam0.setResolution(640, 480);
	}

	public void disabledInit() {

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		if (autonomousCommand != null) {
			autonomousCommand.start();
		}
	}

	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
	}

	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	public void testPeriodic() {
	}
}
