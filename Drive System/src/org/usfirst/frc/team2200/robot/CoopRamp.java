package org.usfirst.frc.team2200.robot;
import edu.wpi.first.wpilibj.Servo;


public class CoopRamp {
	private Servo leftFlap;
	private Servo rightFlap;
	private double upAngleL;
	private double upAngleR;
	private double downAngleL;
	private double downAngleR;
	
	
	public CoopRamp(int leftPin, int rightPin, double upAngleL, double upAngleR,
			double downAngleL, double downAngleR)	{
		leftFlap = new Servo(leftPin);
		rightFlap = new Servo(rightPin);
		this.upAngleL = upAngleL;
		this.upAngleR = upAngleR;
		this.downAngleL = downAngleL;
		this.downAngleR = downAngleR;
	}
	
	public void up()	{
		leftFlap.set(upAngleL);
		rightFlap.set(upAngleR);
	}
	
	public void down()	{
		leftFlap.set(downAngleL);
		rightFlap.set(downAngleR);
	}
	
	

}
