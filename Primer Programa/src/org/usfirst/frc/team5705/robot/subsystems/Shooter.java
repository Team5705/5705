package org.usfirst.frc.team5705.robot.subsystems;

import org.usfirst.frc.team5705.robot.RobotMap;
import org.usfirst.frc.team5705.robot.commands.Disparador;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem {
	Talon shooter = RobotMap.disparador;
	
	
	
	public void initDefaultCommand(){
		setDefaultCommand(new Disparador());
	}
	public void Ndisparo() {
		shooter.set(1.0);
	}
	public void Cdisparo() {
		shooter.set(-1.0);
	}
	public void Stop() {
		
	}
}