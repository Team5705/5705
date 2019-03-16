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
/* Aqui escribimos con variables el numero ID 
  que ocupara cada controlador, sensor, control de XBOX
  dentro del robot
*/
  // ID's de los controladores del Tren Motriz por CAN
  
  public static final int rightMaster = 1;
  public static final int lefttMaster = 2;
  public static final int rightSlave  = 3;
  public static final int leftSlave   = 4;
  
  //Puertos en los que arrancaran los sensores
  public static final Port gyro = Port.kOnboardCS0;

  //Controladores por PWM (SPARK, Victor, Talon, etc...)


  //Neumatica


  //


  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;
}
