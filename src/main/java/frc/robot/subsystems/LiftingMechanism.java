/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import org.team217.ctre.*;
import org.team217.rev.*;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.*;

/**
 * Manages the robot's arm, elevator, and wrist.
 * 
 * @author ThunderChickens 217
 */
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

    public double lastElevatorPos = 0;
    public double lastArmPos = 0;

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
        leftElevator1.follow(rightElevator1);
    }

    public double elevatorLimitCheck(double speed) {
        if (!RobotMap.elevatorBottomLimit.get()) {
            rightElevator1.resetEncoder();
            if (speed > 0) {
                speed = 0;
            }
        }
        else if (!RobotMap.elevatorTopLimit.get() && speed < 0) {
            speed = 0;
        }

        return speed;
    }

    /**
     * Runs the elevator.
     * 
     * @param speed
     *        The elevator speed
     */
    public void elevator(double speed) {
        
        //elevatorMaintainPID1.setPID(0.0001417, 0.00000117, 0); //not even close to the right PID here

        double elevatorMult = 0.65;
        
        if (rightElevator1.getEncoder() <= -11500 && speed <= 0) { //encoder values are wrong, check the logic
        	elevatorMult = .35;
        }
        else if(rightElevator1.getEncoder() >= -5500 && speed >= 0.0) { //this one too
        	elevatorMult = .25;
        }

        if (rightElevator1.getEncoder() >= 16180 && speed < 0) { //Check this first so we can still hold it up
            speed = 0;
        }
        
        if (speed != 0) {
            Robot.kLiftingMechanism.lastElevatorPos = RobotMap.rightElevator.getEncoder();
        }
        else {
            speed = RobotMap.elevatorHoldPID.getOutput(RobotMap.rightElevator.getEncoder(), Robot.kLiftingMechanism.lastElevatorPos);
            elevatorMult = 1;
        }
        
        speed = elevatorLimitCheck(speed);
        rightElevator1.set(speed * elevatorMult);
        leftElevator1.set((speed * elevatorMult));
    }

    /**
     * Returns the new arm speed after checking the arm limits.
     * 
     * @param speed
     *        The current arm speed
     */
    public double armLimitCheck(double speed) {
        if (rightArm1.getPosition() >= 0 && speed >= 0.0) { //get some limit switches, make sure logic is right
            speed = 0;
        }
        else if (rightArm1.getPosition() <= -125 && speed <= 0.0) { // get a limit switch //Practice -188 Comp -207
            speed = 0;
        }
        return speed;
    }

    /**
     * Runs the arm.
     * 
     * @param speed
     *        The arm speed
     */
    public void arm(double speed) {
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

        double armMult = .50;

        if (speed != 0) {
            lastArmPos = RobotMap.rightArm.getPosition();
        }
        else {
            speed = RobotMap.armHoldPID.getOutput(RobotMap.rightArm.getPosition(), Robot.kLiftingMechanism.lastArmPos);
            armMult = 1;
        }

        speed = armLimitCheck(speed);
        leftArm1.set(-speed * armMult);
        rightArm1.set(speed * armMult);

        System.out.println("Arm " + rightArm1.getPosition());
        System.out.println("Wrist Gyro " + intakeGyro1.getAngle());
    }

    /**
     * Returns the new wrist speed after checking the wrist limits.
     * 
     * @param speed
     *        The current wrist speed
     */
    public double wristLimitCheck(double speed) {
        if (!wristBackLimit1.get() && speed <= 0.0) { 
            speed = 0;
        }
        else if (!wristFrontLimit1.get() && speed >= 0.0) { 
            speed = 0;
        }
        return speed;
    }

    /**
     * Runs the wrist.
     * 
     * @param speed
     *        The wrist speed
     */
    public void wrist(double speed) {
        double wristMult = .35;
        speed = wristLimitCheck(speed);
        wrist1.set(speed * wristMult);
    }

}
