/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5705.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	public static Talon disparador;
	public static Spark MotorDer;
	public static Spark MotorIzq;
	public static DifferentialDrive Chasis;
	public static ADXRS450_Gyro gyroc;
	/*
	public static void speedTren() {
	new double SpeedValue = 0;
	}
	*/
	public static void init(){
	gyroc = new ADXRS450_Gyro();
	gyroc.calibrate();
	MotorIzq = new Spark(0);
	MotorDer = new Spark(1);
	disparador = new Talon(2);
	Chasis = new DifferentialDrive(MotorIzq, MotorDer);
	
	}

}