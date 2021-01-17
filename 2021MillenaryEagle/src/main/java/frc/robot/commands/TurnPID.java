/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Powertrain;

public class TurnPID extends CommandBase {
  private final Powertrain powertrain;

  double gyro;

  double angle;

  double T = 20;
  double err = 0;
  double errI = 0; // Integral
  double errD = 0;
  double errPp = 0;

  double errP = 0; // Pasado
  // 0.03kp 5.5kd +/- 2 grados chassis solo 0.000003kI
  double kP = 0.03, kI = 0.00001, kD = 9;

  double PID = 0;

  /**
   * unu
   * 
   * @param powertrain     Subsistema motriz
   * @param angle          Angulo deseado teniendo en cuenta que el angulo se
   *                       resetea y un giro inverso es necesario el angulo
   *                       negativo
   */
  public TurnPID(Powertrain powertrain, int angle, double kP, double kI, double kD) {
    this.powertrain = powertrain;
    this.angle = angle;
    this.kP = kP;
    this.kI = kI;
    this.kD = kD;
    addRequirements(this.powertrain);
  }

  /**
   * unu
   * 
   * @param powertrain Subsistema motriz
   * @param angle Angulo deseado teniendo en cuenta que el angulo se
   *                       resetea y un giro inverso es necesario el angulo
   *                       negativo
   */
  public TurnPID(Powertrain powertrain, int angle) {
    this.powertrain = powertrain;
    this.angle = angle;
    addRequirements(this.powertrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    powertrain.resetGyro();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    gyro = powertrain.navAngle();

    err = (angle - gyro);

    errI = (err * T) + errP;

    errD = (err - errPp) / T;

    errP = errI;
    errPp = err;

    /****************************************
     * PID
     ****************************************/

    PID = (err * kP) + (errI * kI) + (errD * kD);

    powertrain.arcadeDrive(0, PID);

    // SmartDashboard.putNumber("PID", PID);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (gyro >= (angle));
  }
}
