/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  public XboxController driverController = new XboxController(RobotMap.Joystick_1);
	
	JoystickButton D1 = new JoystickButton(driverController, 1);
	JoystickButton D2 = new JoystickButton(driverController, 2);
	JoystickButton D3 = new JoystickButton(driverController, 3);
	JoystickButton D4 = new JoystickButton(driverController, 4);
	
	public OI() {
		
		D1.whenPressed(new On());
		D2.whenPressed(new Off());
		D3.whileHeld(null);
		D4.whileHeld(null);
		
		
	}
}
