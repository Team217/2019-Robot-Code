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

import org.team217.*;
import org.team217.pid.*;

/**
 * Manages the robot's arm, elevator, and wrist.
 * 
 * @author ThunderChickens 217
 */
public class LiftingMechanism extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    WPI_TalonSRX telescope1 = RobotMap.telescope;
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
    DigitalInput telescopeInLimit1 = RobotMap.telescopeInLimit;
    DigitalInput telescopeOutLimit1 = RobotMap.telescopeOutLimit;

    public double lastElevatorPos = 0;
    public double lastArmPos = 0;

    boolean isBack = false, setBack = true;
    
    PID wristGyroPID = RobotMap.wristGyroPID;

    /** Variable that contains information on the current arm preset state. */
    public static enum Preset {
        Manual,
        Low,
        Mid,
        High,
        RocketAdj
    }
    Preset lastPresetA = Preset.Manual;
    Preset lastPresetW = Preset.Manual;
    Preset lastPresetE = Preset.Manual;
    Preset lastPresetT = Preset.Manual;

    APID armAPID = RobotMap.armAPID;
    APID wristAPID = RobotMap.wristAPID;
    APID elevatorAPID = RobotMap.elevAPID;
    APID telescopeAPID = RobotMap.telescopeAPID;

    boolean isTelescopeOut = false;

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
        leftElevator1.follow(rightElevator1);
    }

    /**
     * Returns the new telescope speed after checking the telescope limits.
     * 
     * @param speed
     *        The current telescope speed
     */
    public double telescopeLimitCheck(double speed) {
        if (!telescopeOutLimit1.get() && speed <= 0) {
            speed = 0;
 //           System.out.println("In");
        }
        else if (!telescopeInLimit1.get() && speed >= 0) {
            speed = 0;
 //           System.out.println("Out");
        }
        return speed;
    }

    /**
     * Runs the telescope.
     * 
     * @param speed
     *        The telescope speed
     */
    public void telescope(double speed) {
        speed = telescopeLimitCheck(speed);
//        System.out.println(speed);
        telescope1.set(speed);
    }

    /** Moves the telescope to the out position. */
    public void telescopeOut() {
        telescope(-.25);
        isTelescopeOut = true;
    }

    /** Moves the telescope to the in position. */
    public void telescopeIn() {
        telescope(.25);
        isTelescopeOut = false;
    }

    /** Returns {@code true} if the telescope is out. */
    public boolean getTelescopeOut() {
        return isTelescopeOut;
    }

    /**
     * Returns the new elevator speed after checking the elevator limits.
     * 
     * @param speed
     *        The current elevator speed
     */
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

        if (rightElevator1.getEncoder() <= -16180 && speed < 0) { //Check this first so we can still hold it up
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
        if(isTelescopeOut) {
            if (rightArm1.getPosition() >= 0 && speed >= 0.0) { //get some limit switches, make sure logic is right
                speed = 0;
            }
            else if (rightArm1.getPosition() <= -125 && speed <= 0.0 && rightElevator1.getEncoder() >= -10000) { //Practice: 107 when tele is out, ; Comp: -207
                speed = 0;
            }
            else if (rightArm1.getPosition() <= -143 && speed <= 0.0 && rightElevator1.getEncoder() < -10000){
                speed = 0;
            }
        } 
        else {
            if (rightArm1.getPosition() >= 0 && speed >= 0.0) { //get some limit switches, make sure logic is right
                speed = 0;
            }
            else if (rightArm1.getPosition() <= -125 && speed <= 0.0 && rightElevator1.getEncoder() >= -10000) { //Practice: 107 when tele is out, ; Comp: -207
                speed = 0;
            }
            else if (rightArm1.getPosition() <= -143 && speed <= 0.0 && rightElevator1.getEncoder() < -10000){
                speed = 0;
            }
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

      //  speed = armLimitCheck(speed);
        rightArm1.set(speed * armMult);

        System.out.println("Arm " + rightArm1.getPosition());
 //       System.out.println("Wrist Gyro " + intakeGyro1.getAngle());
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
        double wristMult = .7;
        speed = wristLimitCheck(speed);
        wrist1.set(speed * wristMult);
    }

    /** Runs the wrist using an {@code AnalogGyro}. */
    public void autoWrist() {
        double armAngle = intakeGyro1.getAngle(); // Might be pitch or yaw, depending on how electical electricities
        double speed = 0;

        if (rightArm1.getPosition() > -70) {
            speed = Range.inRange(-wristGyroPID.getOutput(armAngle, 0), -1.0, 1.0);
        }
        else if (rightArm1.getPosition() <= -70 && rightArm1.getPosition() >= -85) {
            if (armAngle < 80) {
                speed = -.7073;
            }
            else if (armAngle > 100) {
                speed = .7073;
            }
        }
        else {
            speed = Range.inRange(-wristGyroPID.getOutput(armAngle, 178), -0.5, 0.5);
        }

        Robot.kLiftingMechanism.wrist(speed);
    }

    /**
     * Runs the arm using {@code APID} to reach a preset.
     * 
     * @param presetState
     *        The {@code Preset} state 
     * @param isBack
     *        {@code true} if the preset moves the arm to the back region of the bot
     */
    public void armPreset(Preset presetState, boolean isBack) {
        double target = 0;
        switch (presetState) {
        case Low:
            target = (isBack) ? -143 : -3.5;
            break;
        case Mid:
            target = (isBack) ? -92 : -57;
            break;
        case High:
            target = (isBack) ? -85 : -67;
            break;
        case RocketAdj:
            switch (lastPresetA) {
            case Low:
                target = (isBack) ? -143 : -3.5;
                break;
            case Mid:
                target = (isBack) ? -92 : -57;
                break;
            case High:
                target = (isBack) ? -85 : -67;
                break;
            default:
                presetState = Preset.Manual;
                break;
            }
        default:
            break;
        }

        double speed = 0;
        
        if (!presetState.equals(Preset.Manual)) {
            if (!lastPresetA.equals(presetState)) {
                armAPID.initialize();
            }
            else {
                speed = Range.inRange(armAPID.getOutput(rightArm1.getPosition(), target), -.45, .45);
            }

            if (!presetState.equals(Preset.RocketAdj)) {
                lastPresetA = presetState;
            }
        }

        Robot.kLiftingMechanism.arm(speed);
    }

    /**
     * Runs the wrist using {@code APID} to reach a preset.
     * 
     * @param presetState
     *        The {@code Preset} state 
     * @param isBack
     *        {@code true} if the preset moves the arm to the back region of the bot
     */
    public void wristPreset(Preset presetState, boolean isBack) {
        double target = 0;
        switch (presetState) {
        case Low:
            target = (isBack) ? 3468 : 124;
            break;
        case Mid:
            target = (isBack) ? 1023 : 2834;
            break;
        case High:
            target = (isBack) ? 657 : 3345;
            break;
        case RocketAdj:
            switch (lastPresetW) {
            case Low:
                target = (isBack) ? 3468 : 124;
                break;
            case Mid:
                target = (isBack) ? 1023 : 2834;
                break;
            case High:
                target = (isBack) ? 657 : 3345;
                break;
            default:
                presetState = Preset.Manual;
                break;
            }
        default:
            break;
        }

        double speed = 0;
        
        if (!presetState.equals(Preset.Manual)) {
            if (!lastPresetW.equals(presetState)) {
                wristAPID.initialize();
            }
            else {
                speed = Range.inRange(wristAPID.getOutput(wrist1.getEncoder(), target), -.6, .6);
            }

            if (!presetState.equals(Preset.RocketAdj)) {
                lastPresetW = presetState;
            }
        }

        Robot.kLiftingMechanism.wrist(speed);
    }

    /**
     * Runs the elevator using {@code APID} to reach a preset.
     * 
     * @param presetState
     *        The {@code Preset} state
     */
    public void elevatorPreset(Preset presetState) {
        double target = 0;
        switch (presetState) {
        case Low:
            target = -12762;
            break;
        case Mid:
            target = 0;
            break;
        case High:
            target = -13300;
            break;
        case RocketAdj:
            switch (lastPresetE) {
            case Low:
                target = -12762;
                break;
            case Mid:
                target = 0;
                break;
            case High:
                target = -13300;
                break;
            default:
                presetState = Preset.Manual;
                break;
            }
        default:
            break;
        }

        double speed = 0;
        
        if (!presetState.equals(Preset.Manual)) {
            if (!lastPresetE.equals(presetState)) {
                elevatorAPID.initialize();
            }
            else {
                speed = Range.inRange(elevatorAPID.getOutput(rightElevator1.getEncoder(), target), -.6, .6);
            }

            if (!presetState.equals(Preset.RocketAdj)) {
                lastPresetE = presetState;
            }
        }

        Robot.kLiftingMechanism.elevator(speed);
    }

    /**
     * Runs the telescope using {@code APID} to reach a preset.
     * 
     * @param presetState
     *        The {@code Preset} state
     */
    public void telescopePreset(Preset presetState) {
        switch (presetState) {
        case Low:
            telescopeIn();
            break;
        case Mid:
            telescopeIn();
            break;
        case High:
            telescopeOut();
            break;
        case RocketAdj:
            switch (lastPresetE) {
            case Low:
            telescopeIn();
                break;
            case Mid:
                telescopeIn();
                break;
            case High:
                telescopeOut();
                break;
            default:
                presetState = Preset.Manual;
                break;
            }
        default:
            break;
        }
        
        if (!presetState.equals(Preset.Manual) && !presetState.equals(Preset.RocketAdj)) {
            lastPresetT = presetState;
        }
    }
}
