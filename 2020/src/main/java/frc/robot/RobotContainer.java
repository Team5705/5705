/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import frc.robot.Constants.OIConstant;
import frc.robot.Constants.pathWeaver;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Powertrain powertrain = new Powertrain();
  private final Vision vision = new Vision();
  private final IntakeBalls intake = new IntakeBalls();

  private final Drive drive = new Drive(powertrain);


  public static XboxController driverController = new XboxController(OIConstant.controllerPort);

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    powertrain.setDefaultCommand(drive);
    /***************************************************************************
    // Configure default commands
    // Set the default drive command to split-stick arcade drive
    powertrain.setDefaultCommand(
        // A split-stick arcade command, with forward/backward controlled by the left
        // hand, and turning controlled by the right.
        new RunCommand(() -> powertrain
            .arcadeDrive(driverController.getRawAxis(0),
                         driverController.getRawAxis(5)), powertrain));
    ****************************************************************************/
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(driverController, 1).whileHeld(new Tracking(powertrain, vision));
    new JoystickButton(driverController, 2).whileHeld(new Distance(powertrain, 8, 0.6, 0, 15));
    new JoystickButton(driverController, 3).whileHeld(new TurnPID(powertrain, 180, 0, 0, 0));
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // Create a voltage constraint to ensure we don't accelerate too fast
    var autoVoltageConstraint =
        new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(pathWeaver.ksVolts,
                                       pathWeaver.kvVoltSecondsPerMeter,
                                       pathWeaver.kaVoltSecondsSquaredPerMeter),
                                       pathWeaver.kDriveKinematics, 10);

    // Create config for trajectory
    TrajectoryConfig config =
        new TrajectoryConfig(pathWeaver.kMaxSpeedMetersPerSecond,
                             pathWeaver.kMaxAccelerationMetersPerSecondSquared)
            // Add kinematics to ensure max speed is actually obeyed
            .setKinematics(pathWeaver.kDriveKinematics)
            // Apply the voltage constraint
            .addConstraint(autoVoltageConstraint);

    // An example trajectory to follow.  All units in meters.
    Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
        // Start at the origin facing the +X direction
        new Pose2d(0, 0, new Rotation2d(0)),
        // Pass through these two interior waypoints, making an 's' curve path
        List.of(
            new Translation2d(1, 1),
            new Translation2d(2, -1)
        ),
        // End 3 meters straight ahead of where we started, facing forward
        new Pose2d(3, 0, new Rotation2d(0)),
        // Pass config
        config
    );
    Trajectory auto;
    String trajectoryJSON = "paths/Test.wpilib.json";
      try {
        Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
        auto = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
      } catch (IOException ex) {
        DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON, ex.getStackTrace());
        auto = exampleTrajectory;
      }
    RamseteCommand ramseteCommand = new RamseteCommand(
        exampleTrajectory,
        powertrain::getPose,
        new RamseteController(pathWeaver.kRamseteB, pathWeaver.kRamseteZeta),
        new SimpleMotorFeedforward(pathWeaver.ksVolts,
                                   pathWeaver.kvVoltSecondsPerMeter,
                                   pathWeaver.kaVoltSecondsSquaredPerMeter),
        pathWeaver.kDriveKinematics,
        powertrain::getWheelSpeeds,
        new PIDController(pathWeaver.kPDriveVel, 0, 0),
        new PIDController(pathWeaver.kPDriveVel, 0, 0),
        // RamseteCommand passes volts to the callback
        powertrain::tankDriveVolts,
        powertrain
    );

    // Run path following command, then stop at the end.
    return ramseteCommand.andThen(() -> powertrain.tankDriveVolts(0, 0));
    //return new Autonomous1(powertrain);
  }
}
