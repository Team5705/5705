/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Powertrain;

public class Drive extends CommandBase {
  private final Powertrain powertrain;
  /**
   * Creates a new Drive.
   */
  public Drive(Powertrain subsystemDrive) {
    // Use addRequirements() here to declare subsystem dependencies.
    powertrain = subsystemDrive;
    addRequirements(powertrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //powertrain.resetGyro();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double xSp = RobotContainer.driverController.getRawAxis(1);
    double turn = RobotContainer.driverController.getRawAxis(4);
    powertrain.arcadeDrive(xSp, turn);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    powertrain.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
