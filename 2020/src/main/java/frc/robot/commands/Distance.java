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
  private static Powertrain powertrain;

  double enc;

  double distance; //in Inches

  double T = 20;
  double err = 0;
  double errI = 0; //Integral
  double errD = 0;
  double errPp = 0;

  double errP = 0; //Pasado

  double kP = 0.002, kD = 0.1, kI = 0;

  double PID = 0;

  public Distance(Powertrain sub, double distanceInInches) {
    powertrain = sub;
    this.distance = distanceInInches;
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
    enc = (powertrain.position()/4096)*(Math.PI*6); //Distance in Inches
    
    err = -(distance - enc); //Negative!
    
    errI = (err * T) + errP;

    errD = (err - errPp)/T;

    errP = errI;
    errPp = err;

    /****************************************
     *                PID
     ****************************************/

    PID = ((err*kP) + (errI*kI) + (errD*kD));

    powertrain.arcadeDrive(PID, 0);

    SmartDashboard.putNumber("PID", PID);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
