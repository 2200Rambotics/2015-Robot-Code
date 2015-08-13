package org.usfirst.frc.team2200.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PIDController;

public class ToteLift {
	
	//objects created
	Talon motorLift;
	AnalogInput pot;
	DigitalInput infra;
	double potData;
	boolean infraData;
	boolean moving;
	double expGround;
	double expTwo;
	double expBelowMag;
	double expHardStop;
	double expTop;
	PIDController pid;
	int currentPos;

	public ToteLift(int pinTalon, int pinAnalog, int infraLPin, int infraRPin, double p, double i, double d, 
			double expGround, double expFirst, double expBelowMag, double expSecond, double expHardStop){

		//objects defined
		motorLift = new Talon(pinTalon);
		pot = new AnalogInput(pinAnalog);
		infra = new DigitalInput(infraLPin);
		pid = new PIDController(p, i, d, pot, motorLift);
		this.expGround = expGround;
		this.expTwo = expFirst;
		this.expBelowMag = expBelowMag;
		this.expTop = expSecond;
		this.expHardStop = expHardStop;
		pid.setSetpoint(expGround);

		
		//runs method to get pot data
		checkSensors();
		gotoExpGround();
	}
	public double getSetpoint(){
		return pid.getSetpoint(); //get current desired position
	}
	public void basicMotorSet(double motorVal){
		motorLift.set(motorVal);
		SmartDashboard.putNumber("tote motor val: ", motorVal);
	}
	public double getPotPos(){
		return pot.getVoltage();
	}
	
	public void checkSensors(){
		
		//checks pot data and displays to SmartDashboard
		potData = pot.getVoltage();
		infraData = infra.get();
		SmartDashboard.putNumber("TLFT Pot: ", potData);
		SmartDashboard.putBoolean("TLFT Infra: ",infraData);
		
		
	}
	private void setSetPoint(double selected){
		
		pid.setSetpoint(selected);
	
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
	
	public int getPosition(){
		return currentPos;
	}
	public void gotoExpGround(){
		setSetPoint(expGround);
		currentPos = 1;
	}
	
	
	public void gotoExpHardHardHardHardHardStop(){
		setSetPoint(expHardStop);
		currentPos = 1;
	}
	public void gotoExpMiddle(){
		setSetPoint(expTwo);
		currentPos = 2;
	}
	
	public void gotoExpBelowMag(){
		setSetPoint(expBelowMag);
		currentPos = 3;
	}
	
	public void gotoExpTop(){
		setSetPoint(expTop);
		currentPos = 3;
	}
	public void setPID(double p, double i, double d)	{
		pid.setPID(p, i, d);
	}

	    }
	    
