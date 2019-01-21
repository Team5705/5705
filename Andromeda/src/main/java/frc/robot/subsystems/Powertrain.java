/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Powertrain extends Subsystem {
  TalonSRX leftmotor1 = null,   rightmotor1 = null, 
           leftmotor2 = null,   rightmotor2 = null;

  ADXRS450_Gyro gyro;

    // DifferentialDrive robot;

  public Powertrain(){
    leftmotor1 = new TalonSRX(RobotMap.powertrain_leftmotor1);
    leftmotor2 = new TalonSRX(RobotMap.powertrain_leftmotor2);
    rightmotor1 = new TalonSRX(RobotMap.powertrain_rightmotor1);
    rightmotor2 = new TalonSRX(RobotMap.powertrain_rightmotor2);

    leftmotor1.configFactoryDefault();
    leftmotor2.configFactoryDefault();
		rightmotor1.configFactoryDefault();
    rightmotor2.configFactoryDefault();
    
    leftmotor1.setNeutralMode(NeutralMode.Brake);
    leftmotor2.setNeutralMode(NeutralMode.Brake);
    rightmotor1.setNeutralMode(NeutralMode.Brake);
    rightmotor2.setNeutralMode(NeutralMode.Brake);

    rightmotor1.setInverted(true);
    rightmotor2.setInverted(true);

    leftmotor2.set(ControlMode.Follower, RobotMap.powertrain_leftmotor1);
    rightmotor2.set(ControlMode.Follower, RobotMap.powertrain_rightmotor1);

  }
    public void arcadeDrive(double xSpeed, double ySpeed){
      /* Gamepad processing */
      double forward = -1 * ySpeed;
      double turn = xSpeed;	
      forward = Deadband(forward);
      turn = Deadband(turn);

      /* Arcade Drive using PercentOutput along with Arbitrary Feed Forward supplied by turn */
      leftmotor1.set(ControlMode.PercentOutput, forward, DemandType.ArbitraryFeedForward, +turn);
      rightmotor1.set(ControlMode.PercentOutput, forward, DemandType.ArbitraryFeedForward, -turn);

    }

  double Deadband(double value) {
    /* Upper deadband */
    if (value >= +0.05) 
      return value;
    
    /* Lower deadband */
    if (value <= -0.05)
      return value;
    
    /* Outside deadband */
    return 0;
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
