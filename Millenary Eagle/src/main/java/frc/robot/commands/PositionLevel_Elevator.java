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

public class PositionLevel_Elevator extends Command {
  double position;
  double level;
  int tol;
  boolean lim1;


  public PositionLevel_Elevator(int level) {
    requires(Robot.elevator);

    switch (level){
      case 1:
        this.level = 500;
        tol = 150;
        //System.out.println("Elevator Level 1");
        break;

      case 2:
        this.level = 15100;
        tol = 150;
        //System.out.println("Elevator Level 2");
        break;

      case 3:
        this.level = 23000;
        tol = 10;
        //System.out.println("Elevator Level 3");
        break;

      default:
        System.out.println("No data level elevator");
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
  //if(Robot.mode == "MM") end();

    double speed = (((level - position)*0.0006));
    double spe = velocity(speed, 1, 0.4);
    Robot.elevator.manualElevator(spe);

    SmartDashboard.putNumber("ElevSpeed", spe);
  }
  
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    position = Robot.elevator.position();
    SmartDashboard.putNumber("Elevator Position", position);
    lim1 = Robot.elevator.limitDown();
    return ((level-tol) <= position && position <= (level+tol)); //|| lim1;//|| position > 10000);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.elevator.manualElevator(0.1);
    //if(lim1 == true) Robot.elevator.resetEncoder();
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
