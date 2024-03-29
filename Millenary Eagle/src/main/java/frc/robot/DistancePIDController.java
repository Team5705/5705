/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DistancePIDController extends Robot{
 /**
  * Variables
  */
    private double displacement = 0;
    private double sum = 0, distance, errorAn = 0, errorD, pid = 0;
    private long pasado = 0, ahora;
 /**
  * Constants
  */
  private double kP, kI, kD;
  private final double PID_MIN, PID_MAX;
  
  public DistancePIDController(double kP, double kI, double kD, double PID_MIN, double PID_MAX){
    this.kP = kP;
    this.kI = kI;
    this.kD = kD;
    this.PID_MIN = PID_MIN;
    this.PID_MAX = PID_MAX;
    
  }

  public double getPIDValue(double desiredDistance){
      distance = powertrain.distanceInInches();

      ahora = System.currentTimeMillis();

      double cambioTiempo = ahora - pasado;

      if(cambioTiempo >= 1) {
        displacement = desiredDistance - distance; //desiredDistance - currentDistance //error
        double sum = this.sum;

        sum = displacement*1 + sum; //ErrorPass

        errorD = (displacement - errorAn)/1; 
        
        //Ecuacion general para el PID
        pid = (kP * displacement) + (kI * sum) + (kD * errorD);

        pasado = ahora;
        errorAn = displacement;
        
        SmartDashboard.putNumber("P:", (kP * displacement));
        SmartDashboard.putNumber("I:", (kI * sum));
        SmartDashboard.putNumber("D:", (kD * errorD));
        SmartDashboard.putNumber("PID", pid);
        
        
        
        //No permita que el valor de PID aumente mas alla de PID_MAX o por debajo de PID_MIN y
        //Evite la acumulacion de la suma
        if(pid >= PID_MAX) pid = PID_MAX;
        else if(pid <= PID_MIN) pid = PID_MIN;
        else this.sum = sum;
      }   

      return pid;
  }
}
