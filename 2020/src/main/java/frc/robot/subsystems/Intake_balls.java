/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Intake;

public class Intake_balls extends SubsystemBase {
  WPI_VictorSPX paquito = new WPI_VictorSPX(Intake.m1);

  DigitalInput sensor1 = new DigitalInput(Intake.sensors[0]),
               sensor2 = new DigitalInput(Intake.sensors[1]);
  
  Intake_balls() {
    paquito.configFactoryDefault();
  }

  public void takeBalls(){
    boolean s1 = sensor1.get();
    boolean s2 = sensor1.get();

    if (s1 == false && s2 == false){              //00

    } else if (s1 == false && s2 == true) {       //01
      
    } else if (s1 == true && s2 == false) {       //10
      
    } else if (s1 == true && s2 == true) {        //11
      
    } else {
      
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
