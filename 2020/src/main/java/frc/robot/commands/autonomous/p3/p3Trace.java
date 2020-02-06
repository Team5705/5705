/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autonomous.p3;

import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Distance;
import frc.robot.commands.TurnPID;
import frc.robot.subsystems.Powertrain;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class p3Trace extends SequentialCommandGroup {
  public static final int timeDelay = 1; //Tiempo de espera en segundos

  public p3Trace(Powertrain powertrain) {
    super(new Distance(powertrain, Units.inchesToMeters(40),0,0,0), new WaitCommand(timeDelay), 
          new TurnPID(powertrain, -90,0,0,0),  new WaitCommand(timeDelay),
          new Distance(powertrain, Units.inchesToMeters(196),0,0,0), new WaitCommand(timeDelay), 
          new TurnPID(powertrain, -90,0,0,0), new WaitCommand(timeDelay),
          new Distance(powertrain, Units.inchesToMeters(60),0,0,0));
  }
}
