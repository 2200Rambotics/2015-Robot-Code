package org.usfirst.frc.team2200.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Magazine {
	/* 
	*Magazine-drop:
	*one motor & 1 potentiometer
	*/
	
	private Talon magMotor;
	private AnalogInput pot;
	private PIDController pid;
	private double expUp;
	private double expDown;
	public boolean magPos;

	
	public Magazine(int pinTalon, int pinAnalog, double p, 
			double i, double d, double expUp, double expDown) {

		
		magMotor = new Talon(pinTalon);
		pot = new AnalogInput(pinAnalog);
		pid = new PIDController(p, i, d, pot, magMotor);
		this.expUp = expUp; 
		this.expDown = expDown; 

	}
	
	public void setPID(double p, double i, double d){ 
		
		pid.setPID(p, i, d);
		pid.disable();
		pid.enable(); 
	}
	
	public void disablePID(){
		
		pid.disable();
	}
	
	public void enablePID() {
		
		pid.enable();
	}
	
	//brings magazine up
	public void Up() {
		
		pid.setSetpoint(expUp);
//		magMotor.set(pid.get());
		SmartDashboard.putString("Magazine Position: ", "Up");
		
	}
	
	public void basicMotorSet(double motorVal){
		magMotor.set(motorVal);
		SmartDashboard.putNumber("mag motor val: ", motorVal);
	}
	
	// brings magazine down
	public void Down() {
		
		pid.setSetpoint(expDown);
//		magMotor.set(pid.get());
		SmartDashboard.putString("Magazine Position: ", "Down");
		
	}
	
}