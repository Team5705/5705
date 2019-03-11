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
  WPI_TalonSRX  rightMaster = null;
  WPI_VictorSPX leftMaster = null, leftSlave = null, rightSlave = null;
  // Un giroscopio tipo ADXRS450 por puerto SPI
  ADXRS450_Gyro giroscopio;
  // Declaramos el arreglo del Tren Motriz tipo "DifferentialDrive"
  DifferentialDrive robotDrive;
  // Un valor tipo double de alta precision decimal, para
  double deadband = 0.06;

  //Creamos un metodo o funcion para hacerse ejecutar el tren motriz
  public TrenMotriz(){
    rightMaster = new WPI_TalonSRX(RobotMap.rightMaster);
    leftMaster = new WPI_VictorSPX(RobotMap.lefttMaster);
    leftSlave = new WPI_VictorSPX(RobotMap.leftSlave);
    rightSlave = new WPI_VictorSPX(RobotMap.rightSlave);

    // Configuramos por defecto los controladores para prevenir comportamientos raros
    // Utilizando el metodo creado por CTRE "configFactoryDefault"
    rightMaster.configFactoryDefault();
    leftMaster.configFactoryDefault();
    leftSlave.configFactoryDefault();		
    rightSlave.configFactoryDefault();
    
    // Configuramos los controladores para que no giren los motores invertidos
    leftMaster.setInverted(false);
    rightMaster.setInverted(false);

    //Le damos la orden de que los esclavos sigan a los maestros con el metodo "Follow"
    leftSlave.follow(leftMaster);
    rightSlave.follow(rightMaster);

    // Configuraciones de los sensores en el Tren Motriz
      // Arrancamos el giroscopio en su debido puerto SPI
      // Posteriormente lo reiniciamos para borrar todo dato anterior, y lo calibramos
    giroscopio = new ADXRS450_Gyro(RobotMap.gyro);
    giroscopio.reset();
    giroscopio.calibrate();
  }
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  @Override
  public void initDefaultCommand() {
    // Establecemos el comando por defecto del subsistema aqui
    setDefaultCommand(new Drive());
  }
}
