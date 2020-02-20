/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  WPI_VictorSPX shoot =  new WPI_VictorSPX(8);

  public Shooter() {
    shoot.configFactoryDefault();
    shoot.setInverted(false);

  }

  public void go(){
    shoot.setVoltage(10); //Voltage de salida 10v
  }

  public void goPID(double voltage){
    if(voltage >= 10) voltage = 10;
    else if(voltage <= 0) voltage = 0;
    shoot.setVoltage(voltage);
  }

  @Override
  public void periodic() {
    
  }
}
