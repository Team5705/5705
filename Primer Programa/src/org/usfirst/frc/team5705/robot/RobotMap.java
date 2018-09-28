/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5705.robot;

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
	static Talon disparador;
	static Spark MotorDer;
	static Spark MotorIzq;
	public static DifferentialDrive Chasis;
	/*
	public static void speedTren() {
	new double SpeedValue = 0;
	}
	*/
	public static void init(){
	MotorIzq = new Spark(0);
	MotorDer = new Spark(1);
	disparador = new Talon(3);
	Chasis = new DifferentialDrive(MotorIzq, MotorDer);
	
	}	

}