/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class TrenMotriz extends Subsystem {
  // Aqui declaramos todo lo que llevara el tren motriz, controladores y sensores
  WPI_TalonSRX  rightMaster1 = null;
  WPI_VictorSPX leftMaster1 = null,
                leftSlave2 = null,
                rightSlave2 = null;
  ADXRS450_Gyro giroscopio;
  DifferentialDrive robotDrive;
  double deadband = 0.06;

  //Creamos un metodo o funcion para hacerse ejecutar el tren motriz
  public TrenMotriz(){
    rightMaster1 = new WPI_TalonSRX(RobotMap.rightMaster);
    leftMaster1 = new WPI_VictorSPX(RobotMap.lefttMaster);
    leftSlave2 = new WPI_VictorSPX(RobotMap.leftSlave);
    rightSlave2 = new WPI_VictorSPX(RobotMap.rightSlave);
  
  }
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  @Override
  public void initDefaultCommand() {
    // Establecemos el comando por defecto del subsistema aqui
    setDefaultCommand(new Drive());
  }
}
