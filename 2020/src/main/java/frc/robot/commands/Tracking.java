/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.PID;
import frc.robot.subsystems.Powertrain;
import frc.robot.subsystems.Vision;

public class Tracking extends CommandBase {
  private final Vision vision;
  private final Powertrain powertrain;
  private static PID pidX = new PID(0.04, 0, 8, 0, 0.27, false);
  private static PID pidY = new PID(0.05, 0, 8, 0, 0.27, true);

  public Tracking(Powertrain powertrain, Vision vision) {
    this.powertrain = powertrain;
    this.vision = vision;
    addRequirements(this.powertrain, this.vision);
  }

  @Override
  public void initialize() {
    vision.ledsDefault();
  }

  @Override
  public void execute() {
    double x = vision.getX();
    double y = vision.getY();

    pidX.runPIDErr(x);
    pidY.runPIDErr(y);

    if (vision.availableTarget()) {

      double xS = pidY.valuePID();
      double turn = pidX.valuePID();

      powertrain.arcadeDrive(xS, turn);// x*0.03);
    } else
      powertrain.arcadeDrive(0, 0);
  }

  @Override
  public void end(boolean interrupted) {
    vision.ledsOff();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
