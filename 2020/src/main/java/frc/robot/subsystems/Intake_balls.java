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

/**
 * Aqui se declara los componentes del Intake
 */
public class Intake_balls extends SubsystemBase {
  WPI_VictorSPX intake = new WPI_VictorSPX(Intake.m1); //Intake
  WPI_VictorSPX motorBandaA = new WPI_VictorSPX(Intake.m1); //Banda A (Bufer)
  WPI_VictorSPX motorBandaB = new WPI_VictorSPX(Intake.m1); //Banda B


  DigitalInput sensor1 = new DigitalInput(Intake.sensors[0]), //Sensor Bufer
               sensor2 = new DigitalInput(Intake.sensors[1]),
               sensor3 = new DigitalInput(Intake.sensors[2]),
               sensor4 = new DigitalInput(Intake.sensors[3]); //Sensor de salida
  
  Intake_balls() {
    //Configurar como defecto los controladores
    intake.configFactoryDefault();
    motorBandaA.configFactoryDefault();
    motorBandaB.configFactoryDefault();

    intake.setInverted(false); //Asignar si es invertido o no
  }

  public void takeBalls(){
    boolean s1 = !sensor1.get();
    boolean s2 = !sensor2.get();
    boolean s3 = !sensor3.get();
    boolean s4 = !sensor4.get();

    /**
     * Para la toma de decisiones de esta logica se tomo como referencia una tabla de verdad para 
     * los casos posibles y redundantes
     */
    if (s1 == true && s4 == true){ //1--1
      //Se detienen las bandas
      //Prender Led de control (Listo para tirar)
      intake.set(1);
      motorBandaA.set(0);
      motorBandaB.set(0);
    } 

    else if (s1 == false && s4 == true){ // 0--1
     intake.set(1);
     motorBandaA.set(1);
     motorBandaB.set(0);
    }

    else if (s1 == false && s2 == false && s3 == false && s4 == false){//0000
      intake.set(1);
      motorBandaA.set(1);
      motorBandaB.set(0);
    }

    else if(s1 == true && s2 == false && s3 == false && s4 == false){//1000
      intake.set(1);
      motorBandaA.set(1);
      motorBandaB.set(1);
    }

    else if(s1 == false && s2 == true && s4 == false){//} s3 == false && s4 == false){
      //0100
      intake.set(1);
      motorBandaA.set(1);
      motorBandaB.set(0);
    }

    else if(s1 == false && s2 == false && s3 == true && s4 == false){//0010
      intake.set(1);
      motorBandaA.set(1);
      motorBandaB.set(0);
    }

    else if(s1 == true && s2 == false && s3 == true && s4 == false){//1010
      intake.set(1);
      motorBandaA.set(1);
      motorBandaB.set(1);
    }

    else if (s1 == true && s2 == true ){//&& s3 == false && s4 == false){//1100
      intake.set(1);
      motorBandaA.set(1);
      motorBandaB.set(1);
    }
    else {
      intake.set(0);
      motorBandaA.set(0);
      motorBandaB.set(0);
    }

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
