/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {
  private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  private NetworkTable table2 = NetworkTableInstance.getDefault().getTable("CameraPublisher").getSubTable("limelight");
  private NetworkTableEntry tx = table.getEntry("tx");
  private NetworkTableEntry ty = table.getEntry("ty");
  private NetworkTableEntry ta = table.getEntry("ta");
  private NetworkTableEntry tv = table.getEntry("tv");
  private NetworkTableEntry available = table2.getEntry("description");

  public static UsbCamera cam0 = CameraServer.getInstance().startAutomaticCapture(0);

  private boolean availableCamera = false;

  public Vision() {
    ledsOff();
		cam0.setResolution(160, 120);

  }

  public boolean availableLimeLight(){
    String name = available.getString("");
    
    if(name.length() > 0)
      return true;
    else 
      return false;  
  }

  public double getX() {
    return tx.getDouble(0.0);
  }

  public double getY() {
    return ty.getDouble(0.0);
  }

  public double getArea() {
    return ta.getDouble(0.0);
  }

  public boolean availableTarget() {
    if (tv.getDouble(0.0) == 1)
      return true;
    else
      return false;
  }

  public void ledsOff() {
    table.getEntry("ledMode").setNumber(1);
  }

  public void ledsOn() {
    table.getEntry("ledMode").setNumber(3);
  }

  public void blinkLeds() {
    table.getEntry("ledMode").setNumber(2);
  }

  public void blinkLeds2() {
    table.getEntry("ledMode").setNumber(6);
  }

  public void ledsDefault() {
    table.getEntry("ledMode").setNumber(0);
  }

  public void visionProcessorMode() {
    table.getEntry("camMode").setNumber(0);
  }

  public void driverCameraMode() {
    table.getEntry("camMode").setNumber(1);
  }

  public void selectPipeline(int pipeline) {
    table.getEntry("pipeline").setNumber((double) pipeline);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("X", getX());
    SmartDashboard.putNumber("Y", getY());
    SmartDashboard.putNumber("Area", getArea());

    if(availableLimeLight() && !availableCamera){
      availableCamera = true;
      ledsOff();
    }
  }
}
