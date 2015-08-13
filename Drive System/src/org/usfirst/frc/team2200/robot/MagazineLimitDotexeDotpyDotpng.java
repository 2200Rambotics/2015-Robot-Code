package org.usfirst.frc.team2200.robot;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MagazineLimitDotexeDotpyDotpng {
	/* 
	Magazine-drop:
	one motor & 2 limit switches
	(two position)
	(before all the weird changes to the magazine and stuff)
	*/
	
	private Talon magMotor;
	private DigitalInput limitSwitchDown;
	private DigitalInput limitSwitchUp;
	private boolean wantDown;
	public boolean isMagDown;
	public boolean isMagUp;
	private boolean isLsPressedDown;
	private boolean isLsPressedUp;
	double upSpeed = 0.6;
	double downSpeed = -0.3;
//	double upSpeed = 0;
//	double downSpeed = -0;
	
	public MagazineLimitDotexeDotpyDotpng(int pinTalon, int lsPinUp, 
			int lsPinDown) {
		
		this.magMotor = new Talon(pinTalon);
		this.limitSwitchDown = new DigitalInput(lsPinDown);
		this.limitSwitchUp = new DigitalInput(lsPinUp);
		wantDown = false; 
	}
	
	//opens magazine
	public void Up() {
		wantDown = false;
	}
	public void basicMotorSet(double motorVal){
		magMotor.set(motorVal);
		SmartDashboard.putNumber("mag motor val: ", motorVal);
	}
	// closes magazine
	public void Down() {
		wantDown = true;
	}
	
	public void run(){
		
		isLsPressedDown = !limitSwitchDown.get();
		isLsPressedUp = !limitSwitchUp.get();
//		if(isLsPressedDown){
//			SmartDashboard.putString("mag Pos:","Down");
//		}else if(isLsPressedUp){
//			SmartDashboard.putString("mag Pos:","Up");
//		}else{
//			SmartDashboard.putString("mag Pos:","Moving");
//		}
		if (wantDown){
			if (!isLsPressedDown){
				magMotor.set(downSpeed);
				isMagDown = false;
				isMagUp = false;

			} else {
				magMotor.set(0);
				isMagDown = true;
				isMagUp = false;
			}
		} else if (!wantDown){
			if (!isLsPressedUp){
				magMotor.set(upSpeed);
				isMagDown = false;
				isMagUp = false;
			} else {
				magMotor.set (0);
				isMagDown = false;
				isMagUp = true;
			}
		}
	}
	
	// checks where magazine is
//	private boolean checkSwitch(DigitalInput input, boolean polarity) {
//		boolean switchValue = limitSwitchDown.get();
//		if (switchValue == false) {
//			if (polarity == true) {
//				return false;
//			}
//			else {
//				return true;
//			}
//		}
//		
//		else {
//			if (polarity == true) {
//				return true;
//			}
//			
//			else {
//				return false;
//			}
//		}
//	}
}

