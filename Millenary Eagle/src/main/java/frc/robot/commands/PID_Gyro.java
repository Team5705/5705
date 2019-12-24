/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class PID_Gyro extends Command {
  double gyro;

  double ajuste = 45;

  double T = 20;
  double err = 0;
  double errI = 0; //Integral
  double errD = 0;
  double errPp = 0;

  double errP = 0; //Pasado

  double kP = 0.05;
  double kD = 0;
  double kI = 0;

  double PID = 0;

  public PID_Gyro() {
    requires(Robot.powertrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  Robot.powertrain.resetGyro();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    gyro = Robot.powertrain.gyroFinal();
    
    err = (ajuste - gyro);
    
    errI = (err * T) + errP;

    errD = (err - errPp)/T;

    errP = errI;
    errPp = err;

    /****************************************
     *                PID
     ****************************************/

    PID = (err*kP) + (errI*kI) + (errD*kD);

    Robot.powertrain.arcadeDrive(0, err);

    SmartDashboard.putNumber("PID", PID);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
