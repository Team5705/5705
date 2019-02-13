/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Gripper extends Subsystem {
  public WPI_TalonSRX juanito = null;
  WPI_VictorSPX carlitos = null;

  Solenoid gripp = null, move = null;

  DigitalInput _lim3 = null;

  public Gripper() {
    carlitos = new WPI_VictorSPX(RobotMap.cargogripper_motor);
    juanito = new WPI_TalonSRX(RobotMap.cargogripper_motormove);

    gripp = new Solenoid(RobotMap.hatchpgripper);
    move = new Solenoid(RobotMap.hatchpgripper_move);

    _lim3 = new DigitalInput(RobotMap._lim3);

    carlitos.configFactoryDefault();
    juanito.configFactoryDefault();

    carlitos.setInverted(false);
    juanito.setInverted(false);

    initQuadrature();


    juanito.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,	// Feedback
                                                              0, 								// PID ID
                                                              20);								// Timeout


  }

  public double gripperAngle(){
    int position = juanito.getSelectedSensorPosition(0);
    return ToDeg(position);
  }

  public void stopMotor(){
    carlitos.set(ControlMode.PercentOutput, 0.0);
  }

  public void takeCargo(){
    carlitos.set(ControlMode.PercentOutput, 0.75);
  }

  public void dropCargo(){
    carlitos.set(ControlMode.PercentOutput, -0.75);
  }

  public boolean limit(){
    return _lim3.get();
  }

  public void moveGRIPPER(double speed){
    juanito.set(ControlMode.PercentOutput, speed);
  }

  public void takeHatchPanel(){
    gripp.set(false);
  }

  public void dropHatchPanel(){
    gripp.set(true);
  }

  public void takeOutHatchGripper(){
    move.set(true);
  }

  public void hideHatchGripper(){
    move.set(false);
  }

  double ToDeg(int units) {
    double deg = units * 360.0 / 4096.0;

    /* truncate to 0.1 res */
    deg *= 10;
    deg = (int) deg;
    deg /= 10;

    return deg;
}

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void initQuadrature() {
		/* get the absolute pulse width position */
		int pulseWidth = Robot.elevator.pedrito.getSensorCollection().getPulseWidthPosition();

		/**
		 * If there is a discontinuity in our measured range, subtract one half
		 * rotation to remove it
		 */
		if (true/*kDiscontinuityPresent*/) {

			/* Calculate the center */
			int newCenter;
      newCenter = (910/*kBookEnd_0*/ + 1137/*kBookEnd_1*/) / 2;
			newCenter &= 0xFFF;

			/**
			 * Apply the offset so the discontinuity is in the unused portion of
			 * the sensor
			 */
			pulseWidth -= newCenter;
		}

		/**
		 * Mask out the bottom 12 bits to normalize to [0,4095],
		 * or in other words, to stay within [0,360) degrees 
		 */
		pulseWidth = pulseWidth & 0xFFF;

		/* Update Quadrature position */
		juanito.getSensorCollection().setQuadraturePosition(pulseWidth, 20);
	}
}
