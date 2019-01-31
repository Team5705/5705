/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;



import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  Vision _vision;
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
  
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    System.out.println();
    System.out.println();
    System.out.println("******************************************************************");
    System.out.println("******************************************************************");
    System.out.println("************** Welcome user, this is the test robot **************");
    System.out.println("******************************************************************");
    System.out.println("******************************************************************");
    System.out.println();
    System.out.println();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
public void teleopInit() {
    _vision = new Vision();
    new Thread(_vision).start();
    
}
  @Override
  public void teleopPeriodic() {
    

  }

  @Override
  public void disabledInit() {
    _vision = new Vision();
    new Thread(_vision).interrupt();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    
  }
  class Vision implements Runnable {
    
    public void run() {

      Mat frame = new Mat(); 
      Mat frame1 = new Mat();
      Mat frame2 = new Mat();
      Mat frame3 = new Mat();
      VideoCapture camera = new VideoCapture(0);
      Mat hierarchy = new Mat();

      while (true) {
        
      camera.read(frame);
      Imgproc.cvtColor(frame, frame1, Imgproc.COLOR_BGR2HSV);
      Core.inRange(frame1, new Scalar(13, 3, 221), new Scalar(173, 255, 255), frame2);
      
      List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
      
      Imgproc.findContours(frame2, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);
      for (int i = 0; i < contours.size(); i++) {
        frame3 = contours.get(i);
      }
      Rect boundRect = Imgproc.boundingRect(frame3);
      double centerX = boundRect.x + (boundRect.width / 2);
      double centerY = boundRect.y + (boundRect.height / 2);
      
      SmartDashboard.putNumber("x", centerX);
      SmartDashboard.putNumber("y", centerY);
      }
    }
  }
  
  
  }
}