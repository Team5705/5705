package org.usfirst.frc.team5705.robot.subsystems;

import org.usfirst.frc.team5705.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain extends Subsystem{
	
	public void initDefaultCommand(){
		
	}
	public void TankDrive(double speedL, double speedR){
		drivetrain.tankDrive(speedL, speedR);
		
	}
	
	public void ArcadeDrive(double speed,  double turn){
		drivetrain.arcadeDrive(speed, turn);
		
	}
	public DifferentialDrive drivetrain = RobotMap.Chasis;
}