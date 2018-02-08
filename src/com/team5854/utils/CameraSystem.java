package com.team5854.utils;

import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

/**
 * Class for implementing a camera system.
 */
public final class CameraSystem implements Runnable {
	CvSink cvSink;
    CvSource outputStream;
    
    Mat source = new Mat();
    Mat output = new Mat();
	public CameraSystem() {
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setResolution(640, 480);
        
        cvSink = CameraServer.getInstance().getVideo();
        outputStream = CameraServer.getInstance().putVideo("CAMERA", 640, 480);
        
        source = new Mat();
        output = new Mat();
	}
	
	public void run() {
		while(!Thread.interrupted()) {
            cvSink.grabFrame(source);
            outputStream.putFrame(source);
        }
	}
	
}