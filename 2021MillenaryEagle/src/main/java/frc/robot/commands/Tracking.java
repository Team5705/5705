/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.PID;
import frc.robot.subsystems.Powertrain;
import frc.robot.subsystems.Vision;

public class Tracking extends CommandBase {
  private final Vision vision;
  private final Powertrain powertrain;
  private static PID pidX = new PID(0.04, 0, 5, 0, 0.27, false); //turn 0.00001dl
  private static PID pidY = new PID(0.05, 0, 8, 0, 0.27, true);
  private boolean finished = false;
  private double x, y;

  /**
   * Ejecuta el seguimiento correspondiente del objetivo.
   * 
   * @param powertrain Subsistema motriz
   * @param vision Subsistema de vision
   */
  public Tracking(Powertrain powertrain, Vision vision) {
    this.powertrain = powertrain;
    this.vision = vision;
    addRequirements(this.powertrain, this.vision);
  }

  /**
   * Ejecuta el seguimiento correspondiente del objetivo.
   * 
   * @param powertrain Subsistema motriz
   * @param vision subsistema de vision
   * @param finished Indica si queremos que el comando termine o no, de ser falso nunca terminará hasta que sea interrumpido,
   *                 de ser verdadero terminará cuando el robot esté alineado con el objetivo.
   */
  public Tracking(Powertrain powertrain, Vision vision, boolean finished) {
    this.powertrain = powertrain;
    this.vision = vision;
    this.finished = finished;

    x = 1;
    y = 1;

    addRequirements(this.powertrain, this.vision);
  }

  @Override
  public void initialize() {
    vision.ledsOn();
  }

  @Override
  public void execute() {
    x = vision.getX();
    y = vision.getY();

    double gyro = powertrain.navAngle();

    pidX.runPIDErr(x);
    pidY.runPIDErr(y);

    if (vision.availableTarget()) {

      double xS = pidY.valuePID();
      double turn = pidX.valuePID();

      powertrain.arcadeDrive(xS, turn);// x*0.03);
    } else
        if (gyro > 0 && gyro <= 180)
          powertrain.arcadeDrive(0, -0.6);
        else if (gyro > 180 && gyro < 360) 
        powertrain.arcadeDrive(0, 0.6);
  }

  @Override
  public void end(boolean interrupted) {
    vision.blinkLeds();
    vision.ledsOff();

    new PrintCommand("FinishedVision");
  }

  @Override
  public boolean isFinished() {
    if (finished == true){
      return (x == 0.00) && (y == 0.00);//(x >= -0.5 && x <= 0.5) && (y >= -0.5 && y <= 0.5);
    } 
    else
      return false;
  }
}
