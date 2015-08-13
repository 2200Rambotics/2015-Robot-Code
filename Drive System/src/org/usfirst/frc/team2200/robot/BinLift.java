package org.usfirst.frc.team2200.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PIDController;

public class BinLift {
	Talon liftMotor;
	AnalogInput liftSensor;
	private boolean configure;
	private PIDController pid;
	public int desiredPosNum = 4;
	//expected val from pot for each pos 5 is top
	double expectedPotPos5 = 0.304;
	double expectedPotPos4 = 0.623;
	double expectedPotPos3 = 1.147;
	double expectedPotPos2 = 1.812;
	double expectedPotPos1 = 2.545;
	boolean isNew;
	double min;
	double max;


	
	
//Constructor
	public BinLift(int motorPin, int potPin, double expectedPotPos5, double expectedPotPos4, double expectedPotPos3, 
			double expectedPotPos2, double expectedPotPos1, double p, double i, double d,boolean isNew){
		if (motorPin <= 9 && motorPin >= 0){
			if(isNew){
				min = 2.910;
				max = 0.999;
			} else {
				min = 4.456;
				max = 2.159;
			}
			liftMotor = new Talon(motorPin);
			liftSensor = new AnalogInput(potPin);
			this.expectedPotPos5 = expectedPotPos5;
			this.expectedPotPos4 = expectedPotPos4;
			this.expectedPotPos3 = expectedPotPos3;
			this.expectedPotPos2 = expectedPotPos2;
			this.expectedPotPos1 = expectedPotPos1;
			pid = new PIDController(p, i, d, liftSensor, liftMotor);
			this.isNew = isNew;
			configure = true;
			pid.setSetpoint(this.expectedPotPos4);
	
		}
		else{
			//TODO a warning to send a better value
			String smokeWeed = "everyday";
			useVar(smokeWeed);
		}
		
		
		
	}
	private void useVar(String var){
		
	}
	public void setSetPoint(double selected){
		
		pid.setSetpoint(selected);
	
	}
	//get the setpoint 
	public double getSetpoint(){
		return pid.getSetpoint(); //get current desired position
	}
		
	public void setPID(double p, double i, double d)	{
		pid.setPID(p, i, d);
		pid.disable();
		pid.enable();
	}
	public void disablePID(){
		pid.disable();
	}
	
	public void enablePID(){
		pid.enable();
	}
	
	public boolean isPIDDone(){
		double currentval = liftSensor.getVoltage();
		double upperSetPoint = pid.getSetpoint() * 1.05;
		double lowerSetPoint = pid.getSetpoint() * 0.95;
		
		if (currentval < upperSetPoint && currentval > lowerSetPoint){
			return true;
		} else {
			return false;
		}
		
	}
	
	public boolean isWithinPercOfPos(double decimalPercent, int pos){
		switch(pos){
		case 1:
			return !(liftSensor.getVoltage() < expectedPotPos1 * (1.0 - decimalPercent));
		case 2:
			return !((liftSensor.getVoltage() < expectedPotPos2 * (1.0 - decimalPercent)) && (liftSensor.getVoltage() > expectedPotPos2 * (1.0 + decimalPercent)));
		case 3:
			return !((liftSensor.getVoltage() < expectedPotPos3 * (1.0 - decimalPercent)) && (liftSensor.getVoltage() > expectedPotPos3 * (1.0 + decimalPercent)));
		case 4:
			return !((liftSensor.getVoltage() < expectedPotPos4 * (1.0 - decimalPercent)) && (liftSensor.getVoltage() > expectedPotPos4 * (1.0 + decimalPercent)));
		case 5:
			return !((liftSensor.getVoltage() < expectedPotPos5 * (1.0 - decimalPercent)) && (liftSensor.getVoltage() > expectedPotPos5 * (1.0 + decimalPercent)));
		default:
			return false;
		}
	}
	
	public boolean isBelowPos(int pos){
		switch(pos){
		case 1:
			return (liftSensor.getVoltage() > expectedPotPos1);
		case 2:
			return (liftSensor.getVoltage() > expectedPotPos2);
		case 3:
			return (liftSensor.getVoltage() > expectedPotPos3);
		case 4:
			return (liftSensor.getVoltage() > expectedPotPos4);
		case 5:
			return (liftSensor.getVoltage() > expectedPotPos5);
		default:
			return false;
		}
	}
	public void setPosition(int desiredPos){
		if (configure == true){
			//compare position of the pot to the position that the method has been sent
			 checkPosition();
			 if (desiredPos == 5){
					pid.setSetpoint(expectedPotPos5);
					desiredPosNum = desiredPos;
				 }
			 if (desiredPos == 4){
				pid.setSetpoint(expectedPotPos4);
				desiredPosNum = desiredPos;
			 }
			 if (desiredPos == 3){
					pid.setSetpoint(expectedPotPos3);
					desiredPosNum = desiredPos;
			 }
			 if (desiredPos == 2){
					pid.setSetpoint(expectedPotPos2);
					desiredPosNum = desiredPos;
			 }
			 if (desiredPos == 1){
					pid.setSetpoint(expectedPotPos1);
					desiredPosNum = desiredPos;
			 }
				 	 
			 
		}
	}
	
	public void basicMotorSet(double motorVal){
		liftMotor.set(motorVal);
		SmartDashboard.putNumber("blft motor val: ", motorVal);
	}
	public int getDesiredPosition(){
		return desiredPosNum;
	}
	public void checkPosition(){
		double currentPos = liftSensor.getVoltage();
		SmartDashboard.putNumber("BLFT Pot", currentPos);
	}

	public double calculateBinSetpoint(double joyZ, boolean isManly){ //xaxis should swing arms left and right, 
		if (!isManly) {

			double defaultPosition = (min + max)/2; //middle
			double multiZ = min - defaultPosition;
			double output;

			output = defaultPosition + (joyZ * multiZ);
			
			if (output < max) { //smart sanity check
				return max;
			} 
			
			else if (output > min) {
				return min;
			} 
			
			else {
				return output;
			}
		} 

		else {
			return joyZ;
		}
	} 
}

