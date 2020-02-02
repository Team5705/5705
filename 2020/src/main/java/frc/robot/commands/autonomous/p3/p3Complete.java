/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autonomous.p3;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Distance;
import frc.robot.commands.Tracking;
import frc.robot.commands.TurnPID;
import frc.robot.subsystems.Powertrain;
import frc.robot.subsystems.Vision;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class p3Complete extends SequentialCommandGroup {

  public p3Complete(Powertrain powertrain, Vision vision) {

    super(new p3Trace(powertrain), 
          new ParallelRaceGroup( 
            new Tracking(powertrain, vision), new SequentialCommandGroup(new WaitCommand(2) )), //Agregar tiro
          new Distance(powertrain, -2),
          new TurnPID(powertrain, 180)
          );
  }
}
