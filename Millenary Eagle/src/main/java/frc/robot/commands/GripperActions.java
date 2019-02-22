/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/*
* Action 0 --> Soltar Hatch Panel
* Action 1 --> Tomar Hatch Panel
* Action 2 --> Sacar HatchGripper
* Action 3 --> Ocultar HatchGripper
* Action 4 --> Tomar Cargo
* Action 5 --> Soltar Cargo
*/

public class GripperActions extends Command {
  boolean takeCargo = false,
          dropCargo = false,
          takeHP = false,
          dropHP = false,
          takeOutHG = false,
          hideHG = false,
          finish = false,
          lim;
          
  public GripperActions(int action) {
    requires(Robot.gripper);

    switch (action){
      case 0:
        dropHP = true;
        break;
      case 1:
        takeHP = true;
        break;
      case 2:
        takeOutHG = true;
        break;
      case 3:
        hideHG = true;
        break;
      case 4:
        takeCargo = true;
        break;
      case 5:
        dropCargo = true;
        break;
      default:
    }
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if (takeHP == true){
       Robot.gripper.takeHatchPanel();
       finish = true;
    }else if (dropHP == true) {
        Robot.gripper.dropHatchPanel();
        finish = true;
      }else if (takeOutHG == true) {
        Robot.gripper.takeOutHatchGripper();
        finish = true;
        }else if (hideHG == true) {
          Robot.gripper.hideHatchGripper();
          finish = true;
          }
    
  }
  
  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (takeCargo == true) Robot.gripper.takeCargo(); 
    else if (dropCargo == true) Robot.gripper.dropCargo();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (dropCargo == true) {
      lim = false;
    } else {
      lim = Robot.gripper.limit();
    }
    return (finish || lim) ;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    if (lim == true) Robot.gripper.stopMotor();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
