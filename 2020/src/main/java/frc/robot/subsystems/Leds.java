/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Leds extends SubsystemBase {
  
  private I2C arduino = new I2C(Port.kOnboard, 0xF5);

  public Leds() {
    sendData(9);

  }

  /********************************************************
   *  Tabla de datos programado para el arduino
   *    0 -> Robot Disable (Rainbow)
   *    1 -> Robot Enable  
   *    2 -> Shoot Enable
   *    3 -> Shoot Disable
   *    4 -> PowerCells Ready
   *    5 -> Rulette Color Red (GameData)
   *    6 -> Rulette Color Yellow (GameData)
   *    7 -> Rulette Color Green (GameData)
   *    8 -> Rulette Color Blue (GameData)
   *    9 -> Badass Mode  
   *    10 -> Begin
   * 
   * 
   *******************************************************/

  public void sendData(int data){
    arduino.write(0xF5, (byte) data);
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("ArduinoConnected?", !arduino.addressOnly());
  }
}
