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
import frc.robot.VisionPIDController;

public class VisionTargetCenter extends Command {
  double center;
  int ref = 160; //320 x 240 resolution
  int tol = 5;

  static VisionPIDController pidVision;

  public VisionTargetCenter() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.powertrain);

    pidVision = new VisionPIDController(0.004, 0.0001, 0, -0.5, 0.5);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double xSpeed = -Robot.oi.controller1.getRawAxis(1);
    //double rotate = -(((ref - center) * .05));
    
    double speed = -pidVision.getPIDValue(ref);

    SmartDashboard.putNumber("Speed", speed);

    Robot.powertrain.arcadeDrive(xSpeed, speed);
  
  }

  double velocity(double speed, double maxSpeed){
    if (speed >= maxSpeed) speed = maxSpeed;
    else if (speed <= -maxSpeed) speed = -maxSpeed;
    return speed;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    center = Robot.center;
    return center > (ref - tol) && center < (ref + tol);
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
