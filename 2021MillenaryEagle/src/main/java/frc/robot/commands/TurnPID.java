/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.PID;
import frc.robot.subsystems.Powertrain;

public class TurnPID extends CommandBase {
  private final Powertrain powertrain;

  private static PID pidTurn;

  double gyro;
  double angle;

  double kP = 0.03, kI = 0.00001, kD = 9, bias = 0.27;

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
   * @param angle Angulo deseado entre los 360 grados del robot, el giroscopio
   *              no se reinicia.
   */
  public TurnPID(Powertrain powertrain, int angle) {
    this.powertrain = powertrain;
    this.angle = angle;
    pidTurn = new PID(kP, kI, kD, angle, bias, false);

    addRequirements(this.powertrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //powertrain.resetGyro();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    gyro = powertrain.navAngle();

    pidTurn.runPID(gyro);

    double turn = pidTurn.valuePID();

    /* err = (angle - gyro);

    errI = (err * T) + errP;

    errD = (err - errPp) / T;

    errP = errI;
    errPp = err;

    /****************************************
     * PID
     ***************************************

    PID = (err * kP) + (errI * kI) + (errD * kD); */

    powertrain.arcadeDrive(0, turn);

    // SmartDashboard.putNumber("PID", PID);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (gyro >= (angle-1) && gyro <= (angle +1));
  }
}
