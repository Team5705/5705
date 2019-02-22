/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class PositionLevel_Elevator extends Command {
  double position;
  double level;
  int tol;
  boolean lim1, lim2;

  public PositionLevel_Elevator(int level) {
    requires(Robot.elevator);

    switch (level){
      case 1:
        this.level = 10;
        tol = 10;
        break;
      case 2:
        this.level = 5000;
        tol = 20;
        break;
      case 3:
        this.level = 10000;
        tol = 10;
        break;
      default:
        end();
    }
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(Robot.mode == "MM") end();

    double speed = -(((position - level)*0.00035)/2);
    Robot.elevator.manualElevator(speed);

    
  }
  
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    position = Robot.elevator.position();
    lim1 = Robot.elevator.limitDown();
    lim2 = Robot.elevator.limitUp();
    
    return (((level-tol <= position) && (position <= level+tol)) || (lim1 || lim2));
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.elevator.manualElevator(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }

  double velocity(double speed, double maxSpeed, double minSpeed){
    if (speed >= maxSpeed) speed = maxSpeed;
    else if (speed <= -maxSpeed) speed = -maxSpeed;
    else if(speed <= minSpeed && speed > 0) speed = minSpeed;
    else if(speed >= -minSpeed && speed < 0) speed = -minSpeed;
    return speed;
  }
}
