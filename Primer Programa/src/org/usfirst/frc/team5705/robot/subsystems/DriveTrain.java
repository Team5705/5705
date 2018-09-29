package org.usfirst.frc.team5705.robot.subsystems;

import org.usfirst.frc.team5705.robot.RobotMap;
import org.usfirst.frc.team5705.robot.commands.Drive;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain extends Subsystem{
	
	public void initDefaultCommand(){
		setDefaultCommand(new Drive());
	}
	public void TankDrive(double speedL, double speedR){
		drivetrain.tankDrive(speedL, speedR);
		
	}
	//Aqui pondremos los modos de manejo del robot, Tank y Arcade.
	public void ArcadeDrive(double speed,  double turn){
		drivetrain.arcadeDrive(speed, turn);
		
	}
	public DifferentialDrive drivetrain = RobotMap.Chasis;
	
	public void Stop() {
		drivetrain.arcadeDrive(0, 0);		
	}
}