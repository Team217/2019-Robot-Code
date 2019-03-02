package frc.robot.subsystems;

import org.team217.pid.*;
import org.team217.*;
import frc.robot.*;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Manages the robot's drivebase.
 * 
 * @author ThunderChickens 217
 */
public class DrivingSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        RobotMap.leftMidSlave.follow(RobotMap.leftMaster);
        RobotMap.leftBackSlave.follow(RobotMap.leftMaster);
        RobotMap.rightMidSlave.follow(RobotMap.rightMaster);
        RobotMap.rightBackSlave.follow(RobotMap.rightMaster);
    }

    PID driveStraightPID = new PID(0.01, 0, 0);
    PID visionPID = RobotMap.visionPID;
    public double targetAngle = 0.0;
    private boolean useAntiTipAngle = false;

    /**
     * Returns the modified motor speeds if antitip code is triggered.
     * 
     * @param leftSpeed
     *        The current left motor speed
     * @param rightSpeed
     *        The current right motor speed
     */
    protected MotorSpeed antiTip(double leftSpeed, double rightSpeed) {
        double antiTipAngle = (useAntiTipAngle) ? 6.0 : 12.0;
        if (RobotMap.pigeonDrive.getPitch() >= antiTipAngle) {
            useAntiTipAngle = true;
            leftSpeed = -0.3707;
            rightSpeed = 0.3707;
        }
        else if (RobotMap.pigeonDrive.getPitch() <= -antiTipAngle) {
            useAntiTipAngle = true;
            leftSpeed = 0.3707;
            rightSpeed = -0.3707;
        }
        else {
            useAntiTipAngle = false;
        }

        return new MotorSpeed(leftSpeed, rightSpeed);
    }

    /**
     * Drives the bot.
     * 
     * @param speed
     *        The forward/backward speed
     */
    public void drive(double speed) {
        drive(speed, false);
    }

    /**
     * Drives the bot with gyro correction.
     * 
     * @param speed
     *        The forward/backward speed
     * @param isDriveStraight
     *        {@code true} if the bot should use the {@code PigeonIMU} to drive straight
     */
    public void drive(double speed, boolean isDriveStraight) {
        double turn = 0.0;

        if (isDriveStraight) {
            double correction = 0.5 * driveStraightPID.getOutput(RobotMap.pigeonDrive.getAngle(), targetAngle);
            turn = correction * speed;
            speed *= (1 - Math.abs(correction));
        }

        drive(speed, turn, false);
    }

    /**
     * Drives the bot with turning.
     * 
     * @param speed
     *        The forward/backward speed
     * @param turn
     *        The turn speed
     */
    public void drive(double speed, double turn) {
        drive(speed, turn, false);
    }

    /**
     * Drives the bot with turning and antitip.
     * 
     * @param speed
     *        The forward/backward speed
     * @param turn
     *        The turn speed
     * @param antiTipOn
     *        {@code true} if the bot should run antitip code
     */
    public void drive(double speed, double turn, boolean antiTipOn) {
        double leftSpeed = speed + turn;
        double rightSpeed = -speed + turn;

        if (antiTipOn) {
            MotorSpeed antiTipSpeed = antiTip(leftSpeed, rightSpeed);
            leftSpeed = antiTipSpeed.leftSpeed;
            rightSpeed = antiTipSpeed.rightSpeed;
        }

        RobotMap.leftMaster.set(leftSpeed);
        RobotMap.rightMaster.set(rightSpeed);
    }

    /**
     * Turns the bot.
     * 
     * @param turn
     *        The turn speed
     */
    public void autonTurn(double turn) {
        drive(0.0, turn);
    }

    /**
     * Drives the bot with vision-managed turning.
     * 
     * @param speed
     *        The forward/backward speed
     */
    public void visionDrive(double speed) {
        visionDrive(speed, false);
    }

    /**
     * Drives the bot with vision-managed turning and antitip.
     * 
     * @param speed
     *        The forward/backward speed
     * @param antiTipOn
     *        {@code true} if the bot should run antitip code
     */
    public void visionDrive(double speed, boolean antiTipOn) {
        double turn = visionTurn();
        drive(speed, turn, antiTipOn);
    }

    /** Calculates and returns the turn speed for vision-managed turning */
    public double visionTurn() {
        double turn = 0;
        double x = Robot.getX1Vis();
        double kP = Range.inRange(.03 / Math.sqrt(Robot.getArea1Vis()) - .01, 0.015, 0.025);

        visionPID.setP(kP);

        if(!Range.isWithinRange(x, -0.5, 0.5)) {
            turn = visionPID.getOutput(0, x);
        }

        return turn;
    }

    /** Resets the vision-managed turning's PID. */
    public void resetVisionPID() {
        visionPID.resetErrors();
    }
}

/**
 * Holds information for left and right motor speeds.
 * 
 * @author ThunderChickens 217
 */
final class MotorSpeed {
    /** The left motor speed */
    public double leftSpeed = 0.0;
    /** The right motor speed */
    public double rightSpeed = 0.0;

    /** Constructor to make a new blank object that holds information for left and right motor speeds. */
    public MotorSpeed() {
    }

    /**
     * Constructor to make a new variable that holds information for left and right motor speeds.
     * 
     * @param leftSpeed
     *        The left motor speed
     * @param rightSpeed
     *        The right motor speed
     */
    public MotorSpeed(double leftSpeed, double rightSpeed) {
        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;
    }
}