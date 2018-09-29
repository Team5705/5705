package org.usfirst.frc.team5705.robot.commands;

import org.usfirst.frc.team5705.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class Drive extends Command{

		public Drive(){
		requires(Robot.driveT);
		}

		public void initialize(){
		}

		protected void execute(){
			Robot.driveT.ArcadeDrive(Robot.driver2.getLeftStickY(), Robot.driver2.getLeftStickY());
		}

		protected void end(){
			Robot.driveT.Stop();
		}

		protected void interrupted(){
			end();
		}
		
		
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
