/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5705.robot;

import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	
	public XboxControllerMap driverController = new XboxControllerMap(RobotMap.Joystick_1);
	
	JoystickButton D1 = new JoystickButton(driverController, 1);
	JoystickButton D2 = new JoystickButton(driverController, 2);
	
	public OI() {
		
		D1.whileHeld(null);
		D2.whileHeld(null);
	}
	
}
