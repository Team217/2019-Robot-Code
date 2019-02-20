/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;

import org.team217.ctre.WPI_TalonSRX;
import org.team217.pid.PID;
import org.team217.rev.CANSparkMax;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class LiftingMechanism extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

	CANSparkMax leftArm1 = RobotMap.leftArm;
	CANSparkMax rightArm1 = RobotMap.rightArm;
	WPI_TalonSRX leftElevator1 = RobotMap.leftElevator;
	WPI_TalonSRX rightElevator1 = RobotMap.rightElevator;
	WPI_TalonSRX wrist1 = RobotMap.wrist;

	AnalogGyro intakeGyro1 = RobotMap.intakeGyro;
//	DigitalInput elevatorBottomLimit1 = RobotMap.elevatorBottomLimit;
//	DigitalInput elevatorTopLimit1 = RobotMap.elevatorTopLimit;
	DigitalInput wristBackLimit1 = RobotMap.wristBackLimit;
	DigitalInput wristFrontLimit1 = RobotMap.wristFrontLimit;
//	DigitalInput armFrontLimit1 = RobotMap.armFrontLimit;
//	DigitalInput armBackLimit1 = RobotMap.armBackLimit;

	PID elevatorPID1 = RobotMap.elevatorPID;
	PID elevatorMaintainPID1 = RobotMap.elevatorMaintainPID;

	double elevatorMult = 1;
	double armMult = 1;
	double wristMult =.35;
	
	public boolean intakePID_OnTarget = false;
	public boolean elevatorPID_OnTarget = false;  

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
	// setDefaultCommand(new MySpecialCommand());
	leftElevator1.follow(rightElevator1);
  }

	/**
	 * Checks if the elevator's limit switches are hit and sets the elevator speed accordingly.
	 * 
	 * @param speed
	 *           The speed value currently being sent to the elevator motors
	 */
/*	
	public void elevatorLimitCheck(double speed) {
		if (!elevatorBottomLimit1.get() && speed >= 0.0) { //get some limit switches
			elevatorSpeed = 0;
			rightElevator1.resetEncoder();
			leftElevator1.resetEncoder();
			lastEncoder = 0;
		}
		else if(!elevatorTopLimit1.get() && speed <= 0.0) { // get a limit switch
			elevatorSpeed = -0.085;
		}
	}
*/
  public void elevator(double speed) {
		//elevatorMaintainPID1.setPID(0.0001417, 0.00000117, 0); //not even close to the right PID here
/*		
		if (rightElevator1.getPosition() <= -34000 && speed <= 0) { //encoder values are wrong, check the logic
			elevatorMult = .35;
		}
		else if(rightElevator1.getPosition() >= -5000 && speed >= 0.0) { //this one too
			elevatorMult = .25;
		}
		else {
			elevatorMult = 1.0;
		}
*/		

		if (!RobotMap.elevatorBottomLimit.get()) {
			rightElevator1.resetEncoder();
			if (speed > 0) {
				speed = 0;
			}
		}

		if ((!RobotMap.elevatorTopLimit.get() || rightElevator1.getEncoder() >= 16180) && speed < 0) {
			speed = 0;
		}
		elevatorMult = 1;
//		elevatorLimitCheck(speed);
		rightElevator1.set(speed * elevatorMult);
		//leftElevator1.set((speed * elevatorMult));
	}
	
	public double armLimitCheck(double speed){
		if (rightArm1.getPosition() >= 0 && speed >= 0.0) { //get some limit switches, make sure logic is right
			speed = 0;
			leftArm1.resetEncoder();
			rightArm1.resetEncoder();
		}
		else if(rightArm1.getPosition() <= -207 && speed <= 0.0) { // get a limit switch //Practice -188 Comp -207
			speed = 0;
		}
		return speed;
	}


  public void arm(double speed){
/*
		if(rightArm1.getPosition() <= && speed <= 0){ 	//get encoder value, check logic (slow going to front extreme)
			armMult = .35;
		}	
		else if(rightArm1.getPosition() >= && speed >= 0){ 	//same as above. (slow top when front goes to back)
			armMult = .5;
		}
		else if(rightArm1.getPosition() <= && speed <= 0){ //same as above. (slow top when back goes to front)
			armMult = .5;
		}
		else if(rightArm1.getPosition() >= && speed >= 0){ 	//same as above. (slow going to back extreme)
			armMult = .35;
		}
		else {
			armMult = 1;
		}

		if (speed != 0) {
			lastEncoder = rightArm1.getPosition(); //wrong encoder perhaps?
			armSpeed = speed;
		}

		else if(speed == 0 && rightArm1.getPosition() == ) { //value should be right before the middle, coming from the front (use potentiometer?)
			armSpeed = -0; //find right value, might need to use PID this time
			armMult = 1.0;
		}
		else if(speed == 0 && rightArm1.getPosition() == ) { //value should be right before the middle, coming from the back (use potentiometer?)
			armSpeed = 0; //find right value, might need to use PID this time
			armMult = 1.0;
		}
*/
	armMult = 1;
	speed = armLimitCheck(speed);
    leftArm1.set(-(speed * armMult));
	rightArm1.set(speed * armMult);
	System.out.println("Arm " + rightArm1.getPosition());
	System.out.println("Wrist Gyro " + intakeGyro1.getAngle());
  }


  public double wristLimitCheck(double speed) {
	if (!wristBackLimit1.get() && speed <= 0.0) { //get some limit switches
		speed = 0;
	}
	else if(!wristFrontLimit1.get() && speed >= 0.0) { // get a limit switch
		speed = -0;
	}
		return speed;
}

  public void wrist(double speed){
	speed = wristLimitCheck(speed);
	wristMult = .5;
	wrist1.set(speed * wristMult);
  }

}
