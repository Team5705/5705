/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.Constants.Shoot;

public class Shooter extends SubsystemBase {
  private WPI_VictorSPX shoot = new WPI_VictorSPX(Shoot.mShooter);
  private WPI_VictorSPX shoot2 = new WPI_VictorSPX(Shoot.mShooter2);

  private Compressor compressor = new Compressor();

  public Shooter() {
    shoot.configFactoryDefault();
    shoot.setInverted(false);

    shoot2.configFactoryDefault();
    shoot2.setInverted(false);

  }

  public void go() {
    /*
    shoot.setVoltage(12);
    shoot2.setVoltage(12);*/
    
    shoot.set(ControlMode.PercentOutput, 1.00);
    shoot2.set(ControlMode.PercentOutput, 1.00); // 0.9 es el valor óptimo sin utilizar el valor máximo y sin ser poco

    RobotContainer.leds.sendData(2);

    // Apaga el compresor cuando se active el disparador y así utilizar toda la
    // batería
    compressor.stop();
  }

  public void neutral() {
    shoot.set(0);
    shoot2.set(0);

    RobotContainer.leds.sendData(3);

    // Vuelve a activar el compresor si es necesario
    compressor.start();
  }

  public void goPID(double speed) {
    if (speed >= 0.8)
      speed = 0.8;
    else if (speed <= 0)
      speed = 0;
    shoot.setVoltage(speed);
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("CompressorEnable?", compressor.enabled());

  }
}
