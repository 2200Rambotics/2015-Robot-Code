package org.usfirst.frc.team2200.robot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PIDController;

public class Claw {
	public Talon clawMotor;
//	private AnalogInput clawPot;
	private DigitalInput clawLimitClose;
	private DigitalInput clawLimitOpen;
	private DigitalInput clawProc;
//	private double potVoltage;
//	private boolean potReverse;
//	private double expectedOpenPos;
//	private double expectedMiddlePos;
//	private double expectedClosedPos;
//	private PIDController pid;
	private boolean wantOpen;
	private boolean wantMiddle;
	private boolean isLsPressedOpen;
	private boolean isLsPressedClose;
	private boolean isLsPressedMiddle; //hold sensor data, is proc sensor

	private double closeSpeed = 1;
	private double openSpeed = -1;
	private double middleSpeed;
	public boolean isClawClose;
	public boolean isClawOpen;
	public boolean isClawMiddle; //fip fop sort of 
	public boolean blahBlahBlah = false;
	
	//expected values from pot for wach pos
	
	public Claw(int motorPin, int potPin, int limitPinClose, int limitPinOpen,int proc, double openPos, double closedPos, double middlePos,
			double p, double i, double d)	{

		clawMotor = new Talon(motorPin);
//		clawPot = new AnalogInput(potPin);
		clawLimitClose = new DigitalInput(limitPinClose);
		clawLimitOpen = new DigitalInput(limitPinOpen);
		clawProc = new DigitalInput(proc);
//		pid = new PIDController(p, i, d, clawPot, clawMotor);
//		this.expectedOpenPos = openPos;
//		this.expectedClosedPos = closedPos;
//		expectedMiddlePos = middlePos;
//		potReverse = false;
		isLsPressedClose = !clawLimitClose.get();
		isLsPressedOpen = !clawLimitOpen.get();
		if (isLsPressedClose){
			close();
			blahBlahBlah = false;
		}else if(isLsPressedOpen){
			open();
			blahBlahBlah = false;
		}else{
			open();
			blahBlahBlah = false;
		}
		run();
		blahBlahBlah = true;
	}
	
//	public void setPID(double p, double i, double d)	{
//		pid.setPID(p, i, d);
//		pid.disable();
//		pid.enable();
//	}
//	
//	public void reverseMotorDontUseThis(boolean isReversed)	{
//		if (isReversed){
//			potReverse = true;
//		}
//		else	{
//			potReverse = false;
//		}
//	}
//	
//	public void open()	{
//		double desiredPos;
//		if (potReverse)	{
//			desiredPos = 5 - expectedOpenPos;
//		} else {
//			desiredPos = expectedOpenPos;
//		} //screw sanity
//		pid.setSetpoint(desiredPos);
//		SmartDashboard.putString("Claw Position", "OPEN");
//	}
//	
//	public void middle(){
//		double desiredPos;
//		if (potReverse)	{
//			desiredPos = 5 - expectedMiddlePos;
//		} else {
//			desiredPos = expectedMiddlePos;
//		} //screw sanity
//		pid.setSetpoint(desiredPos);
//		SmartDashboard.putString("Claw Position", "MIDDLE");
//		
//	}
//	
//	public void close()	{
//		double desiredPos;
//		if (potReverse)	{
//			desiredPos = 5 - expectedClosedPos;
//		} else {
//			desiredPos = expectedClosedPos;
//		}
//		pid.setSetpoint(desiredPos);
//		SmartDashboard.putString("Claw Position", "CLOSED");
//	}
	
	//get the setpoint 
//	public double getSetpoint(){
//		return pid.getSetpoint(); //get current desired position
//	}
//		
//	public void disablePID(){
//		pid.disable();
//	}
//	
//	public void enablePID(){
//		pid.enable();
//	}
//	
//	public boolean isPIDDone(){
//		double currentval = clawPot.getVoltage();
//		double upperSetPoint = pid.getSetpoint() * 1.05;
//		double lowerSetPoint = pid.getSetpoint() * 0.95;
//		
//		if (currentval < upperSetPoint && currentval > lowerSetPoint){
//			return true;
//		} else {
//			return false;
//		}
//		
//	}

	
	public boolean checkLimitClose()	{
		boolean limitValue = clawLimitClose.get();
		SmartDashboard.putBoolean("CLAW Limit", limitValue);
		return limitValue;
	}
	public boolean checkLimitOpen()	{
		boolean limitValue = clawLimitOpen.get();
		SmartDashboard.putBoolean("CLAW Limit", limitValue);
		return limitValue;
	}
	public boolean checkSwitchMiddle()	{
		boolean switchValue = clawProc.get();
		SmartDashboard.putBoolean("CLAW Proc", switchValue);
		return switchValue;
	}
	public void close() {
		wantOpen = false;

	}
	public void open() {
		wantOpen = true;
	}

	
	
	public void run(){
		isLsPressedClose = !clawLimitClose.get();
		isLsPressedOpen = !clawLimitOpen.get();
		isLsPressedMiddle = !clawProc.get();
		if (isLsPressedClose){
			SmartDashboard.putString("Claw Position: ", "Closed");	
		}else if (isLsPressedOpen){
			SmartDashboard.putString("Claw Position: ", "Open");	
		}else if (isLsPressedMiddle){
			SmartDashboard.putString("Claw Position: ", "Middle");	
		}else{
			SmartDashboard.putString("Claw Position: ", "Moving");	
		}
		SmartDashboard.putBoolean("Claw Ls Close: ", isLsPressedClose);
		SmartDashboard.putBoolean("Claw Ls Open: ", isLsPressedOpen);
		SmartDashboard.putBoolean("Claw Ls Middle: ", isLsPressedMiddle);
		if (!blahBlahBlah) {
			if (wantOpen) {
				if (!isLsPressedOpen && !isLsPressedMiddle) {
					clawMotor.set(openSpeed);
					isClawClose = false;
					isClawOpen = false;

				} else {
					clawMotor.set(0);
					isClawOpen = true;
					isClawClose = false;

				}
			} else { //if want to close
				if (!isLsPressedClose) {
					middleSpeed = 1;
					clawMotor.set(closeSpeed);
					isClawClose = false;
					isClawOpen = false;

				} else {
					clawMotor.set(0);
					isClawOpen = false;
					isClawClose = true;

				}
			}
		} else {
			clawMotor.set(0);
		}
	}
	
	public void basicMotorSet(double motorVal){
		clawMotor.set(motorVal);
		SmartDashboard.putNumber("claw motor val: ", motorVal);
	}
	


}
