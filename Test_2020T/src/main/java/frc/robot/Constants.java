/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.SPI.Port;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class DriveConstant {        
        public static final int[] portsMotors = new int[]{3, 5,     //leftMaster(SRX) / leftFollow
                                                          4, 6};    //rightMaster / leftFollow

        public static final Port Gyro = Port.kOnboardCS0;
    }

    public static final class OIConstant {
        public static final int controllerPort = 0;
    }
    
    public static final class Intake {
        public static final int m1 = 2;
        public static final int[] sensors = new int[]{0 ,1};
    }
}
