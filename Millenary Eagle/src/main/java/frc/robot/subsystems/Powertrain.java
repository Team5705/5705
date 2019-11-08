/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import frc.robot.RobotMap;
import frc.robot.commands.Drive;

/**
 * Subsistema del tren motriz con 1 SRX y 3 SPX y a la vez un giroscopio como un encoder
 */
public class Powertrain extends Subsystem {
  WPI_VictorSPX leftMaster = null,
                leftFollow = null, 
                rightFollow = null;
  WPI_TalonSRX rightMaster = null;


  ADXRS450_Gyro gyro;

  DifferentialDrive robotDrive;

  double deadband = 0.06;

  public Powertrain(){
    leftMaster = new WPI_VictorSPX(RobotMap.powertrain_leftmotor1);
    leftFollow = new WPI_VictorSPX(RobotMap.powertrain_leftmotor2);
    rightMaster = new WPI_TalonSRX(RobotMap.powertrain_rightmotor1);
    rightFollow = new WPI_VictorSPX(RobotMap.powertrain_rightmotor2);

    gyro = new ADXRS450_Gyro(RobotMap.gyro);
    gyro.reset();
    gyro.calibrate();
    
    leftMaster.configFactoryDefault();
    leftFollow.configFactoryDefault();
		rightMaster.configFactoryDefault();
    rightFollow.configFactoryDefault();
    
    leftFollow.follow(leftMaster);
    rightFollow.follow(rightMaster);

    leftMaster.setInverted(false);
    rightMaster.setInverted(false);
    
    leftFollow.setInverted(InvertType.FollowMaster);
    rightFollow.setInverted(InvertType.FollowMaster);
    
    robotDrive = new DifferentialDrive(leftMaster, rightMaster);

    rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    rightMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 1);

    
    /*rightMaster.config_kF(0, 0.1097, 10);
    rightMaster.config_kP(0, 0.5,   10);
    rightMaster.config_kI(0, 0.001, 10);
    rightMaster.config_kD(0, 0.0,   10); */
  }
  
  public double distanceInInches(){
    int position = rightMaster.getSelectedSensorPosition(0);
    double distance = (position / 4096) * 12.56; //Wheels 4''
    return distance;
  }

  public int position(){
    return rightMaster.getSelectedSensorPosition(0);
  } 


  /*public void dis(float inches){
    int value = (int) (inches / 4) * 4096;
    rightMaster.set(ControlMode.Position, value);
  }*/

  public void arcadeDrive(double xSpeed, double rotateSpeed){
    
    /*if (Math.abs(xSpeed) < deadband) {
      xSpeed = 0;
    }
    if (Math.abs(rotateSpeed) < deadband) {
      rotateSpeed = 0;
    } */

    robotDrive.arcadeDrive(xSpeed, rotateSpeed);

  }

  public void resetEncoder(){
    rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0); /* PIDLoop=0,timeoutMs=0 */
    rightMaster.setSelectedSensorPosition(0, 0, 10);
  }

  public void resetGyro(){
    gyro.reset();
  }

  public double gyro(){
    double angle = (gyro.getAngle() % 360);

    if (angle == 0){
      return angle;
    }else{
      if ((angle * 360) < 0){
        return 360 + (angle * 360);
      }else{
      return angle * 360;
      }
    }
  }

  public double getRateGyro(){
    return gyro.getRate();
  }

  public double gyroFinal(){
    return gyro.getAngle();
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new Drive());
  }
}
