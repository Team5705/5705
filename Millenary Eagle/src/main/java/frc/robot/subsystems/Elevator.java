/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.ManualElevator;

/**
 * Add your docs here.
 */
public class Elevator extends Subsystem {
  WPI_TalonSRX pedrito = null;
  //wpi_ marquito = null;

  DigitalInput _lim1 = null, _lim2 = null; 

  public Elevator(){
    pedrito = new WPI_TalonSRX(RobotMap.elevatorleftmotor);
    //marquito = new Spark(RobotMap.elevatorrightmotor);

    _lim1 = new DigitalInput(RobotMap._lim1);
    _lim2 = new DigitalInput(RobotMap._lim2);

    pedrito.configFactoryDefault();
    pedrito.setInverted(false);
    pedrito.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 40,10);
    pedrito.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
  }

  public int position(){
    return -pedrito.getSelectedSensorPosition(0);
  }

  public void manualElevator(double speed){
    pedrito.set(ControlMode.PercentOutput, speed);
    //marquito.set(speed);
  }

  public boolean limitDown(){
    return _lim1.get();
  }
  
  public boolean limitUp(){
    return _lim2.get();
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ManualElevator());
  }
}
