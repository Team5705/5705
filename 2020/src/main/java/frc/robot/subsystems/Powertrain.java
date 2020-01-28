/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
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
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstant;


public class Powertrain extends SubsystemBase { 
  private final WPI_TalonSRX leftMaster = new WPI_TalonSRX(DriveConstant.portsMotors[0]);
  private final WPI_VictorSPX rightMaster = new WPI_VictorSPX(DriveConstant.portsMotors[2]),
                              leftFollow = new WPI_VictorSPX(DriveConstant.portsMotors[1]),
                              rightFollow = new WPI_VictorSPX(DriveConstant.portsMotors[3]);

  private final DifferentialDrive drive = new DifferentialDrive(leftMaster, rightMaster);

  private final Gyro gyro = new ADXRS450_Gyro(DriveConstant.Gyro);
  private final AHRS ahrs = new AHRS(SerialPort.Port.kMXP);

  /**
   * Creates a new Powertrain.
   */
  
  public Powertrain() {
    gyro.calibrate();

    configTalon_Victor();
    
    new PrintCommand("Powertrain iniciado");
  }
  
  public void arcadeDrive(double xSp, double turn){
    drive.arcadeDrive(xSp, turn);  
  }

  public int position(){
    return leftMaster.getSelectedSensorPosition(0);
  }

  public double getDistanceLeft(){
    double value = (leftMaster.getSelectedSensorPosition(0)/4096)*(Math.PI*6);
    return value;
  }

  public double getDistanceRight(){
    double value = (leftMaster.getSelectedSensorPosition(0)/4096)*(Math.PI*6);
    return value;
  }
  
  public double angle(){
    return gyro.getAngle();
  }
  
  public double angleNormalized(){ 
    double angle = gyro.getAngle();
    if(angle >= 0) 
      return (angle % 360);
    else
      return 360 - (angle % 360);
  }

  public double navAngle(){
    return ahrs.getAngle();
  }

  public void resetEncoder(){
    leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    leftMaster.setSelectedSensorPosition(0, 0, 10);
  }
  
  public void resetGyro(){
    gyro.reset();
  }

  public void updateOdometry(){

  }
  
  
  @Override
  public void periodic() {

    SmartDashboard.putNumber("Gyro", angleNormalized());
    SmartDashboard.putNumber("Position Encoder", position());
    SmartDashboard.putNumber("navGyro", navAngle());
  }
  
  private void configTalon_Victor(){
    leftMaster.configFactoryDefault();
    rightMaster.configFactoryDefault();
    leftFollow.configFactoryDefault();
    rightFollow.configFactoryDefault();
    
    leftFollow.follow(leftMaster);
    rightFollow.follow(rightMaster);
    
    leftMaster.setInverted(false);
    rightMaster.setInverted(false);

    leftFollow.setInverted(InvertType.FollowMaster);
    rightFollow.setInverted(InvertType.FollowMaster);
    
    try {
      leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
      leftMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 1);
    } catch (Exception e) {
      new PrintCommand("Encoder unavailable!");
      System.out.println();
    }
    
  }
  
}
