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

/**
 * Add your docs here.
 */
public class Solenoidd extends Subsystem {

  Solenoid sole = null;
	
	public Solenoidd() {
		sole = new Solenoid(RobotMap.solenoid_arm);
	}
	
	public void open() {
		sole.set(true);
	}
	
	public void close() {
		sole.set(false);
	}

  @Override
  public void initDefaultCommand() {
  
  }
}
