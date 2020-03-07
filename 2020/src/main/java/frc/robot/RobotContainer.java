/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import frc.robot.Constants.OIConstant;
import frc.robot.Constants.pathWeaver;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Powertrain powertrain = new Powertrain();
  private final Vision vision = new Vision();
  private final IntakeBalls intake = new IntakeBalls();
  private final Shooter shooter = new Shooter();
  private final Climber climber = new Climber();

  private final Drive drive = new Drive(powertrain);
  //private final Shoot shoot = new Shoot(shooter, intake, powertrain, vision);
  private final Climberr climberr = new Climberr(climber);

  public static XboxController driverController = new XboxController(OIConstant.controllerPort);

  ArrayList<Command> commands = new ArrayList<Command>();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    powertrain.setDefaultCommand(drive);
    climber.setDefaultCommand(climberr);
    /***************************************************************************
     * // Configure default commands // Set the default drive command to split-stick
     * arcade drive powertrain.setDefaultCommand( // A split-stick arcade command,
     * with forward/backward controlled by the left // hand, and turning controlled
     * by the right. new RunCommand(() -> powertrain
     * .arcadeDrive(driverController.getRawAxis(0), driverController.getRawAxis(5)),
     * powertrain));
     ****************************************************************************/
  }

  public void loadConfigs(ArrayList<String> trajectoryPaths) {
    for (String path : trajectoryPaths) {
        Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(path);
        try {
            Trajectory trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);

            RamseteCommand command = new RamseteCommand(trajectory, powertrain::getPosition,
                    new RamseteController(2.0, .7), powertrain.getFeedFoward(), powertrain.getDifferentialDriveKinematics(),
                    powertrain::getWheelSpeeds, powertrain.getLeftPIDController(), powertrain.getRightPIDController(),
                    powertrain::setVolts, powertrain);

            commands.add(command);

        } catch (IOException e) {
            // TODO: handle this just in case maybe
            System.out.println("Unable to open trajectory: " + path);
        }
      }
      System.out.println("Paths successfully read");
  }
  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // new JoystickButton(driverController, 1).whileHeld(new Tracking(powertrain,
    // vision));
    // new JoystickButton(driverController, 1).whileHeld(new Shoot(shooter, intake,
    // powertrain, vision));

    new JoystickButton(driverController, 1).whileHeld(new Shootv2(shooter));// , !driverController.getAButton());
    new JoystickButton(driverController, 2).whenPressed(new InstantCommand(intake::toExtendIntake, intake));
    new JoystickButton(driverController, 3).whenPressed(new InstantCommand(intake::saveIntake, intake));
    // new JoystickButton(driverController, 4).whileHeld(new Tracking(powertrain,
    // vision));
    new JoystickButton(driverController, 4).whenPressed(new Shoot(shooter, intake, powertrain, vision).withTimeout(4.5));
    // new JoystickButton(driverController, 2).whileHeld(new Distance(powertrain, 8,
    // 0.6, 0, 15));
    // new JoystickButton(driverController, 3).whileHeld(new TurnPID(powertrain,
    // 180, 0, 0, 0));
    new JoystickButton(driverController, 5).whileHeld(new TakeAll(intake));
    new JoystickButton(driverController, 6).whileHeld(new TakeWithSensor(intake));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {

    var autoVoltageConstraint = new DifferentialDriveVoltageConstraint(
                new SimpleMotorFeedforward(pathWeaver.ksVolts, pathWeaver.kvVoltSecondsPerMeter, 
                pathWeaver.kaVoltSecondsSquaredPerMeter), powertrain.getDifferentialDriveKinematics(), 10);

        // TODO: update these
        TrajectoryConfig config = new TrajectoryConfig(1, 1);

        config.addConstraint(autoVoltageConstraint);
        config.setKinematics(powertrain.getDifferentialDriveKinematics());

        String trajectoryJSON = "paths/Test.wpilib.json";

        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
            Trajectory trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);

            System.out.println(trajectory.getStates());

            RamseteCommand command = new RamseteCommand(trajectory, powertrain::getPosition, new RamseteController(2.0, .7),
                    powertrain.getFeedFoward(), powertrain.getDifferentialDriveKinematics(), powertrain::getWheelSpeeds,
                    powertrain.getLeftPIDController(), powertrain.getRightPIDController(), powertrain::setVolts, powertrain);

            return command.andThen(() -> powertrain.setVolts(0, 0));
        } catch (IOException ex) {
            System.out.println("Unable to open trajectory: " + trajectoryJSON);
        }

        // TODO: return empty command to set motors to 0, 0
        return null;
  }

  public Command getNextAutonomousCommand() {
    return commands.remove(0);
}
}
