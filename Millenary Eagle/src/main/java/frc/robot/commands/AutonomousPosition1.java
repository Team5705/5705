/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonomousPosition1 extends CommandGroup {
  /**
   * Add your docs here.
   */
  public AutonomousPosition1() {

    addSequential(new DistanceinInches(383.7, -19));
    addSequential(new RotatebyAngle(45));
    addParallel(new PositionLevel_Elevator(2));
    addSequential(new TrackingVisionTarget());
    addSequential(new DistanceinInches(6, 0));
    addSequential(new GripperActions(2)); //Sacar gripp Hatch Panel
    addSequential(new GripperActions(0)); //Soltar hatch panel
    addParallel(new GripperActions(3)); //Ocultar gripp hatch panel
    addSequential(new PositionLevel_Elevator(1));
    addSequential(new RotatebyAngle(-45));

    // Add Commands here:
    // e.g. addSequential(new Command1());
    // addSequential(new Command2());
    // these will run in order.

    // To run multiple commands at the same time,
    // use addParallel()
    // e.g. addParallel(new Command1());
    // addSequential(new Command2());
    // Command1 and Command2 will run in parallel.

    // A command group will require all of the subsystems that each member
    // would require.
    // e.g. if Command1 requires chassis, and Command2 requires arm,
    // a CommandGroup containing them would require both the chassis and the
    // arm.
  }
}
