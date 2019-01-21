/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Powertrain extends Subsystem {
  WPI_TalonSRX leftMaster = null,   rightMaster = null, 
           leftFollow = null,   rightFollow = null;

  ADXRS450_Gyro gyro;

    DifferentialDrive robotDrive;

  public Powertrain(){
    leftMaster = new WPI_TalonSRX(RobotMap.powertrain_leftmotor1);
    leftFollow = new WPI_TalonSRX(RobotMap.powertrain_leftmotor2);
    rightMaster = new WPI_TalonSRX(RobotMap.powertrain_rightmotor1);
    rightFollow = new WPI_TalonSRX(RobotMap.powertrain_rightmotor2);

    robotDrive = new DifferentialDrive(leftMaster, rightMaster);

    leftMaster.configFactoryDefault();
    leftFollow.configFactoryDefault();
		rightMaster.configFactoryDefault();
    rightFollow.configFactoryDefault();
    
    leftFollow.follow(leftMaster);
    rightFollow.follow(rightMaster);

    leftMaster.setInverted(false);
    rightMaster.setInverted(true);
    
    leftFollow.setInverted(InvertType.FollowMaster);
    rightFollow.setInverted(InvertType.FollowMaster);
  }
    public void arcadeDrive(double xSpeed, double ySpeed){
      /* Gamepad processing */
      double forw = -1 * ySpeed;
      double turn = +1 * xSpeed;	
      
      if (Math.abs(forw) < 0.10) {
        forw = 0;
      }
      if (Math.abs(turn) < 0.10) {
        turn = 0;
      }

      robotDrive.arcadeDrive(forw, turn);

    }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
