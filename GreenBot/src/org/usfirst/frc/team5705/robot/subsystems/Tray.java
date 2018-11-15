package org.usfirst.frc.team5705.robot.subsystems;

import org.usfirst.frc.team5705.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Tray extends Subsystem {

	Talon motorVentanilla = null;

	DigitalInput limitSwitch = null;
	DigitalInput limitSwitch2 = null;

	public Tray() {
		motorVentanilla = new Talon(RobotMap.motorVentanilla);
		limitSwitch = new DigitalInput(RobotMap.limitswitch_1);
		limitSwitch2 = new DigitalInput(RobotMap.limitswitch_2);
	}

	public void Speed(double speed) {
		motorVentanilla.set(speed);
	}

	public boolean isSwitch1Set() {
		return limitSwitch.get();
	}

	public boolean isSwitch2Set() {
		return limitSwitch2.get();
	}

	protected void initDefaultCommand() {

	}

}
