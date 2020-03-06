/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
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

  private final DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));

  private Pose2d pose;

  public Powertrain() {
    gyro.calibrate();

    resetGyro();
    resetEncoders();

    configTalon_Victor();

    new PrintCommand("Powertrain iniciado");
  }

  /**
   * Hola que hace
   *
   * @param xSp  Velocidad en X
   * @param turn Velocidad en Y
   */
  public void arcadeDrive(double xSp, double turn) {
    drive.arcadeDrive(xSp, turn);
  }

  /**
   * Controls the left and right sides of the drive directly with voltages.
   *
   * @param leftVolts  the commanded left output
   * @param rightVolts the commanded right output
   */
  public void tankDriveVolts(double leftVolts, double rightVolts) {
    leftMaster.setVoltage(leftVolts);
    rightMaster.setVoltage(rightVolts);
    /*
    leftMaster.set(ControlMode.PercentOutput, leftVolts / 12); 
    rightMaster.set(ControlMode.PercentOutput, rightVolts / 12);*/
    drive.feed();
  }

  public int positionLeft() {
    return leftMaster.getSelectedSensorPosition(0);
  }

  public int positionRight() {
    return rightMaster.getSelectedSensorPosition(0);
  }

  /**
   * 7u7
   * 
   * @return La distancia en metros
   */
  public double getDistanceLeft() {
    double value = Units.inchesToMeters(((double) -positionLeft() / 4096) * (Math.PI * 6.00)); // Invertidow
    return value; // en sentido horario del robot marca negativo
  }

  /**
   * UwU
   * 
   * @return La distancia en metros
   */
  public double getDistanceRight() {
    double value = Units.inchesToMeters(((double) positionRight() / 4096) * (Math.PI * 6.00));
    return value;
  }

  /**
   * n_n
   * 
   * @return La velocidad en metros por segundo
   */
  public double getRateLeft() {
    return (((double) -leftMaster.getSelectedSensorVelocity(0) * 10) / 4096) * (Units.inchesToMeters(6) * Math.PI);
  }

  /**
   * :3
   * 
   * @return La velocidad en metros por segundo
   */
  public double getRateRight() {
    return (((double) rightMaster.getSelectedSensorVelocity(0) * 10) / 4096) * (Units.inchesToMeters(6) * Math.PI);
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
    return Math.IEEEremainder(navAngle(), 360) * (pathWeaver.kGyroReversed ? -1.0 : 1.0);
  }

  /**
   * Resets the odometry to the specified pose.
   *
   * @param pose The pose to which to set the odometry.
   */
  public void resetOdometry(Pose2d pose) {
    resetEncoders();
    odometry.resetPosition(pose, Rotation2d.fromDegrees(getHeading()));
  }

  /**
   * Zeroes the heading of the robot.
   */
  public void zeroHeading() {
    ahrs.reset();
  }


  public double aangle() {
    return gyro.getAngle();
  }

  /**
   * T_T
   * 
   * @return Devuelve el angulo del robot normalizado en un rango de 0 a 359
   */
  public double angleNormalized() {
    double angle = navAngle();
    double operation;
    if (angle >= 0)
      operation = (angle % 360);
    else
      operation = 360 - (Math.abs(angle % 360));
    if (operation == 360)
      operation = 0;

    return operation;
  }

  public double navAngle() {
    return ahrs.getAngle();
  }

  public void resetEncoders() {
    leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    leftMaster.setSelectedSensorPosition(0, 0, 10);

    rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    rightMaster.setSelectedSensorPosition(0, 0, 10);
  }

  public void resetGyro() {
    ahrs.reset();
  }

  public void updateOdometry() {
    pose = odometry.update(Rotation2d.fromDegrees(getHeading()), getDistanceLeft(), getDistanceRight());
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
    SmartDashboard.putNumber("PositionL", positionLeft());
    SmartDashboard.putNumber("PositionR", positionRight());
    SmartDashboard.putNumber("RateL", getRateLeft());
    SmartDashboard.putNumber("RateR", getRateRight());
    SmartDashboard.putNumber("navGyro", navAngle());
    SmartDashboard.putBoolean("navX-MXP_Calibrated", !ahrs.isCalibrating());
  }

  private void configTalon_Victor() {
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
      new PrintCommand("Encoders unavailables!");
      System.out.println();
    }

  }

  public Command odometryCommand(int pathNumber) {
    // Create a voltage constraint to ensure we don't accelerate too fast
    var autoVoltageConstraint = new DifferentialDriveVoltageConstraint(
                    new SimpleMotorFeedforward(pathWeaver.ksVolts,
                                               pathWeaver.kvVoltSecondsPerMeter, 
                                               pathWeaver.kaVoltSecondsSquaredPerMeter), 
                                pathWeaver.kDriveKinematics, 10);

    // Create config for trajectory
    TrajectoryConfig config = new TrajectoryConfig(pathWeaver.kMaxSpeedMetersPerSecond,
                                                   pathWeaver.kMaxAccelerationMetersPerSecondSquared)
            // Add kinematics to ensure max speed is actually obeyed
            .setKinematics(pathWeaver.kDriveKinematics)
            // Apply the voltage constraint
            .addConstraint(autoVoltageConstraint);

    // An example trajectory to follow.  All units in meters.
    Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
        // Start at the origin facing the +X direction
        new Pose2d(0, 0, new Rotation2d(0)),
        // Pass through these two interior waypoints, making an 's' curve path
        List.of(
            new Translation2d(1, 1),
            new Translation2d(2, -1)
        ),
        // End 3 meters straight ahead of where we started, facing forward
        new Pose2d(3, 0, new Rotation2d(0)),
        // Pass config
        config
    );
     
    Trajectory auto;
    String trajectoryJSON;

    switch (pathNumber) {
    case 1:
      trajectoryJSON = "paths/post1.wpilib.json"; // Directorio del path numero 1
      break;

    case 2:
      trajectoryJSON = ""; // Directorio del path numero 2
      break;

    default:
      trajectoryJSON = "";
      break;
    }

    try {
      Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
      auto = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
    } catch (IOException ex) {
      DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON, ex.getStackTrace());
      auto = null;
    }

    RamseteCommand ramseteCommand = new RamseteCommand(exampleTrajectory, this::getPose,
        new RamseteController(pathWeaver.kRamseteB, 
                              pathWeaver.kRamseteZeta),
                              new SimpleMotorFeedforward(pathWeaver.ksVolts, 
                                                         pathWeaver.kvVoltSecondsPerMeter,
                                                         pathWeaver.kaVoltSecondsSquaredPerMeter),
                        pathWeaver.kDriveKinematics, 
                        this::getWheelSpeeds, 
              new PIDController(pathWeaver.kPDriveVel, 0, 0),
              new PIDController(pathWeaver.kPDriveVel, 0, 0),
        // RamseteCommand passes volts to the callback
        this::tankDriveVolts, this);

      
    if(auto == null)
    return null;
    else
    // Run path following command, then stop at the end.
    return ramseteCommand.andThen(() -> this.tankDriveVolts(0, 0));
  }

}
