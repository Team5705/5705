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

public class Drive extends Command {
  double chassisSpeed;

  public Drive() {
    requires(Robot.powertrain);

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    chassisSpeed = 1.0;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    int POV = Robot.oi.controller1.getPOV();

    double moveSpeed = -Robot.oi.controller1.getRawAxis(1);
    double rotateSpeed = Robot.oi.controller1.getRawAxis(4);

    Robot.powertrain.arcadeDrive(moveSpeed * chassisSpeed, rotateSpeed * chassisSpeed);  
    

    SmartDashboard.putNumber("Angle", Robot.powertrain.gyro());
    SmartDashboard.putNumber("POV", POV);
    SmartDashboard.putNumber("ChassisSpeed", chassisSpeed);
    SmartDashboard.putNumber("GyroFinal", Robot.powertrain.gyroFinal());
    SmartDashboard.putNumber("Distance ", Robot.powertrain.position());

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.powertrain.arcadeDrive(0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
