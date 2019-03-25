/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.RobotJumper;

/**
 * Add your docs here.
 */
public class Jumper extends Subsystem {
  Solenoid front = null, rear = null;

  public Jumper(){
    front = new Solenoid(RobotMap.jumper_front);
    rear = new Solenoid(RobotMap.jumper_rear);
  }
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public void upFront(){
    front.set(true);
  }
  
  public void downFront(){
    front.set(false);
  }

  public void upRear(){
    rear.set(true);
  }
  
  public void downRear(){
    rear.set(false);
  }

    
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new RobotJumper());
  }
}
