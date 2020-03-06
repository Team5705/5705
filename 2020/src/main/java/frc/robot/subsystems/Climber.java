/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
  private final Spark m1 = new Spark(9);
  private final Spark m2 = new Spark(8);
  private final Spark up = new Spark(7);

  private final DifferentialDrive scale = new DifferentialDrive(m1, m2);

  public Climber() {

  }

  public void speedClimber(double speed) {
    up.set(speed);

  }

  public void speedRobotClimber(double speed) {
    scale.arcadeDrive(speed, 0);
  }

  @Override
  public void periodic() {
  }
}
