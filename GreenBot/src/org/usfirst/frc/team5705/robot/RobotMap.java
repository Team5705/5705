/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5705.robot;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;

public class RobotMap {
	// Motors
	public static final int drivetrain_leftmotor = 8;
	public static final int drivetrain_rightmotor = 9;
	public static final Port drivetrain_gyro = SPI.Port.kOnboardCS0;
	public static final int motor_shooter = 6;
	public static final int motorVentanilla = 7;

	// Joysticks
	public static int joystick_1 = 0;

	// Cameras
	public static final int cam = 0;
	public static final int limitswitch_1 = 0;
	public static final int limitswitch_2 = 1;
}