
package org.usfirst.frc.team2200.robot;






//import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import javax.swing.JComponent;

import edu.wpi.first.wpilibj.SampleRobot;
//import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.PowerDistributionPanel;


import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;



public class Robot extends SampleRobot {
	
	//objects created

//    RobotDrive myRobot;
    Joystick driverStick;
    Joystick shooterStick;

//    BuiltInAccelerometer accel;
//    Encoder enRight;
//    Encoder enLeft;
    
	//method objects created
    IntakeArms leftIntake;
    IntakeArms rightIntake;
    ToteLift toteLift;
    Claw claw;
//    CoopRamp coopRamp;
//    Magazine mag;
    MagazineLimitDotexeDotpyDotpng mag;
    BinLift binLift;
    PinConstants pins;
    DriveClass drive;
    SendableChooser sender;
    String autoChooser;
    PowerDistributionPanel pdp = new PowerDistributionPanel();
    CameraServer server;
	double multiX = 1.5;
	double multiY = 1.5;
	double leftMin = 1.0;
	double leftMax = 3.6;
	double rightMin = 2.2;
	double rightMax = 4.6;

    boolean isNew = false; //TODO change here

	double shooterJoyX;
	double shooterJoyY;
    
    //obj specific constants
    //claw
    double clawOpenPos = 0.5;
    double clawMiddlePos = 1.0;
    double clawClosedPos = 1.5;
    double clawP = 3;
    double clawI = 0;
    double clawD = 0;
    boolean clawWantOpen;
    boolean clawLimit;
    boolean killClaw = false;

    //coop
    double coopUpAngleL = 0;
    double coopUpAngleR = 0;
    double coopDownAngleL = 1;
    double coopDownAngleR = 1;
    double coopP = 0;
    double coopI = 0;
    double coopD = 0;
    
    //drive
    double drivePL = 0;
    double driveIL = 0;
    double driveDL = 0;
    double drivePR = 0;
    double driveIR = 0;
    double driveDR = 0;
    
    //tote lift
    double tlftExpGround = 3.502;
    double tlftExpTwo = 3.18;
    double tlftExpBelowMag = 2.1; //TODO get real value
    double tlftExpHardStop = tlftExpGround; //TODO fix
    double tlftExpTop = 1.6;
    double tlftP = 9.0;
    double tlftI = 0; 
    double tlftD = 0; 
    boolean tlftGoingToThree;
    boolean tlftAboveOne = false;
    boolean tlftGoingDown;
    int tlftPos;
    
    //bin Lift
    double blftExpPos1 = 4.456;
    double blftExpPos2 = 3.970;
    double blftExpPos3 = 3.345;
    double blftExpPos4 = 2.541;
    double blftExpPos5 = 2.159;
    double blftP = 3.0;
    double blftI = 0.0;
    double blftD = 0;
    boolean blftBelowThree = false;
    double blftStartTime;
    double blftTimeout = 3.5;
    boolean blftCheckTime = false;
    boolean blftTimerFipFop = false;
    double blftZLastPos = 0;
    int blftPoseidon;
    double blftZCurrentPos;
    double blftSetPoint;
    
    //intake
	short rvMultiWL = 1;
	short rvMultiWR = 1;
	double iarmsPL = 0.5;
	double iarmsIL = 0;
	double iarmsDL = 0;
	double iarmsPR = 0.5;
	double iarmsIR = 0;
	double iarmsDR = 0;
	double leftDefaultPosition = 2.5; //middle 
	double rightDefualtPosition = 3.6;
	double intakeStartime = 0;
	double intakeWheelTimeSpeed;

	//magazine
	double magP = 0;
	double magI = 0;
	double magD = 0;
	double magExpUp = 4.3;
	double magExpDown = 2.2;
	boolean magGoingDown;
	boolean magGoingUp;
	
	
	
	
    
    //vision
    int session;
    Image frame;
    
    


    public Robot() {
    	if(isNew)   {
    		
    		//claw
    	     clawOpenPos = 0;
    	     clawMiddlePos = 0;
    	     clawClosedPos = 0;
    	     clawP = 3;
    	     clawI = 0;
    	     clawD = 0;


    	    //coop
    	     coopUpAngleL = 0;
    	     coopUpAngleR = 0;
    	     coopDownAngleL = 1;
    	     coopDownAngleR = 1;
    	     coopP = 0;
    	     coopI = 0;
    	     coopD = 0;
    	    
    	    //drive
    	     drivePL = 0;
    	     driveIL = 0;
    	     driveDL = 0;
    	     drivePR = 0;
    	     driveIR = 0;
    	     driveDR = 0;
    	    
    	    //tote lift
//    	     tlftExpGround = 3.473;
    	     tlftExpGround = 3.253;
    	     tlftExpTwo = 2.861;
    	     tlftExpBelowMag = 2.151;
    	     tlftExpHardStop = 2.625;
    	     tlftExpTop = 1.370;
    	     tlftP = 3.0;
    	     tlftI = 0; 
    	     tlftD = 0; 

    	    
    	    //bin Lift
    	     blftExpPos1 = 2.910;
    	     blftExpPos2 = 3.202;
    	     blftExpPos3 = 2.556;
    	     blftExpPos4 = 1.796;
    	     blftExpPos5 = 0.999;
    	     blftP = 7.0;
    	     blftI = 0.0;
    	     blftD = 0;

    	    
    	    //intake
    		 rvMultiWL = 1;
    		 rvMultiWR = 1;
    		 iarmsPL = 1.0;
    		 iarmsIL = 0;
    		 iarmsDL = 0;
    		 iarmsPR = 1.0;
    		 iarmsIR = 0;
    		 iarmsDR = 0;
    		 
    		 multiX = 1.5;
    		 multiY = 1.5;
    		 leftMin = 1.625;
    		 leftMax = 3.710;
    		 rightMin = 0.911;
    		 rightMax = 2.969;
    		 leftDefaultPosition = 2.560; //middle 
    		 rightDefualtPosition = 1.949;

    		 //magazine
    		 magP = 0;
    		 magI = 0;
    		 magD = 0;
    		 magExpUp = 4.3;
    		 magExpDown = 2.2;
    		 
    		 


    		
    	}
    	//objects defined
        pins = new PinConstants();
		leftIntake = new IntakeArms(pins.iarmsLWhlMotorPin,
				pins.iarmsLAngMotorPin, pins.iarmsLPotPin, iarmsPL,
				iarmsIL, iarmsDL, rvMultiWL, false, true);
		rightIntake = new IntakeArms(pins.iarmsRWhlMotorPin,
				pins.iarmsRAngMotorPin, pins.iarmsRPotPin, iarmsPR,
				iarmsIR, iarmsDR, rvMultiWR, false, false);
	
		claw = new Claw(pins.clawMotorPin, pins.clawPotPin,pins.clawCloseLsPin, pins.clawOpenLsPin,pins.clawSwitch, clawOpenPos, clawClosedPos, clawMiddlePos, clawP, clawI, clawD);
//        coopRamp = new CoopRamp(pins.coopLServoPin, pins.coopRServoPin, coopUpAngleL,coopUpAngleR, coopDownAngleL, coopDownAngleR);
        toteLift = new ToteLift(pins.tlftMotorPin, pins.tlftPotPin, 
        		pins.tlftInfraPin, pins.tlftRInfraPin, tlftP, tlftI, 
        		tlftD, tlftExpGround, tlftExpTwo, tlftExpBelowMag, tlftExpTop, tlftExpHardStop);
//        mag = new Magazine(pins.magMotorPin, pins.magPotPin, magP, magI, magD, magExpUp, magExpDown);
        mag = new MagazineLimitDotexeDotpyDotpng(pins.magMotorPin, pins.magLsPinUp, pins.magLsPinDown);
        if (isNew){
        	binLift = new BinLift(pins.blftMotorPin, pins.blftPotPin, blftExpPos5, blftExpPos4, blftExpPos3, blftExpPos2, blftExpPos1, blftP, blftI, blftD,true);
        }
        else{
        	binLift = new BinLift(pins.blftMotorPin, pins.blftPotPin, blftExpPos5, blftExpPos4, blftExpPos3, blftExpPos2, blftExpPos1, blftP, blftI, blftD,false); 	
        }
        driverStick = new Joystick(0);
        shooterStick = new Joystick(1);
        drive = new DriveClass(driverStick, shooterStick, drivePL, driveIL, driveDL, drivePR, driveIR, driveDR);
        
        //autonomous radio button choser
        sender = new SendableChooser();
        sender.addDefault("drive straight forwards overhump", "drive straight forwards overhump");
        sender.addObject("drive straight forwards", "drive straight forwards");
        sender.addObject("drive straight backwards overhump", "drive straight backwards overhump");
        sender.addObject("drive straight backwards", "drive straight backwards");
        sender.addObject("Tote 3 stack and drop", "Tote 3 stack and drop");
        sender.addObject("Tote push one", "Tote push one");
        sender.addObject("Bin Push one", "Bin Push one");
        sender.addObject("Follow Hammel", "Follow Hammel");
        SmartDashboard.putData("NEWNEWNEWNEWNEW autochooser", sender);
//TODO if camera connected       
//        frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
//        session = NIVision.IMAQdxOpenCamera("cam2", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
//		NIVision.IMAQdxConfigureGrab(session);
//		ultra = new Ultrasonic(pins.ultraPin1,pins.ultraPin2);
//		server = CameraServer.getInstance();
//		server.setQuality(25);
		
	
        
    }

    public void autonomous() {
    	drive.smiteDrive.setSafetyEnabled(false);
    	//get the selected auto mode
    	autoChooser = (String) sender.getSelected();
    	
    	if (autoChooser.equals("drive straight forwards overhump")){
    		DriveForwardAutoDotpy basicAuto = new DriveForwardAutoDotpy(drive, 
        			claw, binLift, toteLift, mag, leftIntake, rightIntake, leftDefaultPosition,
        			rightDefualtPosition,leftMin,rightMax);
    		basicAuto.driveForwardOverhump();
    	} else if (autoChooser.equals("drive straight forwards")){
    		DriveForwardAutoDotpy basicAuto = new DriveForwardAutoDotpy(drive, 
        			claw, binLift, toteLift, mag, leftIntake, rightIntake, leftDefaultPosition,
        			rightDefualtPosition,leftMin,rightMax);
    		basicAuto.driveForward();
    	} else if (autoChooser.equals("drive straight backwards overhump")){
    		DriveForwardAutoDotpy basicAuto = new DriveForwardAutoDotpy(drive, 
        			claw, binLift, toteLift, mag, leftIntake, rightIntake, leftDefaultPosition,
        			rightDefualtPosition,leftMin,rightMax);
    		basicAuto.driveBackwardOverhump();
    	} else if (autoChooser.equals("drive straight backwards")){
    		DriveForwardAutoDotpy basicAuto = new DriveForwardAutoDotpy(drive, 
        			claw, binLift, toteLift, mag, leftIntake, rightIntake, leftDefaultPosition,
        			rightDefualtPosition,leftMin,rightMax);
    		basicAuto.driveBackward();
    	} else if (autoChooser.equals("Follow Hammel")){
    		VisionAuto visionAuto = new VisionAuto();
    		VisionCode camObj = visionAuto.getcamObj();
    		
    		SmartDashboard.putString("status: ", "calling visionAuto.run()");
    		visionAuto.hammel();
    		
            NIVision.IMAQdxStopAcquisition(camObj.session);
    		SmartDashboard.putString("status: ", "done");
    		
    	} else if (autoChooser.equals("Tote 3 stack and drop")){
    		VisionAuto visionAuto = new VisionAuto();
    		VisionCode camObj = visionAuto.getcamObj();
    		
    		SmartDashboard.putString("status: ", "calling visionAuto.run()");
    		visionAuto.hammel();
    		
            NIVision.IMAQdxStopAcquisition(camObj.session);
    		SmartDashboard.putString("status: ", "done");
    		
    	} else if (autoChooser.equals("Tote push one")){
    		DriveForwardAutoDotpy basicAuto = new DriveForwardAutoDotpy(drive, 
        			claw, binLift, toteLift, mag, leftIntake, rightIntake, leftDefaultPosition,
        			rightDefualtPosition,leftMin,rightMax);
    		basicAuto.drivePickupTote();
    	} else if (autoChooser.equals("Bin Push one")){
    		DriveForwardAutoDotpy basicAuto = new DriveForwardAutoDotpy(drive, 
        			claw, binLift, toteLift, mag, leftIntake, rightIntake, leftDefaultPosition,
        			rightDefualtPosition,leftMin,rightMax);
    		basicAuto.drivePickupBin();
    	}
    	
    	
		
		
    }


    //@SuppressWarnings("unused")
	public void operatorControl() {

    	boolean isManlyMode = false;
    	if (isManlyMode) { //initial	 state 
			binLift.disablePID();
			leftIntake.disablePID();
			rightIntake.disablePID();
//			mag.disablePID();
			toteLift.disablePID();
		} else {
			binLift.enablePID();
			leftIntake.enablePID();
			rightIntake.enablePID();
//			mag.enablePID();
			toteLift.enablePID();
		}
    	claw.open();
    	
    	
    	boolean isManlyModeIsMuhTriggersFipFop = false;

		@SuppressWarnings("unused")
    	boolean clawIsMuhTriggersFipFop = false;
    
		boolean tlftIsMuhTriggersFipFopTop = false;
 
		boolean tlftIsMuhTriggersFipFopBot = false;
		
    	@SuppressWarnings("unused")
		boolean blftIsMuhTriggersFipFopTop = false;
    	
    	@SuppressWarnings("unused")
		boolean blftIsMuhTriggersFipFopBot = false; //eclipse says these are not used dafuq they are just click on them
		
		
//    	drive.disablePID();
//        myRobot.setSafetyEnabled(true);
//    	NIVision.Rect rect = new NIVision.Rect(10, 10, 300, 500);
    	int start1x = 130;
    	int start1y = 0;
    	int end1x = 130;
    	int end1y = 400;
    	int start2x = 500;
    	int start2y = 0;
    	int end2x = 500;
    	int end2y = 400;
    	float lineColourBGR = 0xFFFF00;
    	
    	
    	
//TODO if camera     	
//    	NIVision.Point line11Pstart = new NIVision.Point(start1x, start1y);
//    	NIVision.Point line11Pend = new NIVision.Point(end1x, end1y);
//    	NIVision.Point line12Pstart = new NIVision.Point(start1x + 1, start1y);
//    	NIVision.Point line12Pend = new NIVision.Point(end1x + 1, end1y);
//    	NIVision.Point line13Pstart = new NIVision.Point(start1x + 2, start1y);
//    	NIVision.Point line13Pend = new NIVision.Point(end1x + 2, end1y);
//    	NIVision.Point line14Pstart = new NIVision.Point(start1x + 3, start1y);
//    	NIVision.Point line14Pend = new NIVision.Point(end1x + 3, end1y);
//    	NIVision.Point line15Pstart = new NIVision.Point(start1x + 4, start1y);
//    	NIVision.Point line15Pend = new NIVision.Point(end1x + 4, end1y);
//    	NIVision.Point line16Pstart = new NIVision.Point(start1x + 5, start1y);
//    	NIVision.Point line16Pend = new NIVision.Point(end1x + 5, end1y);
//    	
//    	NIVision.Point line21Pstart = new NIVision.Point(start2x, start2y);
//    	NIVision.Point line21Pend = new NIVision.Point(end2x, end2y);
//    	NIVision.Point line22Pstart = new NIVision.Point(start2x + 1, start2y);
//    	NIVision.Point line22Pend = new NIVision.Point(end2x + 1, end2y);
//    	NIVision.Point line23Pstart = new NIVision.Point(start2x + 2, start2y);
//    	NIVision.Point line23Pend = new NIVision.Point(end2x + 2, end2y);
//    	NIVision.Point line24Pstart = new NIVision.Point(start2x + 3, start2y);
//    	NIVision.Point line24Pend = new NIVision.Point(end2x + 3, end2y);
//    	NIVision.Point line25Pstart = new NIVision.Point(start2x + 4, start2y);
//    	NIVision.Point line25Pend = new NIVision.Point(end2x + 4, end2y);
//    	NIVision.Point line26Pstart = new NIVision.Point(start2x + 5, start2y);
//    	NIVision.Point line26Pend = new NIVision.Point(end2x + 5, end2y);
//
//    	NIVision.IMAQdxStartAcquisition(session);
    	drive.smiteDrive.setSafetyEnabled(true);

        while (isOperatorControl() && isEnabled()) {

    		
//    		NIVision.imaqDrawShapeOnImage(frame, frame, rect, DrawMode.DRAW_VALUE, ShapeMode.SHAPE_RECT, 0x000000);
    		
        	
        	//PDB current display
            SmartDashboard.putNumber("Drive Left Current: ", pdp.getCurrent(3)+pdp.getCurrent(0));
            SmartDashboard.putNumber("Drive Right Current: ", pdp.getCurrent(14)+pdp.getCurrent(15));
            SmartDashboard.putNumber("Magazine Current: ", pdp.getCurrent(9));
            SmartDashboard.putNumber("Intake Wheel Right Current: ", pdp.getCurrent(1));
            SmartDashboard.putNumber("Intake Wheel Left Current: ", pdp.getCurrent(6));
            SmartDashboard.putNumber("Intake Angle Right Current: ", pdp.getCurrent(7));
            SmartDashboard.putNumber("Intake Angle Left Current: ", pdp.getCurrent(11));
            SmartDashboard.putNumber("Claw Current: ", pdp.getCurrent(8));
            SmartDashboard.putNumber("Tote Lift Current: ", pdp.getCurrent(5));
            SmartDashboard.putNumber("Bin Lift Current: ", pdp.getCurrent(4));
            



            
        	mag.run();
        	binLift.checkPosition();
        	leftIntake.getCurrentVolt();
        	rightIntake.getCurrentVolt();
        	toteLift.checkSensors();
        	if(toteLift.getPosition() == 1){
            	SmartDashboard.putString("tlft Pos: ","Ground");
        	}else if(toteLift.getPosition() == 2){
            	SmartDashboard.putString("tlft Pos: ","Off Ground");
        	}else if(toteLift.getPosition() == 3){
            	SmartDashboard.putString("tlft Pos: ","Loading Tote");        		
        	}
//        	SmartDashboard.putNumber("tlft Pos: ",toteLift.getPosition());
        	int tlftPos = toteLift.getPosition();
        	@SuppressWarnings("unused")
			int blftPoseidon = binLift.getDesiredPosition();
        	double shooterJoyX = shooterStick.getX();
        	double shooterJoyY = shooterStick.getY();
//        	drive.disablePID(); //used to ensure that the autonomous PID stuff in drive is not used again        	
        	drive.run();
        	clawLimit = claw.checkLimitClose();
        	
//        	if (driverStick.getRawButton(7)){
//        		
//        		if (!isManlyModeIsMuhTriggersFipFop) {
//					if (!isManlyMode) {
//						binLift.disablePID();
//						leftIntake.disablePID();
//						rightIntake.disablePID();
//						//					mag.disablePID();
//						toteLift.disablePID();
//						isManlyMode = true;
//					} else {
//						binLift.enablePID();
////						claw.enablePID();
//						leftIntake.enablePID();
//						rightIntake.enablePID();
//						//					mag.enablePID();
//						toteLift.enablePID();
//						isManlyMode = false;
//					}
//					binLift.basicMotorSet(0);
//					claw.basicMotorSet(0);
//					leftIntake.basicAngleMotorSet(0);
//					leftIntake.basicWheelMotorSet(0);
//					rightIntake.basicAngleMotorSet(0);
//					rightIntake.basicWheelMotorSet(0);
//					mag.basicMotorSet(0);
//					toteLift.basicMotorSet(0);
//				}
//				isManlyModeIsMuhTriggersFipFop = true;
//        	} else {
//        		isManlyModeIsMuhTriggersFipFop = false;
//        	}
        	SmartDashboard.putBoolean("isManlyMode: ", isManlyMode);
        	
        	if (!isManlyMode){
        		
        		
        		
        		
					//do iarm angle calculations:
					leftIntake.setSetpoint(calculateIarmAngle(shooterJoyX,
							shooterJoyY, true, false));
					rightIntake.setSetpoint(calculateIarmAngle(shooterJoyX,
							shooterJoyY, false, false));
					SmartDashboard.putNumber("left calc arngle func: ", calculateIarmAngle(shooterJoyX,
							shooterJoyY, true, false));
					SmartDashboard.putNumber("right calc arngle func: ", calculateIarmAngle(shooterJoyX,
							shooterJoyY, false, false));
				//	        	if(shooterStick.getRawButton(10)){
//	        		claw.Close();
//	        	}
//	        	else if(shooterStick.getRawButton(11)){
//	        		claw.Open();
//	        	}
//
	        	claw.run();
//	        	if (!shooterStick.getRawButton(9)) {
//					if (clawLimit) {
//						claw.clawMotor.set(0);
//						killClaw = true;
//					}
//				}
//				if (killClaw){
//	        		claw.clawMotor.set(0);
//	        	} else {
//	        		if (shooterStick.getRawButton(10)) { //claw in
//						claw.clawMotor.set(1);
//					} else if (shooterStick.getRawButton(11)){//claw out
//						claw.clawMotor.set(-1);
//					} else {
//						claw.clawMotor.set(0);
//					}
//	        	}
//	        	if (shooterStick.getRawButton(9)){
//	        		killClaw = false;
//	        	}
//	        	
	            if (shooterStick.getRawButton(1)){ //drop
	            	mag.Down();
	            	toteLift.gotoExpGround();
	            } else {
	            	
	            }

				if (driverStick.getRawButton(2)){ //bottom butt on joy handle
	            	mag.Down();
	            } else {
	            	
	            }
	            if (driverStick.getRawButton(4)){ //top butt on joy handle
	            	mag.Up();
	            } else {
	            	
	            }
	            
	            //TODO fix
	            
	            if (shooterStick.getRawButton(3)){ //left butt on joy handle , both oout
	            	intakeWheelTimeSpeed = (Timer.getFPGATimestamp() - intakeStartime) * 2;
	            	if (intakeWheelTimeSpeed < 1) {
						leftIntake.setSpeed(intakeWheelTimeSpeed);
						rightIntake.setSpeed(intakeWheelTimeSpeed);
					} else {
						leftIntake.setSpeed(1);
						rightIntake.setSpeed(1);
					}
	            } else if (shooterStick.getRawButton(2)){ //right butt on joy handle, both inn
	            	intakeWheelTimeSpeed = (intakeStartime - Timer.getFPGATimestamp()) * 2;
	            	if (intakeWheelTimeSpeed > -1) {
						leftIntake.setSpeed(intakeWheelTimeSpeed);
						rightIntake.setSpeed(intakeWheelTimeSpeed);
					} else {
						leftIntake.setSpeed(-1);
						rightIntake.setSpeed(-1);
					} 
	            	
	            } else if (shooterStick.getRawButton(4)){ //right butt on joy handle, both left
	            	leftIntake.setSpeed(0.5);
	            	rightIntake.setSpeed(-1);
				} else if (shooterStick.getRawButton(5)){ //right butt on joy handle, both rightt
					leftIntake.setSpeed(-1);
					rightIntake.setSpeed(0.5);
				} else {
	            	leftIntake.setSpeed(0);
	            	rightIntake.setSpeed(0);
	            	intakeStartime = Timer.getFPGATimestamp();
	            	
	            }
	            
	            
	            
	            
	            if (shooterStick.getRawButton(6)){ //forward left butt joy base
	            	
	            	if (!tlftIsMuhTriggersFipFopTop) {
						if (!blftBelowThree) {
//							if (toteLift.infra.get() || driverStick.getRawButton(1)) {
								if (tlftPos == 1) {
									toteLift.gotoExpMiddle();
									tlftGoingToThree = false;
									tlftAboveOne = true;
								}
//							}
							if (tlftPos == 2) {
								toteLift.gotoExpTop();
								tlftGoingToThree = true;
								tlftAboveOne = true;

							} else if (tlftPos == 3) {
								toteLift.gotoExpTop(); //can't go any further top kek
								tlftAboveOne = true;

							}
						}
					}
	            	tlftIsMuhTriggersFipFopTop = true;
	            } else {
	            	tlftIsMuhTriggersFipFopTop = false;
	            }
	            

	            if (shooterStick.getRawButton(7)){ //backward left butt joy base
	            	
	            	if (!tlftIsMuhTriggersFipFopBot) {
//						if (!toteLift.infra.get() || driverStick.getRawButton(1)) {
							if (tlftPos == 1) { //1 is ground
								toteLift.gotoExpGround();
								tlftGoingToThree = false;
								tlftAboveOne = false;
							} else if (tlftPos == 2) { //2 is FIrst
								toteLift.gotoExpGround();
								tlftGoingToThree = false;
								tlftAboveOne = false;
							} else if (tlftPos == 3) { //3 is second
								toteLift.gotoExpMiddle();
								tlftGoingToThree = false;
								tlftAboveOne = true;
							}
//						}
					}
	            	tlftIsMuhTriggersFipFopBot = true;
	            } else {
	            	tlftIsMuhTriggersFipFopBot = false;
	            }
//	            if (shooterStick.getRawButton(8)){ //dongers
//	            	updatePID();
//	            } else {
//	            	
//	            }

	            
	            if(shooterStick.getRawButton(8)){
	            	blftStartTime = Timer.getFPGATimestamp();
	            }
//TODO if camera connected	            
//	            if(shooterStick.getRawButton(9)){
//	            	
//	        		
//	        		NIVision.IMAQdxGrab(session, frame, 1);
//	        		NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, line11Pstart, line11Pend, lineColourBGR);
//	        		NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, line12Pstart, line12Pend, lineColourBGR);
//	        		NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, line13Pstart, line13Pend, lineColourBGR);
//	        		NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, line14Pstart, line14Pend, lineColourBGR);
//	        		NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, line15Pstart, line15Pend, lineColourBGR);
//	        		NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, line16Pstart, line16Pend, lineColourBGR);
//	        		
//	        		NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, line21Pstart, line21Pend, lineColourBGR);
//	        		NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, line22Pstart, line22Pend, lineColourBGR);
//	        		NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, line23Pstart, line23Pend, lineColourBGR);
//	        		NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, line24Pstart, line24Pend, lineColourBGR);
//	        		NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, line25Pstart, line25Pend, lineColourBGR);
//	        		NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, line26Pstart, line26Pend, lineColourBGR);
//	        		server.setImage(frame);
//	            } else {
//	            	
//	            }
	            
	            blftZCurrentPos = shooterStick.getZ();
//            	if ((blftZLastPos > (blftZCurrentPos * 1.05)) && (blftZLastPos < (blftZCurrentPos * 0.95))) {
	            blftSetPoint = binLift.calculateBinSetpoint(blftZCurrentPos, false);
	            binLift.setSetPoint(blftSetPoint);
	            SmartDashboard.putNumber("what were setting the blift to: ", blftSetPoint);
	            SmartDashboard.putBoolean("binLift.isWithinPercOfPos(0.10, 1)", binLift.isWithinPercOfPos(0.10, 1));
	            SmartDashboard.putBoolean("claw.isClawClose", claw.isClawClose);
//				if (blftSetPoint > blftExpPos3){ //if we're going lower than pos 3 and not in pos 1
//					if(!binLift.isWithinPercOfPos(0.10, 1)){
//						if (!claw.isClawClose) {
//							claw.close();
//						} else {
//							binLift.setSetPoint(blftSetPoint);
//						}
//					}
////					toteLift.gotoExpHardHardHardHardHardStop();
//				} else {
//					binLift.setSetPoint(blftSetPoint);
//				}
//				
//				if (blftSetPoint < blftExpPos1){ //if we want to go above pos 1, 
//					if (binLift.isBelowPos(3)){
//						if (!claw.isClawClose){
//							claw.close();
//						} else {
//							binLift.setSetPoint(blftSetPoint);
//						}
//					}
//				}
	            if(blftZLastPos != blftZCurrentPos){
            		blftStartTime = Timer.getFPGATimestamp();
            		
					blftZLastPos = blftZCurrentPos;
				}
				

            	if ((Timer.getFPGATimestamp() - blftStartTime) > blftTimeout)	{
            		binLift.setPID(0, 0, 0);
            		blftTimerFipFop = true;
            	} else {
            		if (blftTimerFipFop) {
            			binLift.setPID(blftP, blftI, blftD);
            			blftTimerFipFop = false;
            		}
            	}
	        	if(shooterStick.getRawButton(11)){
	        		claw.open();
	        		claw.blahBlahBlah = false;
	        	}else if(shooterStick.getRawButton(10)){
	        		claw.close();
	        		claw.blahBlahBlah = false;
	        	} else {
	        		claw.blahBlahBlah = true;
	        	}
	            
	            
//	            if (driverStick.getRawButton(2)){ //backward right butt joy base //go down
//	            	
//	            	if (!blftIsMuhTriggersFipFopBot) {
//	            		blftStartTime = Timer.getFPGATimestamp();
//						if (!tlftAboveOne) {
//							if (mag.isMagDown) {
//								if (blftPoseidon == 1) {
//									binLift.setPosition(1);
//									blftBelowThree = true;
//									blftCheckTime = false;
//								} else if (blftPoseidon == 2) {
//									binLift.setPosition(1);
//									blftBelowThree = true;
//									blftCheckTime = false;
//								}
//
//							}
//							if (blftPoseidon == 3) {
//								binLift.setPosition(2);
//								blftBelowThree = true;
//								blftCheckTime = true;
//							}
//						}
//						if (blftPoseidon == 4) {
//							binLift.setPosition(3);
//							blftBelowThree = false;
//							blftCheckTime = true;
//						} else if (blftPoseidon == 5) {
//							binLift.setPosition(4);
//							blftBelowThree = false;
//							blftCheckTime = true;
//						}
//					}
//	            	blftIsMuhTriggersFipFopBot = true;
//	            } else {
//	            	blftIsMuhTriggersFipFopBot = false;
//	            }
//	            if (driverStick.getRawButton(4)){ //forward right butt joy base //go up
//	            	if (!blftIsMuhTriggersFipFopTop) {
//	            		blftStartTime = Timer.getFPGATimestamp();
//						if (mag.isMagDown) {
//							if (blftPoseidon == 1) {
//								binLift.setPosition(2); //needs to be down for clearence
//								blftBelowThree = true;
//								blftCheckTime = true;
//							} else if (blftPoseidon == 2) {
//								binLift.setPosition(3);
//								blftCheckTime = true;
//								blftBelowThree = false;
//							} 
//						}
//						if (blftPoseidon == 3) { //does not care if mag is down
//							binLift.setPosition(4);
//							blftCheckTime = true;
//							blftBelowThree = false;
//						} else if (blftPoseidon == 4) {
//							blftCheckTime = true;
//							binLift.setPosition(5);
//							blftBelowThree = false;
//						} else if (blftPoseidon == 5) {
//							blftCheckTime = true;
//							binLift.setPosition(5);
//							blftBelowThree = false;
//						}
//					}
//					blftIsMuhTriggersFipFopTop = true;
//	            } else {
//	            	blftIsMuhTriggersFipFopTop = false;
//	            }
//	            
//	            if (blftCheckTime){
//	            	if ((Timer.getFPGATimestamp() - blftStartTime) > blftTimeout)	{
//	            		binLift.setPID(0, 0, 0);
//	            		blftTimerFipFop = true;
//	            	} else {
//	            		if (blftTimerFipFop) {
//							binLift.setPID(blftP, blftI, blftD);
//							blftTimerFipFop = false;
//						}
//	            	}
//	            	
//	            }
//	            
//	            if (tlftGoingToThree){
//            	if (toteLift.isPIDDone()){
//            		tlftGoingToThree = false;
//          		    toteLift.gotoExpGround();
//
//            	}	            
//            }
            	
            	
            	
            	
            	
	            if (tlftGoingToThree){
	            	if (toteLift.isPIDDone()){
//	            		mag.Down();
	            		magGoingDown = true;
	            		tlftGoingToThree = false;
	            	}	            
	            }
	            
	            if (magGoingDown){
//	            	if (mag.isMagDown){
	            		magGoingDown = false;
	            		magGoingUp = true;
	            		mag.Up();
//	            	}
	            }
	            if (magGoingUp){
	            	if (mag.isMagUp){
	            		magGoingUp = false;
//        				toteLift.gotoExpGround();
	            		toteLift.gotoExpBelowMag();
        				tlftGoingDown = true;
	            	}
	            }
//	            
	            if ((toteLift.getPotPos() > tlftExpTop) && (toteLift.getPotPos() < tlftExpBelowMag)){
	            	if(tlftGoingDown){
	            		toteLift.setPID((tlftP * .5), tlftI, tlftD);
	            		tlftGoingDown = false;
	            	}
	            } else {
	            	toteLift.setPID(tlftP, tlftI, tlftD);
	            }
	            
	            
	            Timer.delay(0.005);		
	            
        	} else {
        		manlyMode();
        	}
        	Timer.delay(0.005);
        }
        //TODO camera is connected
//        NIVision.IMAQdxStopAcquisition(session);
    }


    
    
    public double calculateIarmAngle(double joyX, double joyY, boolean isLeftSide, boolean isManly){ //xaxis should swing arms left and right, 
    	if (!isManly) {
			//y axis should bring arms closer/farther apart

			double output;
			if (isLeftSide) {
				output = leftDefaultPosition + (joyX * multiX)
						+ (joyY * multiY);
				if (output > leftMax) { //smart sanity check
					return leftMax;
				} else if (output < leftMin) {
					return leftMin;
				} else {
					return output;
				}
			} else {
				output = rightDefualtPosition + (joyX * multiX)
						- (joyY * multiY); //mixer
				if (output > rightMax) { //sanity check
					return rightMax;
				} else if (output < rightMin) {
					return rightMin;
				} else {
					return output;
				}
			}
		} else {
			//y axis should bring arms closer/farther apart
			double leftDefaultPosition = 0; //middle 
			double rightDefualtPosition = 0;
			double output;
			if (isLeftSide) {
				output = leftDefaultPosition + (joyX * multiX)
						+ (joyY * multiY);
				if (output > leftMax) { //smart sanity check
					return leftMax;
				} else if (output < leftMin) {
					return leftMin;
				} else {
					return output;
				}
			} else {
				output = rightDefualtPosition + (joyX * multiX)
						- (joyY * multiY); //mixer
				if (output > rightMax) { //sanity check
					return rightMax;
				} else if (output < rightMin) {
					return rightMin;
				} else {
					return output;
				}
			}
		}
    	
    }

    public void manlyMode(){

    	/*
		buttons are bundled together in groups of two, for in and out
    	Bundles:
    		shooterJoy
    		1 & 8 -- claw
    		shooterjoystick xY -- iarms angle
    		2 & 3 -- mag
    		4 & 5 -- iarm wheel
    		6 & 7 -- tlift
    		10 & 11 -- blift
    		
    		driverJoy
    		1 & 2 -- 
    		3 & 4
    		7 -- manly mode hold
    		6 & 8 --
    		10 -- claw override
    	*/
    	//shooterstick
    	if (!driverStick.getRawButton(10)) {
			if (clawLimit) {
				claw.clawMotor.set(0);
				killClaw = true;
			}
		}
		if (killClaw){
    		claw.clawMotor.set(0);
    	} else {
    		if (shooterStick.getRawButton(1)) { //trigger
				claw.clawMotor.set(1);
			} else if (shooterStick.getRawButton(8)){
				claw.clawMotor.set(-1);
			} else {
				claw.clawMotor.set(0);
			}
    	}
    	if (driverStick.getRawButton(10)){
    		killClaw = false;
    	}
    	
    	
    	double shooterJoyX = shooterStick.getX();
    	double shooterJoyY = shooterStick.getY();
    	leftIntake.basicAngleMotorSet(calculateIarmAngle(shooterJoyX, shooterJoyY, true, true));
    	rightIntake.basicAngleMotorSet(calculateIarmAngle(shooterJoyX, shooterJoyY, false, true));
    	
    	if (shooterStick.getRawButton(2)){ //bottom butt on joy handle
        	mag.Down();
        } else if (shooterStick.getRawButton(3)){ //top butt on joy handle
        	mag.Up();
        } else {
        	@SuppressWarnings("unused")
			String dongers = "raised";
        }
        
        if (shooterStick.getRawButton(4)){ //bottom butt on joy handle
        	leftIntake.basicWheelMotorSet(-1);
        	rightIntake.basicWheelMotorSet(-1);
        } else if (shooterStick.getRawButton(5)){ //top butt on joy handle
        	leftIntake.basicWheelMotorSet(1);
        	rightIntake.basicWheelMotorSet(1);
        } else {
        	leftIntake.basicWheelMotorSet(0);
        	rightIntake.basicWheelMotorSet(0);
        }

        if (shooterStick.getRawButton(6)){ //bottom butt on joy handle
        	toteLift.basicMotorSet(-1);
        } else if (shooterStick.getRawButton(7)){ //top butt on joy handle
        	toteLift.basicMotorSet(1);
        } else {
        	toteLift.basicMotorSet(0);
        }

        if (shooterStick.getRawButton(11)){ //bottom butt on joy handle
        	binLift.basicMotorSet(-1);
        } else if (shooterStick.getRawButton(10)){ //top butt on joy handle
        	binLift.basicMotorSet(1);
        } else {
        	binLift.basicMotorSet(0);
        }
    }

    public void test() {
//    	drive.driveDistance(0.1);
    }
}
/*
 * Dear Reader, you have reached the end of this code. I hope you have benefited from the foolish syntax and naming of 
 * objects/variables. I would have wished you luck reading this but, it's already been over 500 lines of code so I am sure 
 * you know. This is one of the hardest games, and most difficult robot. Next Year will be better . . . we hope! For now 
 * I bid you farewell. GG / ff at 20
 */
