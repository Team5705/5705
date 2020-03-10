/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 Desert Eagles 5705. All Rights Reserved.                */
/* Codigo hecho en base a los conocimientos y experiencias del equipo.        */
/* Usar este codigo como base para hacer su propio codigo. Por favor de no    */
/* copiar exactamente partes de nuestro código,                               */
/* pongase en contacto con nuestro equipo y con gusto lo asesoramos con       */
/* nuestros conocimientos y experiencias.                                     */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  private Command currentCommand;

  private RobotContainer m_robotContainer;

  private String gameData;

  

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer. This will perform all our button bindings,
    // and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();

    RobotContainer.leds.sendData(10);

    
    


    
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler. This is responsible for polling buttons, adding
    // newly-scheduled
    // commands, running already-scheduled commands, removing finished or
    // interrupted commands,
    // and running subsystem periodic() methods. This must be called from the
    // robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();

    gameData();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
    RobotContainer.leds.sendData(0);
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    RobotContainer.leds.sendData(1);

    currentCommand = m_robotContainer.getAutonomousCommand();

        if (currentCommand != null) {
            currentCommand.schedule();
        }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {

  }

  @Override
  public void teleopInit() {
    RobotContainer.leds.sendData(1);
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (currentCommand != null) {
      currentCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  private void gameData() {
    gameData = DriverStation.getInstance().getGameSpecificMessage();
    if (gameData.length() > 0) {
      switch (gameData.charAt(0)) {
      case 'B':
        // Blue case code
        SmartDashboard.putString("Gamedata", "Blue/Azul");
        SmartDashboard.putBoolean("B", true);

        RobotContainer.leds.sendData(8);
        break;
      case 'G':
        // Green case code
        SmartDashboard.putString("Gamedata", "Green/Verde");
        SmartDashboard.putBoolean("G", true);

        RobotContainer.leds.sendData(7);
        break;
      case 'R':
        // Red case code
        SmartDashboard.putString("Gamedata", "Red/Rojo");
        SmartDashboard.putBoolean("R", true);

        RobotContainer.leds.sendData(5);
        break;
      case 'Y':
        // Yellow case code
        SmartDashboard.putString("Gamedata", "Yellow/Amarillo");
        SmartDashboard.putBoolean("Y", true);

        RobotContainer.leds.sendData(6);
        break;
      default:
        // This is corrupt data
        SmartDashboard.putString("Gamedata", "Corrupt Data");
      }
    } else {
      // No data received yet
    }
  }
}
