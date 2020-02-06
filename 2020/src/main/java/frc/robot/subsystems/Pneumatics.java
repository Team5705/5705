/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pneumatics extends SubsystemBase {

  private final Solenoid car = new Solenoid(0);
  private final Solenoid car2 = new Solenoid(1);
  private final Solenoid car3 = new Solenoid(2);
  private final Solenoid car4 = new Solenoid(3);
  /**
   * Creates a new Pneumatics.
   */
  public Pneumatics() {

  }

  public void on(){
    car.set(true);
    car2.set(true);
    car3.set(true);
    car4.set(true);
  }

  public void off(){
    car.set(false);
    car2.set(false);;
    car3.set(false);
    car4.set(false);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
