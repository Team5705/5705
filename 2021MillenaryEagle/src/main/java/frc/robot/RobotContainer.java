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
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import frc.robot.Constants.OIConstant;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
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
  public static Leds leds = new Leds();

  private final Drive drive = new Drive(powertrain);
  private final Climberr climberr = new Climberr(climber);

  public static XboxController driverController = new XboxController(OIConstant.controllerPort);

  SendableChooser<String> autonomous = new SendableChooser<String>();
  
  ArrayList<String> trajectoryPaths = new ArrayList<String>();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    powertrain.setDefaultCommand(drive);
    climber.setDefaultCommand(climberr);

    autonomous.addOption("Trench", "trench");
    autonomous.addOption("Mid", "mid");
    autonomous.addOption("Simple", "simple");
    autonomous.addOption("Emergency", "emergency");
    autonomous.addOption("Test", "test");
    SmartDashboard.putData("Auto Mode?", autonomous);



    trajectoryPaths.add(0, "paths/Trace1.wpilib.json");
    trajectoryPaths.add(1, "paths/Trace2.wpilib.json");

    trajectoryPaths.add(2, "paths/Trace1_v2.wpilib.json");
    trajectoryPaths.add(3, "paths/Trace2_v2.wpilib.json");
    trajectoryPaths.add(4, "paths/Trace3_v2.wpilib.json");
    trajectoryPaths.add(5, "paths/Trace4_v2.wpilib.json");

    trajectoryPaths.add(6, "paths/One_Meter_Test.wpilib.json");

    vision.ledsOff();

    /**
     * Autonomous 1
     */
    /*auto1.add(0, new Shoot(shooter, intake, powertrain, vision).withTimeout(3.5));
    loadConfigs(1, trajectoryPaths.get(0), auto1);
    auto1.add(2, new ParallelDeadlineGroup(new Distance(powertrain, 2.6, 0, 0, 0, 0.4),
                                              new TakeWithSensor(intake)) );
    loadConfigs(3, trajectoryPaths.get(1), auto1);
    auto1.add(4, new Shoot(shooter, intake, powertrain, vision).withTimeout(3.5));

    /**
     * Autonomous 2 
     */
    /*
    auto2.add(0, new Shoot(shooter, intake, powertrain, vision));
    loadConfigs(1, trajectoryPaths.get(2), auto2);
    auto2.add(2, new TakeWithSensor(intake).withTimeout(1));
    loadConfigs(3, trajectoryPaths.get(3), auto2);
    auto2.add(4, new TakeWithSensor(intake).withTimeout(1));
    loadConfigs(5, trajectoryPaths.get(4), auto2);
    auto2.add(6, new TakeWithSensor(intake).withTimeout(1));
    loadConfigs(7, trajectoryPaths.get(5), auto2);

    //Test
      test.add(0, new PrintCommand("0").withTimeout(2));
      test.add(1, new PrintCommand("1").withTimeout(2));
      test.add(2, new PrintCommand("2").withTimeout(2));
      test.add(3, new PrintCommand("3").withTimeout(2));
      test.add(4, new PrintCommand("4").withTimeout(2));
*/
    
  }

  public Command ramseteC(String trajectoryPaths) {
        String path = trajectoryPaths;
        Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(path);
        try {
            Trajectory trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);

            RamseteCommand command = new RamseteCommand(trajectory, powertrain::getPosition,
                    new RamseteController(2.0, .7), powertrain.getFeedFoward(), powertrain.getDifferentialDriveKinematics(),
                    powertrain::getWheelSpeeds, powertrain.getLeftPIDController(), powertrain.getRightPIDController(),
                    powertrain::setVolts, powertrain);
                    powertrain.zeroHeading();

                    System.out.println("Path successfully read");
                    new PrintCommand("Path successfully read");
                    
            return command;
    
        } catch (IOException e) {
            // TODO: handle this just in case maybe
            System.out.println("Unable to open trajectory: " + path);
            new PrintCommand("Unable to open trajectory: " + path);

            return null;
        }
  }
  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    new JoystickButton(driverController, 1).whileHeld(new Shootv2(shooter));
    new JoystickButton(driverController, 2).whenPressed(new InstantCommand(intake::toExtendIntake, intake));
    new JoystickButton(driverController, 3).whenPressed(new InstantCommand(intake::saveIntake, intake));

    new JoystickButton(driverController, 4).whenPressed(new Shoot(shooter, intake, powertrain, vision).withTimeout(4.5));
   
    new JoystickButton(driverController, 5).whileHeld(new TakeAll(intake));
    new JoystickButton(driverController, 6).whileHeld(new TakeWithSensor(intake));
  }

  //-----------------------------------------------------------------------------------------------------------------------------------------

  public Command getAutonomousCommand(){
    if(autonomous.getSelected() == "trench"){
      
      return new SequentialCommandGroup(new Shoot(shooter, intake, powertrain, vision).withTimeout(3.6), 
                                        ramseteC(trajectoryPaths.get(0)),
                                        new PrintCommand("3"), 
                                        new PrintCommand("4")
                                        );
    }

    else if(autonomous.getSelected() == "mid"){
      
      return new SequentialCommandGroup(new PrintCommand("1"), new PrintCommand("2"), new PrintCommand("3"), new PrintCommand("4"));
    }
    
    else if(autonomous.getSelected() == "Test"){
      return ramseteC(trajectoryPaths.get(6)); //6 is test
    }
    else
    return null;
  }
}
  
//-----------------------------------------------------------------------------------------------------------------------------------------  

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   *//*
  public final Command getPathWeaver() {

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
  }*/
  /*
  public Command getNextAutonomousCommand() {
    if(autonomous.getSelected() == "trinch"){
      auto1.remove(0);
      return auto1.get(0);
    }

    else if(autonomous.getSelected() == "mid"){
      auto2.remove(0);
      return auto2.get(0);
    }

    else
      //return null;
      return test.remove(0);
}*/
//}
