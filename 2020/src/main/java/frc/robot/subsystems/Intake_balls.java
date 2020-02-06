/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Intake;

/**
 * Aqui se declara los componentes del Intake
 */
public class Intake_balls extends SubsystemBase {
  private WPI_VictorSPX intake = new WPI_VictorSPX(Intake.m1); //Intake
  private WPI_VictorSPX motorBandaA = new WPI_VictorSPX(Intake.m1); //Banda A (Bufer)
  private WPI_VictorSPX motorBandaB = new WPI_VictorSPX(Intake.m1); //Banda B

  private Solenoid pistonA = new Solenoid(Intake.solenoids[0]);
  private Solenoid pistonB = new Solenoid(Intake.solenoids[1]);

  private DigitalInput sensor1 = new DigitalInput(Intake.sensors[0]), //Sensor Bufer
                       sensor2 = new DigitalInput(Intake.sensors[1]),
                       sensor3 = new DigitalInput(Intake.sensors[2]),
                       sensor4 = new DigitalInput(Intake.sensors[3]); //Sensor de salida
  
  Intake_balls() {
    //Configurar como defecto los controladores
    intake.configFactoryDefault();
    motorBandaA.configFactoryDefault();
    motorBandaB.configFactoryDefault();
    
    //Asignar si es invertido o no
    intake.setInverted(false);
    motorBandaA.setInverted(false);
    motorBandaB.setInverted(false);
  }

  public void takeBalls(){
    boolean s1 = !sensor1.get();
    boolean s2 = !sensor2.get();
    boolean s3 = !sensor3.get();
    boolean s4 = !sensor4.get();

    /**
     * Para la toma de decisiones de esta logica se tomo como referencia una tabla de verdad para 
     * los casos posibles y redundantes.
     * 
     * Modo Simplificado
     */
    if (s1 == true && s4 == true){ //1--1
      //Se detienen las bandas
      //Prender Led de control (Listo para tirar)
      intake.set(ControlMode.PercentOutput, 1);
      motorBandaA.set(ControlMode.PercentOutput, 0);
      motorBandaB.set(ControlMode.PercentOutput, 0);
    } 

    else if (s1 == false && s4 == true || 
             s1 == false && s2 == false && s3 == false && s4 == false ||
             s1 == false && s2 == true && s4 == false){ // 0--1
      intake.set(ControlMode.PercentOutput, 1);
      motorBandaA.set(ControlMode.PercentOutput, 1);
      motorBandaB.set(ControlMode.PercentOutput, 0);
    }
/*
    else if (s1 == false && s2 == false && s3 == false && s4 == false){//0000
      intake.set(1);
      motorBandaA.set(1);
      motorBandaB.set(0);
    }*/

    else if(s1 == true && s2 == false && s3 == false && s4 == false ||
            s1 == true && s2 == false && s3 == true && s4 == false ||
            s1 == true && s2 == true){//1000
      intake.set(ControlMode.PercentOutput, 1);
      motorBandaA.set(ControlMode.PercentOutput, 1);
      motorBandaB.set(ControlMode.PercentOutput, 1);
    }
    /*
    else if(s1 == false && s2 == true && s4 == false){//} s3 == false && s4 == false){
      //0100
      intake.set(1);
      motorBandaA.set(1);
      motorBandaB.set(0);
    }*/
    /*
    else if(s1 == false && s2 == false && s3 == true && s4 == false){//0010
      intake.set(1);
      motorBandaA.set(1);
      motorBandaB.set(0);
    }*/
    /*
    else if(s1 == true && s2 == false && s3 == true && s4 == false){//1010
      intake.set(1);
      motorBandaA.set(1);
      motorBandaB.set(1);
    }*/
    /*
    else if (s1 == true && s2 == true){//&& s3 == false && s4 == false){//1100
      intake.set(ControlMode.PercentOutput, 1);
      motorBandaA.set(ControlMode.PercentOutput, 1);
      motorBandaB.set(ControlMode.PercentOutput, 1);
    }*/
    else {
      intake.set(0);
      motorBandaA.set(0);
      motorBandaB.set(0);
    }

  }

  public void toExtendIntake(){
    pistonA.set(true);
    pistonB.set(true);
  }

  public void saveIntake(){
    pistonA.set(false);
    pistonB.set(false);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
