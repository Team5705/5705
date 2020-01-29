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
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstant;
import frc.robot.Constants.pathWeaver;


public class Powertrain extends SubsystemBase { 
  private final WPI_TalonSRX leftMaster = new WPI_TalonSRX(DriveConstant.portsMotors[0]),
                             rightMaster = new WPI_TalonSRX(DriveConstant.portsMotors[2]);
  private final WPI_VictorSPX leftFollow = new WPI_VictorSPX(DriveConstant.portsMotors[1]),
                              rightFollow = new WPI_VictorSPX(DriveConstant.portsMotors[3]);

  private final DifferentialDrive drive = new DifferentialDrive(leftMaster, rightMaster);

  private final Gyro gyro = new ADXRS450_Gyro(DriveConstant.Gyro);
  private final AHRS ahrs = new AHRS(SerialPort.Port.kMXP);

  private final DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(new Rotation2d(0));
  
  public Powertrain() {
    gyro.calibrate();

    configTalon_Victor();
    
    new PrintCommand("Powertrain iniciado");
  }
  
  /**
   * Hola que hace
   *
   * @param xSp Velocidad en X
   * @param turn Velocidad en Y
   */
  public void arcadeDrive(double xSp, double turn){
    drive.arcadeDrive(xSp, turn);
    drive.feed();
  }

   /**
   * Controls the left and right sides of the drive directly with voltages.
   *
   * @param leftVolts  the commanded left output
   * @param rightVolts the commanded right output
   */
  public void tankDriveVolts(double leftVolts, double rightVolts) {
    leftMaster.setVoltage(leftVolts);
    rightMaster.setVoltage(-rightVolts);
    drive.feed();
  }

  public int position(){
    return leftMaster.getSelectedSensorPosition(0);
  }

  /**
   * 7u7
   * 
   * @return La distancia en metros
   */
  public double getDistanceLeft(){
    double value = Units.inchesToMeters((leftMaster.getSelectedSensorPosition(0)/4095)*(Math.PI*6));
    return value;
  }

  /**
   * UwU
   * 
   * @return La distancia en metros
   */
  public double getDistanceRight(){
    double value = Units.inchesToMeters((rightMaster.getSelectedSensorPosition(0)/4095)*(Math.PI*6));
    return value;
  }

  public double getRateLeft(){
    return ((leftMaster.getSelectedSensorVelocity(0)*10)/4095)*(Units.inchesToMeters(6)); //Velocidad en metros por segundo
  }

  public double getRateRight(){
    return ((rightMaster.getSelectedSensorVelocity(0)*10)/4095)*(Units.inchesToMeters(6)); //Velocidad en metros por segundo
  }

  /**
   * Returns the current wheel speeds of the robot.
   *
   * @return The current wheel speeds.
   */
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(getRateLeft(), getRateRight());
  }

   /**
   * Returns the heading of the robot.
   *
   * @return the robot's heading in degrees, from -180 to 180
   */
  public double getHeading() {
    return Math.IEEEremainder(gyro.getAngle(), 360) * (pathWeaver.kGyroReversed ? -1.0 : 1.0);
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

  public void resetEncoders(){
    leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    leftMaster.setSelectedSensorPosition(0, 0, 10);

    rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    rightMaster.setSelectedSensorPosition(0, 0, 10);
  }
  
  public void resetGyro(){
    gyro.reset();
  }

  public void updateOdometry(){
    odometry.update(Rotation2d.fromDegrees(getHeading()), getDistanceLeft(),
                      getDistanceRight());
  }
  
  /**
   * Returns the currently-estimated pose of the robot.
   *
   * @return The pose.
   */
  public Pose2d getPose() {
    return odometry.getPoseMeters();
  }

  @Override
  public void periodic() {
    updateOdometry();

    SmartDashboard.putNumber("Gyro", angleNormalized());
    SmartDashboard.putNumber("Encoder_L", getDistanceLeft());
    SmartDashboard.putNumber("Encoder_R", getDistanceRight());
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

      rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
      rightMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 1);
    } catch (Exception e) {
      new PrintCommand("Encoder unavailable!");
      System.out.println();
    }
    
  }
  
}