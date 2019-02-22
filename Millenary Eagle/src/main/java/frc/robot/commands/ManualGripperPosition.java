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

public class ManualGripperPosition extends Command {
  double amarre;
  double angle;

  public ManualGripperPosition() {
    requires(Robot.gripper);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (angle >= 0 && angle <= 5) amarre = 0;
    else if (angle > 5 && angle <= 85) amarre = 0.3;
    else if (angle > 85 && angle < 90) amarre = 0.45;
    else if (angle >= 90) amarre = 0.65;

    double speed = (amarre + (Robot.oi.controller1.getRawAxis(5) * (1-amarre)));

    if (Robot.oi.controller1.getRawButton(7) == true){
    Robot.gripper.moveGRIPPER(speed);
    }

    SmartDashboard.putNumber("SpeedGripperPosition", speed);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    angle = Robot.gripper.gripperAngle();
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.gripper.moveGRIPPER(amarre);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
