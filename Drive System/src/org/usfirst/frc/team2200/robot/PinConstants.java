package org.usfirst.frc.team2200.robot;

public class PinConstants {
	/* 
	 * iarms = Intake Arms
	 * tlft = Tote Lift
	 * blft = Bin Lift
	 * drvs = Drive System
	 * claw = Claw
	 * coop = Co-Opertition
	 * mag = magazine
	 * L = Left
	 * R = Right
	 * Whl = Wheel
	 * Ang = Angle
	 * Ls = Limit Switch
	 * Pot = Potentiometer
	 * En = Encoder
	 * Motor = Motor
	 * Servo = Servo
	 */
	
	
	//PWM pins
	public int drvsLMotorPin;
	public int drvsRMotorPin;
	public int clawMotorPin;
	public int iarmsLWhlMotorPin;
	public int iarmsLAngMotorPin;
	public int iarmsRAngMotorPin;
	public int iarmsRWhlMotorPin;
	public int coopLServoPin;
	public int coopRServoPin;
	public int tlftMotorPin;
	public int blftMotorPin;
	public int magMotorPin;

	
	//Analog pins
	public int clawPotPin;
	public int iarmsLPotPin;
	public int iarmsRPotPin;
	public int tlftPotPin;
	public int blftPotPin;
	public int magPotPin;
	
	//DIO pins
	public int ultraPin1;
	public int ultraPin2;
	public int tlftRInfraPin;
	public int drvsREnPin2;
	public int magLsPinDown;
	public int magLsPinUp;
	public int clawCloseLsPin;
	public int clawOpenLsPin;
	public int tlftInfraPin;
	public int clawSwitch;


	
	public PinConstants()	{
		
		//PWM pins
		drvsLMotorPin = 0;
		drvsRMotorPin = 1;
		clawMotorPin = 2;
		iarmsLAngMotorPin = 3;
		iarmsLWhlMotorPin = 4;
		iarmsRAngMotorPin = 5;
		iarmsRWhlMotorPin = 7;
		magMotorPin = 6;
		tlftMotorPin = 8;
		blftMotorPin = 9;
		
		
		
		//Analog pins
//		
		blftPotPin = 0;
		iarmsLPotPin = 1;
		iarmsRPotPin = 2;
		tlftPotPin = 3;
		clawPotPin = 5; 
//		magPotPin = 5; //removed
		
		//DIO pins
		ultraPin1 = 0;
		ultraPin2 = 1;
		tlftRInfraPin = 2;
		drvsREnPin2 = 3;
		magLsPinDown = 4;
		magLsPinUp = 5;
		clawCloseLsPin = 6; //close
		clawOpenLsPin = 7;
		clawSwitch = 8; //open
		tlftInfraPin = 9;

		
	}
	

}
