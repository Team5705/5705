/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Powertrain;

public class Distance extends CommandBase {
  private final Powertrain powertrain;

  double enc;

  double distance; //in Inches

  double T = 20;
  double err = 0;
  double errI = 0; //Integral
  double errD = 0;
  double errPp = 0;

  double errP = 0; //Pasado

  double kP = 0.85, kI = 0.00001  , kD = 30.0;

  double PID = 0;

  /**
   * 7v7
   * @param sub Subsistema motriz
   * @param distanceInMeters Distancia deseada en metros
   */
  public Distance(Powertrain powertrain, double distanceInMeters) {
    this.powertrain = powertrain;
    this.distance = distanceInMeters;
    addRequirements(powertrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    powertrain.resetGyro();
    powertrain.resetEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    enc = powertrain.getDistanceRight(); //Distance in meter
    
    err = (distance - enc);
    
    errI = (err * T) + errP;

    errD = (err - errPp)/T;

    errP = errI;
    errPp = err;

    /****************************************
     *                PID
     ****************************************/

    PID = ((err*kP) + (errI*kI) + (errD*kD));

    double turn = (0 - powertrain.angle())*0.1;

    powertrain.arcadeDrive(PID, turn);

    SmartDashboard.putNumber("PID", PID);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (enc > (distance-0.1));
  }
}
