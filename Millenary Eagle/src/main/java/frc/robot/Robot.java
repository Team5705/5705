/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */

 /*
 * Robot Code Desert Eagles 5705 
  */
public class Robot extends TimedRobot {
  public static Powertrain powertrain;
  public static Gripper gripper; 
  public static Elevator elevator;
  public static Jumper jumper;
  
  public static Compressor compresor;
  //public static final Object imgLock = new Object();

  private static I2C Wire = new I2C(Port.kOnboard, 0xF3);
  
  NetworkTable table;
  NetworkTable subtable;
  NetworkTableEntry centerX;
  public static double center;

  Command autonomousCommand;
  public static String mode;
  String auto;

  public static OI oi;
  public static GlobalVariables globalvariables;
  
  SendableChooser<Command> chooser = new SendableChooser<>();
  SendableChooser<String> mode_chooser = new SendableChooser<>();
  SendableChooser<String> autonomous = new SendableChooser<>();

  SendableChooser<Boolean> compress = new SendableChooser<>();
  
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    health();

  
    powertrain = new Powertrain();
    gripper = new Gripper();
    elevator = new Elevator();
    jumper = new Jumper();
    
    compresor = new Compressor(0);
    
    oi = new OI();

    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    table = inst.getTable("CenterVisionTarget");
    centerX = table.getEntry("centerVT");
    inst.startClientTeam(5705);

    chooser.setDefaultOption("Automatic Autonomous", null);
    chooser.addOption("Estation 1", new RotatebyAngle(45));
    chooser.addOption("Estation 2", new DistanceinInches(10, 0));
    chooser.addOption("Estation 3", new AutonomousPosition2());
    chooser.addOption("Null", null);
    
    mode_chooser.setDefaultOption("Automated Mode", "AM");
    mode_chooser.addOption("Manual Mode", "MM");

    autonomous.setDefaultOption("Yes", "Y");
    autonomous.addOption("No", "N");
    
    compress.setDefaultOption("On", true);
    compress.addOption("Off", false);
    
    
    SmartDashboard.putData("Select Autonomous", chooser);
    SmartDashboard.putData("Select Mode", mode_chooser);
    SmartDashboard.putData("Autonomous On?", autonomous);
    SmartDashboard.putData("Compressor", compress);

  }
  
  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    mode = mode_chooser.getSelected();

    center = centerX.getDouble(0);
    SmartDashboard.putNumber("CenterX", center);

    boolean com = compress.getSelected();

     if(com == true) compresor.start();
     else compresor.stop();

    if(compresor.enabled() == true) SmartDashboard.putString("Compresor", "ON");
    else SmartDashboard.putString("Compresor", "OFF");

  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }
  
  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    try {
      Wire.write(0xF5, 6);
    } catch (Exception e) {
      //TODO: handle exception
    }


    autonomousCommand = chooser.getSelected();
    auto = autonomous.getSelected();
    
    /*
    * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (autonomousCommand != null && auto != "N") {
      autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();

    if (auto != "Y") autonomousCommand.cancel();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
  
  void health(){
    System.out.println("********************************************");
    System.out.println("********** Welcome to 5705 Robot **********");
    System.out.println("********************************************");
    
  }

  
}
