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
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
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
                             rightMaster = new WPI_TalonSRX(DriveConstant.portsMotors[2]),
                             rightFollow = new WPI_TalonSRX(DriveConstant.portsMotors[3]);;
  private final WPI_VictorSPX leftFollow = new WPI_VictorSPX(DriveConstant.portsMotors[1]);
                              

  private final DifferentialDrive drive = new DifferentialDrive(leftMaster, rightMaster);

  private final Gyro gyro = new ADXRS450_Gyro(DriveConstant.Gyro);
  private final AHRS ahrs = new AHRS(SerialPort.Port.kMXP);

  private DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(pathWeaver.kTrackwidthMeters);
  //private Pose2d position = new Pose2d(3.157, -2.361, new Rotation2d(180));
  private Pose2d position = new Pose2d(0, 0, new Rotation2d(0));
  
  private DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(getHeading(), position);

  private SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(pathWeaver.ksVolts, 
                                                                          pathWeaver.kvVoltSecondsPerMeter, 
                                                                          pathWeaver.kaVoltSecondsSquaredPerMeter);

  private PIDController leftPIDController = new PIDController(pathWeaver.kPDriveVel, 0, 0);
  private PIDController rightPIDController = new PIDController(pathWeaver.kPDriveVel, 0, 0);

  //private Pose2d pose;

  public Powertrain() {
    gyro.calibrate();

    configTalon_Victor();

    resetEncoders();
    zeroHeading();

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
  public void setVolts(double leftVolts, double rightVolts) {
    drive.feed();
    leftMaster.set(leftVolts/12);
    rightMaster.set(rightVolts/12);
    /*
    leftMaster.set(ControlMode.PercentOutput, leftVolts / 12); 
    rightMaster.set(ControlMode.PercentOutput, rightVolts / 12);*/
  }

  private double last_world_linear_accel_x;
  private double last_world_linear_accel_y;

  final static double kCollisionThreshold_DeltaG = 0.5f;

  public boolean collision(){
    boolean collisionDetected = false;
          
    double curr_world_linear_accel_x = ahrs.getWorldLinearAccelX();
    double currentJerkX = curr_world_linear_accel_x - last_world_linear_accel_x;

    last_world_linear_accel_x = curr_world_linear_accel_x;

    double curr_world_linear_accel_y = ahrs.getWorldLinearAccelY();
    double currentJerkY = curr_world_linear_accel_y - last_world_linear_accel_y;
    
    last_world_linear_accel_y = curr_world_linear_accel_y;
    
    if ( ( Math.abs(currentJerkX) > kCollisionThreshold_DeltaG ) ||
         ( Math.abs(currentJerkY) > kCollisionThreshold_DeltaG) ) {
        collisionDetected = true;
    }
    SmartDashboard.putBoolean("CollisionDetected", collisionDetected);
    return collisionDetected;
  }

  public double positionLeft() {
    return leftMaster.getSelectedSensorPosition(0);
  }

  public double positionRight() {
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
    //return -leftMaster.getSelectedSensorVelocity(0);
  }

  /**
   * :3
   * 
   * @return La velocidad en metros por segundo
   */
  public double getRateRight() {
    return (((double) rightMaster.getSelectedSensorVelocity(0) * 10) / 4096) * (Units.inchesToMeters(6) * Math.PI);
    //return rightMaster.getSelectedSensorVelocity(0);
  }

  /**
   * Returns the current wheel speeds of the robot.
   *
   * @return The current wheel speeds.
   */
  public DifferentialDriveWheelSpeeds getWheelSpeedss() {
    return new DifferentialDriveWheelSpeeds(getRateLeft(), getRateRight());
  }

  /**
   * Returns the heading of the robot.
   *
   * @return the robot's heading in degrees, from -180 to 180
   */
  public Rotation2d getHeading() {
    return Rotation2d.fromDegrees(Math.IEEEremainder(navAngle(), 360) * (pathWeaver.kGyroReversed ? -1.0 : 1.0));
  }

  /**
   * Resets the odometry to the specified pose.
   *
   * @param pose The pose to which to set the odometry.
   */
  public void resetOdometry(Pose2d pose) {
    resetEncoders();
    odometry.resetPosition(pose, getHeading());
  }

  /**
   * Zeroes the heading of the robot.
   */
  public void zeroHeading() {
    ahrs.reset();
    ahrs.zeroYaw();
  }

  /**
     * Sets the max output of the drive. Useful for scaling the drive to drive more
     * slowly.
     *
     * @param maxOutput the maximum output to which the drive will be constrained
     */
    public void setMaxOutput(double maxOutput) {
      drive.setMaxOutput(maxOutput);
  }

  public SimpleMotorFeedforward getFeedFoward() {
    return feedforward;
  }

  public PIDController getLeftPIDController() {
      return leftPIDController;
  }

  public PIDController getRightPIDController() {
      return rightPIDController;
  }

  public DifferentialDriveKinematics getDifferentialDriveKinematics() {
      return kinematics;
  }

  public Pose2d getPosition() {
      return position;
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
    double operation;

    if (navAngle() >= 0)
      operation = navAngle() % 360;
    else
      operation = 360 - Math.abs(navAngle() % 360);

    return operation;
  }

  public double navAngle() {
    return ahrs.getAngle();
  }

  public void resetEncoders() {
    leftMaster.setSelectedSensorPosition(0);

    rightMaster.setSelectedSensorPosition(0);
  }

  public void resetGyro() {
    ahrs.reset();
  }

  public static double ticksToMeters(double ticks) {
    return ticks * pathWeaver.TICKS_TO_METERS_RATIO;
}

  public void updateOdometry() {
     position = odometry.update(getHeading(), ticksToMeters(leftMaster.getSelectedSensorPosition(0)), 
                                              ticksToMeters(rightMaster.getSelectedSensorPosition(0)));
  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds(){
    return new DifferentialDriveWheelSpeeds(ticksToMeters(leftMaster.getSelectedSensorVelocity(0) * 10),
                                            ticksToMeters(rightMaster.getSelectedSensorVelocity(0) * 10));
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

    SmartDashboard.putNumber("gyro", angleNormalized());
    SmartDashboard.putNumber("encoder_L", getDistanceLeft());
    SmartDashboard.putNumber("encoder_R", getDistanceRight());
    SmartDashboard.putNumber("positionL", positionLeft());
    SmartDashboard.putNumber("positionR", positionRight());
    SmartDashboard.putNumber("rateL", getRateLeft());
    SmartDashboard.putNumber("rateR", getRateRight());
    SmartDashboard.putBoolean("navX-MXP_Calibrated", !ahrs.isCalibrating());

    /* Dashboard.sendDouble("gyro", angleNormalized());
    Dashboard.sendDouble("rateL", getRateLeft());
    Dashboard.sendDouble("rateR", getRateRight());
    Dashboard.sendBoolean("navReady", !ahrs.isCalibrating()); */
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
/*
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
      trajectoryJSON = "paths/Test.wpilib.json"; // Directorio del path numero 1
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
/*
    RamseteCommand ramseteCommand = new RamseteCommand(auto, this::getPose,
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
*/
}
