package org.usfirst.frc.team2200.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.AnalogInput; 
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeArms {
	private Talon wheelMotor;
	private Talon angleMotor;
	private PIDController pid;
	private AnalogInput pot;
	private short wheelRevMulti;
	private boolean potReverse;
	private double wheelSpeed = 0.0;
	private boolean isLeft;
	double potNew;
	double potVal;

	
	//constructor
	public IntakeArms(int wheelMotorPin, int angleMotorPin, int potpin, double p, double i, double d, short wheelReverseMultiplier, boolean potReverseMultiplier, boolean isLeft){
		wheelMotor = new Talon(wheelMotorPin);
		angleMotor = new Talon(angleMotorPin);
		pot = new AnalogInput(potpin);
		pid = new PIDController(p, i, d, pot, angleMotor); //init PID object with P, I, D, values, with pot data with no default output
		wheelRevMulti = wheelReverseMultiplier; //either 1 or -1 to reverse
		potReverse = potReverseMultiplier; //false for normal, true for reverse
		this.isLeft = isLeft;
	}
	
	//take in PID values
	public void setPID(double p, double i, double d){ //overwrites the P, I, D, values given in constructor to new values
		pid.setPID(p, i, d);
		pid.disable();
		pid.enable(); //restart
	}
	
	//set the desired angle
	public void setSetpoint(double angle){
		if (angle > 0 && angle < 5){ //sanity check
			pid.setSetpoint(angle);
		} else {
			if(isLeft){
				SmartDashboard.putNumber("arm angle left: ", angle);
			} else {
				SmartDashboard.putNumber("arm angle right: ", angle);
			}
		}
	}
	
	//get the setpoint 
	public double getSetpoint(){
		return pid.getSetpoint(); //get current desired position
	}
	
	//get raw pot data
	public double getCurrentVolt(){ //get current position data
		potVal = pot.getVoltage();
		
		if (isLeft){
			SmartDashboard.putNumber("IARM Angle Left Pot: ", potVal);
		} else {
			SmartDashboard.putNumber("IARM Angle Right Pot: ", potVal);
		}
		return potVal;
	}
	
	//set motor speed & direction
	public void setSpeed(double speed){
		if (speed >= -1 && speed <= 1){
			wheelSpeed = speed; //sanity checks
			wheelMotor.set((wheelSpeed) * wheelRevMulti);
		} else {
			System.out.print("ARM : Invalid speed ");
			System.out.println(speed); //debug 
		}
	}
	
	//get motor speed
	public double getSpeed(){
		return wheelSpeed; //get desired motor speed
	}
	
	public void disablePID(){
		pid.disable();
	}
	
	public void enablePID(){
		pid.enable();
	}
	public boolean isPIDDone(){
		double currentval = pot.getVoltage();
		double upperSetPoint = pid.getSetpoint() * 1.05;
		double lowerSetPoint = pid.getSetpoint() * 0.95;
		
		if (currentval < upperSetPoint && currentval > lowerSetPoint){
			return true;
		} else {
			return false;
		}
		
	}
	public void basicWheelMotorSet(double motorVal){
		if (isLeft) {
			wheelMotor.set(motorVal);
		} //top lel
	}
	public void basicAngleMotorSet(double motorVal){
		angleMotor.set(motorVal);
		if (isLeft) {
		} else {
		}
	}
}
