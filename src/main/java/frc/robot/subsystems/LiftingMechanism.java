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
    CANSparkMax leftArm1 = RobotMap.leftArm;
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
        if (!telescopeOutLimit1.get() && speed >= 0) {   
            speed = 0;
        }
        else if (!telescopeInLimit1.get()){
            telescope1.resetEncoder();
            if(speed <= 0){
                speed = 0;
            }
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
        double telescopeMult = 1;

        if(telescope1.getEncoder() <= 5000){
            telescopeMult = .75;
        }
        else if(telescope1.getEncoder() >= 15000){
            telescopeMult = .75;
        }

        speed = telescopeLimitCheck(speed);
        telescope1.set(speed * telescopeMult);
    }


    /** Moves the telescope to the out position. */
    public void telescopeOut() {
        if (!isTelescopeOut) {
            telescopeAPID.initialize();
            isTelescopeOut = true;
        }

        double speed = telescopeAPID.getOutput(telescope1.getEncoder(), 28000); //TODO: Get correct value
        telescope(speed);
    }

    /** Moves the telescope to the in position. */
    public void telescopeIn() {
        if (isTelescopeOut) {
            telescopeAPID.initialize();
            isTelescopeOut = false;
        }

        double speed = telescopeAPID.getOutput(telescope1.getEncoder(), 0);
        telescope(speed);
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
            leftElevator1.resetEncoder();
            if (speed > 0){
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
        double elevatorMult = 0.65;
        
        if (leftElevator1.getEncoder() <= -11500 && speed <= 0) { //encoder values are wrong, check the logic
        	elevatorMult = .35;
        }
        else if(leftElevator1.getEncoder() >= -5500 && speed >= 0.0) { //this one too
        	elevatorMult = .25;
        }

        if (leftElevator1.getEncoder() <= -16180 && speed < 0) { //Check this first so we can still hold it up
            speed = 0;
        }
        
        if (speed != 0) {
            Robot.kLiftingMechanism.lastElevatorPos = leftElevator1.getEncoder();
        }
     //   else {
     //       speed = RobotMap.elevatorHoldPID.getOutput(RobotMap.rightElevator.getEncoder(), Robot.kLiftingMechanism.lastElevatorPos);
     //       elevatorMult = 1;
     //   }  obselete
        
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
            if (rightArm1.getPosition() <= 0 && speed <= 0.0) { //get some limit switches, make sure logic is right
                speed = 0;
            }
            else if (rightArm1.getPosition() >= 109 && speed >= 0.0) { //Practice: 107 when tele is out, ; Comp: -207
                speed = 0;
            }
            else if (rightArm1.getPosition() >= 109 && speed >= 0.0){
                speed = 0;
            }
        } 
        else {
            if (rightArm1.getPosition() <= 0 && speed <= 0.0) { //get some limit switches, make sure logic is right
                speed = 0;
            }
            else if (rightArm1.getPosition() >= 109 && speed >= 0.0) { //Practice: 107 when tele is out, ; Comp: -207
                speed = 0;
            }
            else if (rightArm1.getPosition() >= 109 && speed >= 0.0){
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
        double armMult = .50;
       
        if (!Robot.kClimbingSubsystem.isClimbing()) {
            if (speed != 0) {
                lastArmPos = RobotMap.rightArm.getPosition();
            }
            else {
                speed = RobotMap.armHoldPID.getOutput(RobotMap.rightArm.getPosition(), Robot.kLiftingMechanism.lastArmPos);
            }
    
            speed = armLimitCheck(speed);
        }
        else {
            lastArmPos = RobotMap.rightArm.getPosition();
        }
      
        rightArm1.set(speed * armMult);
        leftArm1.set(speed * armMult);

        System.out.println("Arm " + rightArm1.getPosition());
        System.out.println("Arm speed" + speed);
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

        if (rightArm1.getPosition() < 60) {
            speed = Range.inRange(-wristGyroPID.getOutput(armAngle, 0), -1.0, 1.0);
        }
        else if (rightArm1.getPosition() >= 60 && rightArm1.getPosition() <= 75) {
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
        System.out.println("Here");
        double target = 0;
        switch (presetState) {
        case Low:
            target = (isBack) ? 111.14 : 13;
            break;
        case Mid:
            target = (isBack) ? 73.1 : 58;
            break;
        case High:
            target = (isBack) ? 68.168 : 56.5;
            break;
        case RocketAdj:
            switch (lastPresetA) {
                case Low:
                target = (isBack) ? 111.14 : 13;
                break;
            case Mid:
                target = (isBack) ? 73.1 : 58;
                break;
            case High:
                target = (isBack) ? 68.168 : 56.5;
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
            target = (isBack) ? -4140 : -773;
            break;
        case Mid:
            target = (isBack) ? -3950 : -4090;
            break;
        case High:
            target = (isBack) ? -377 : -4100;
            break;
        case RocketAdj:
            switch (lastPresetW) {
                case Low:
                target = (isBack) ? -4140 : -377;
                break;
            case Mid:
                target = (isBack) ? -3950 : -3950;
                break;
            case High:
                target = (isBack) ? -377 : -4140;
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
                speed = Range.inRange(-wristAPID.getOutput(wrist1.getEncoder(), target), -1, 1);
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
            target = -6800;
            break;
        case Mid:
            target = 0;
            break;
        case High:
            target = -13762;
            break;
        case RocketAdj:
            switch (lastPresetE) {
            case Low:
                target = -5183;
                break;
            case Mid:
                target = 0;
                break;
            case High:
                target = -11762;
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
                speed = Range.inRange(elevatorAPID.getOutput(leftElevator1.getEncoder(), target), -.6, .6);
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
