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

public class TrackingVisionTarget extends Command {
  double center;
  double tol = 3;
  double ref = 160; // 320 x 240 resolution

  public TrackingVisionTarget() {
    requires(Robot.powertrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double xSpeed = -Robot.oi.controller1.getRawAxis(1);
    double rotate = (((ref - center) * 0.008));

    double speed = velocity(rotate, 0.7, 0.35);
    SmartDashboard.putNumber("Speed", speed);

    Robot.powertrain.arcadeDrive(xSpeed, speed);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    center = Robot.center;
    return center >= (ref - tol) && center <= (ref + tol); //     305 <-- ref --> 335
  }

  double velocity(double speed, double maxSpeed, double minSpeed){
    if (speed >= maxSpeed) speed = maxSpeed;
    else if (speed <= -maxSpeed) speed = -maxSpeed;
    else if(speed <= minSpeed && speed > 0) speed = minSpeed;
    else if(speed >= -minSpeed && speed < 0) speed = -minSpeed;
    return speed;
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
