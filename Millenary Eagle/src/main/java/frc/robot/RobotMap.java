/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.SPI.Port;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  //Motors CAN-Bus
	public static final int powertrain_leftmotor1 = 6;
	public static final int powertrain_leftmotor2 = 7;
	public static final int powertrain_rightmotor1 = 2;
  public static final int powertrain_rightmotor2 = 5;

	public static final int cargogripper_motor = 8;
	public static final int cargogripper_motor2 = 8;
  public static final int cargogripper_motormove = 4;
  public static final int elevatorleftmotor = 3;

  //Sparks
  public static final int elevatorrightmotor = 0;

  //Solenoids
  public static final int hatchpgripper = 0;
  public static final int hatchpgripper_move = 1;
  public static final int jumper_front = 2;
  public static final int jumper_rear = 3;
  
  //Sensors
  public static final Port gyro = Port.kOnboardCS0;
  public static final int _lim1 = 1; //Limit Down
  public static final int _lim2 = 2; //Limit Up
  public static final int _lim3 = 0; //LimitCargo
  

  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;
}
