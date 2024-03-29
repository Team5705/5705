/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class VisionPIDController {

    private double error = 0;
    private double sum = 0, rate = 0, cX, errorAn = 0, errorD, pid = 0;
    private long pasado = 0, ahora;
    
    private double kP, kI, kD;
      private final double PID_MIN, PID_MAX;

      public VisionPIDController(double kP, double kI, double kD, double PID_MIN, double PID_MAX){
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.PID_MIN = PID_MIN;
        this.PID_MAX = PID_MAX;
    }
    
    public double getPIDValue(int desiredPixel){
        cX = Robot.center;
  
        ahora = System.currentTimeMillis();
  
        double cambioTiempo = ahora - pasado;
  
        
        if (cambioTiempo >= 1) {
          error = desiredPixel - cX; // desiredDistance - currentDistance //error
          double sum = this.sum;
  
          sum = error*1 + sum; //ErrorPass
  
          errorD = (error - errorAn)/1; 
          
          //Ecuacion general para el PID
          pid = (kP *error) + (kI * sum) + (kD * rate);
          
          pasado = ahora;
          errorAn = error;
          
          SmartDashboard.putNumber("P:", (kP * error));
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
