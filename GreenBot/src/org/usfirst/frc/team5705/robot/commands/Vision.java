package org.usfirst.frc.team5705.robot.commands;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Vision extends Command {
	
	VideoCapture camera = new VideoCapture(0);
	Mat frame = new Mat();
	Mat frame_two = new Mat();
	Mat frame_three = new Mat();
	

    public Vision() {
    }

    protected void initialize() {
    }

    protected void execute() {
    	camera.read(frame);
    	Imgproc.cvtColor(frame, frame_two, Imgproc.COLOR_BGR2HSV);
    	Core.inRange(frame_two, new Scalar(0, 0, 113), new Scalar(146, 255, 255), frame_three);
    	
    	List<MatOfPoint> contour = new ArrayList<MatOfPoint>();
    	Mat hierarchy = new Mat();
    	Imgproc.findContours(frame_three, contour, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);
    	for (int i = 0; i < contour.size(); i++) {
    	        //...contour code here...
    	}
    	
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
