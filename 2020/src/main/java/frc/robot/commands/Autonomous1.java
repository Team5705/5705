/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Powertrain;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class Autonomous1 extends SequentialCommandGroup {
  public static final int timeDelay = 1; //Tiempo de espera en segundos

  public Autonomous1(Powertrain subsystem) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(new Distance(subsystem, 10), new WaitCommand(timeDelay), 
          new TurnPID(subsystem, 90),  new WaitCommand(timeDelay),
          new Distance(subsystem, 20), new WaitCommand(timeDelay), 
          new TurnPID(subsystem, -45));
  }
}
