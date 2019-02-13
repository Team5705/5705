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
import frc.robot.RobotMap;
import frc.robot.commands.Drive;

/**
 * Subsistema del tren motriz con 1 SRX y 3 SPX y a la vez un giroscopio como un encoder
 */
public class Powertrain extends Subsystem {
  public WPI_TalonSRX leftMaster = null;  
  WPI_VictorSPX rightMaster = null, 
               leftFollow = null,   rightFollow = null;


  ADXRS450_Gyro gyro;

  DifferentialDrive robotDrive;

  double deadband = 0.06;

  public Powertrain(){
    leftMaster = new WPI_TalonSRX(RobotMap.powertrain_leftmotor1);
    leftFollow = new WPI_VictorSPX(RobotMap.powertrain_leftmotor2);
    rightMaster = new WPI_VictorSPX(RobotMap.powertrain_rightmotor1);
    rightFollow = new WPI_VictorSPX(RobotMap.powertrain_rightmotor2);


    gyro = new ADXRS450_Gyro(RobotMap.gyro);
    gyro.reset();
    gyro.calibrate();

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

    leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    leftMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 1);

    /* leftMaster.config_kF(0, 0.1097, 10);
    leftMaster.config_kP(0, 0.113333, 10);
    leftMaster.config_kI(0, 0, 10);
    leftMaster.config_kD(0,0,10); */
  }
  
  public double distanceInInches(){
    int position = leftMaster.getSelectedSensorPosition(0);
    double distance = (position / 4096) * 8; //Wheels 8''
    return distance;
  }

  public void arcadeDrive(double xSpeed, double rotateSpeed){
    
    if (Math.abs(xSpeed) < deadband) {
      xSpeed = 0;
    }
    if (Math.abs(rotateSpeed) < deadband) {
      rotateSpeed = 0;
    }

    robotDrive.arcadeDrive(xSpeed, rotateSpeed);

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

  public double gyroFinal(){
    return gyro.getAngle();
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new Drive());
  }
}
