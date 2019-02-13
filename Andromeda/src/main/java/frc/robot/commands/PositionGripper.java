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

/*
*   Position 0 --> Initial/Default
*   Position 1 --> Take Cargo
*   Position 2 --> Take/Drop Hatch Panel | Drop Cargo in Level 1 and 2
*   Position 3 --> Drop Cargo in Level 3
*/

public class PositionGripper extends Command {
  double angle;
  double positionAngle;
  int tol = 1;

  public PositionGripper(int position) {
    requires(Robot.gripper);
    
    switch (position){
      case 0:
        positionAngle = 0;
        break;
      case 1:
      positionAngle = -90;
        break;
      case 2:
      positionAngle = -45;
        break;
      case 3:
      positionAngle = -10;
        break;
      default:
      positionAngle = 0;
    }
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (Robot.mode == "MM") end();
    double speed = (((angle - positionAngle)*0.00035)/2);

    Robot.gripper.moveGRIPPER(speed);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    angle = Robot.gripper.gripperAngle();
    SmartDashboard.putNumber("Gripper Angle", angle);

  return (angle >= positionAngle-tol && angle <= positionAngle+tol);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.gripper.moveGRIPPER(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}