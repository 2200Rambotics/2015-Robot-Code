package org.usfirst.frc.team2200.robot;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveClass {
	Joystick driverStick;
	Joystick shooterStick;
	Encoder encRight;
	Encoder encLeft;
	PinConstants pins;
	public RobotDrive smiteDrive;
	BuiltInAccelerometer accel;
	int leftEncDesired;
	int rightEncDesired;
	double leftEncRatio;
	double rightEncRatio;
//	PIDController pidL;
//	PIDController pidR;
	Talon leftMotor;
	Talon rightMotor;
	
	
	
	public DriveClass(Joystick stickl, Joystick stickr, double pL, 
			double iL, double dL, double pR, double iR, double dR){

		this.driverStick = stickl;
		this.shooterStick = stickr;
		pins = new PinConstants();
		leftMotor = new Talon(pins.drvsLMotorPin);
		rightMotor = new Talon(pins.drvsRMotorPin);
		smiteDrive = new RobotDrive(leftMotor, rightMotor);
//		encRight = new Encoder(pins.ultraPin1, pins.ultraPin2);
//    	encLeft = new Encoder(pins.drvsREnPin1, pins.drvsREnPin2);
    	accel = new BuiltInAccelerometer();
//    	encLeft.setDistancePerPulse(leftEncRatio);
//    	encRight.setDistancePerPulse(rightEncRatio); //may not use this...
//    	pidL = new PIDController(pL, iL, dL, encLeft, leftMotor);
//    	pidR = new PIDController(pR, iR, dR, encRight, rightMotor);
	}
	//drive a distance
//	public void driveDistanc(double distanceMeters){
//		
////		pidL.setSetpoint(encLeft.get() + meters2Ticks(distanceMeters));
////		pidR.setSetpoint(encRight.get() + meters2Ticks(distanceMeters));
//		
//		
//	}
	public void driveTime(double speed, double time){
		smiteDrive.drive(speed, 0);
		Timer.delay(time);
		smiteDrive.drive(0, 0);
	}
	
	public void turnTime(double speed, double time){
		smiteDrive.tankDrive(speed, speed * -1);
		Timer.delay(time);
		smiteDrive.drive(0, 0);
	}
	
	public void driveTimeComplex(double speed, double curve, double time){
		smiteDrive.drive(speed, curve);
		Timer.delay(time);
		smiteDrive.drive(0, 0);
	}
	
//	public void turnAngle(double angle){
//		//wheel radius = 0.0635 meter
//		//radius of wheel to point of rotation 0.31115
//		//use radians for easier math
//		//angle x = (arc length)/(radius)
//		//arc length = (angle in Rads)*(radius)
//		//rads = (3.1415 / 180) * #degrees 
//		double rads = 0.017452778 * angle; //radians to turn
//		double radius = 0.31115; //const
//		double recArcLen = rads * radius; //arc length
//		long recTicks = meters2Ticks(recArcLen); //calculate ticks required for distance arclength
////		pidL.setSetpoint(encLeft.get() + (recTicks * -1));
////		pidR.setSetpoint(encRight.get() + (recTicks)); //I don't know which way this will turn
//	}

//	public void disablePID(){
////		pidL.disable();
////		pidR.disable();
//	}
//	public void enablePID(){
//		pidL.enable();
////		pidR.enable();
//	}
//	public void setPID(double pL, double iL, double dL, double pR, double iR, double dR){ //overwrites the P, I, D, values given in constructor to new values
//		pidL.setPID(pL, iL, dL);
//		pidL.disable();
//		pidL.enable(); //restart
////		pidR.setPID(pR, iR, dR);
////		pidR.disable();
////		pidR.enable(); //restart
//	}
	private long meters2Ticks(double distance){
		//wheel is 5 inch diameter, ratio between wheel gear and enc gear is 1:2.2
		//one wheel turn is 0.3989705 meters
		//5644 ticks per meter (apporx)
		long ticks = (long) (5644 * distance);
		return ticks;	
	}
	public boolean isPIDDone(){
		double leftCurrent = encRight.get();
		double rightCurrent = encRight.get();
//		double upperSetPointLeft = pidL.getSetpoint() * 1.05;
//		double lowerSetPointLeft = pidL.getSetpoint() * 0.95;
//		double upperSetPointRight = pidR.getSetpoint() * 1.05;
//		double lowerSetPointRight = pidR.getSetpoint() * 0.95;
		return true;
//		if (leftCurrent < upperSetPointLeft && leftCurrent > lowerSetPointLeft && rightCurrent < upperSetPointRight && rightCurrent > lowerSetPointRight){
//			return true;
//		} else {
//			return false;
//		}
		
	}
	public void run(){
		smiteDrive.tankDrive((driverStick.getThrottle() * -1),(driverStick.getY() * -1));
//		sortaDrive.arcadeDrive((driverStick.getY() * -1), driverStick.getX()); //TODO change
//		SmartDashboard.putNumber("drive left encoder val", encLeft.get());
//		SmartDashboard.putNumber("drive right encoder val", encRight.get());
	
	}
	


}