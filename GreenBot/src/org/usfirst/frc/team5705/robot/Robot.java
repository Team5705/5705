/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5705.robot;

import org.usfirst.frc.team5705.robot.commands.*;
import org.usfirst.frc.team5705.robot.subsystems.*;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
	// public static String gamedata;
	public static double chassisSpeed;
	public static Drivetrain drivetrain;
	public static Balls balls;
	public static Tray tray;

	public static OI oi;

	

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	public void robotInit() {
		chassisSpeed = 0.8;
		drivetrain = new Drivetrain();
		balls = new Balls();
		tray = new Tray();

		oi = new OI();

		chooser.addDefault("Default Auto", null);
		chooser.addObject("My Auto", new MyAutonomous());
		chooser.addObject("La Buena dijo el Frank", null);
		chooser.addObject("GameData", new GameData());
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
		// gamedata = DriverStation.getInstance().getGameSpecificMessage();
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
