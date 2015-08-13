package org.usfirst.frc.team2200.robot;

import com.ni.vision.NIVision;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionAuto {
	private VisionCode vcl;
//	private VisionCode vcr;
	double camDelayTime = 10;
	public VisionAuto(){
		vcl = new VisionCode("cam0");
//    	vcr = new VisionCode("cam2");
	}
	
	public void hammel(){
		double starttime = Timer.getFPGATimestamp();
		SmartDashboard.putNumber("start time: ", starttime);
		while ((Timer.getFPGATimestamp() - starttime) < 100){
			vcl.doVisionStuff();
			SmartDashboard.putNumber("xLeft", vcl.xLeft);
			SmartDashboard.putNumber("xRight", vcl.xRight);
			
			
		}
		
	}
	
	public void threeTote(){
		
	}

	void calcAngle(){
		
	}
	
	public VisionCode getcamObj(){
		return vcl;
	}
}
