/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.GlobalVariables;

/**
 * Add your docs here.
 */
public class DecreaseSpeed extends InstantCommand {
  /**
   * Add your docs here.
   */
  public DecreaseSpeed() {
    super();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    if (GlobalVariables.chassisSpeed <= 1) GlobalVariables.chassisSpeed = -1;
    else GlobalVariables.chassisSpeed -= 0.2;
  }

}
