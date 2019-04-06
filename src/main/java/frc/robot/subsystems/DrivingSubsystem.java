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

    PID driveStraightPID = new PID(0.015, 0, 0);
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
        double angle = RobotMap.pigeonDrive.getPitch();
        int tipSign = Num.sign(angle);
        
        if (Math.abs(angle) >= antiTipAngle) {
            useAntiTipAngle = true;
            leftSpeed = tipSign * -0.3707;
            rightSpeed = tipSign * 0.3707;
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
    public void set(double speed) {
        set(speed, false);
    }

    /**
     * Drives the bot with gyro correction.
     * 
     * @param speed
     *        The forward/backward speed
     * @param isDriveStraight
     *        {@code true} [default] if the bot should use the {@code PigeonIMU} to drive straight
     */
    public void set(double speed, boolean isDriveStraight) {
        double turn = 0.0;

        if (isDriveStraight) {
            double correction = driveStraightPID.getOutput(RobotMap.pigeonDrive.getAngle(), targetAngle);
            correction = Num.inRange(correction, 0.25);
            turn = correction * Math.abs(speed);
            speed *= 1 - Math.abs(correction);
        }

        set(speed, turn, false);
    }

    /**
     * Drives the bot with turning.
     * 
     * @param speed
     *        The forward/backward speed
     * @param turn
     *        The turn speed
     */
    public void set(double speed, double turn) {
        set(speed, turn, false);
    }

    /**
     * Drives the bot with turning and antitip.
     * 
     * @param speed
     *        The forward/backward speed
     * @param turn
     *        The turn speed
     * @param antiTipOn
     *        {@code true} [not default] if the bot should run antitip code
     */
    public void set(double speed, double turn, boolean antiTipOn) {
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
        set(0.0, turn);
    }

    /**
     * Drives the bot with vision-managed turning.
     * 
     * @param speed
     *        The forward/backward speed
     * @param isCamFront
     *        {@code true} if using the front camera
     */
    public void visionDrive(double speed, boolean isCamFront) {
        visionDrive(speed, isCamFront, false);
    }

    /**
     * Drives the bot with vision-managed turning and antitip.
     * 
     * @param speed
     *        The forward/backward speed
     * @param isCamFront
     *        {@code true} if using the front camera
     * @param antiTipOn
     *        {@code true} [not default] if the bot should run antitip code
     */
    public void visionDrive(double speed, boolean isCamFront, boolean antiTipOn) {
        double turn = visionTurn(isCamFront);
        set(speed, turn, antiTipOn);
    }

    /**
     * Calculates and returns the turn speed for vision-managed turning.
     * 
     * @param isCamFront
     *        {@code true} if using the front camera
     */
    public double visionTurn(boolean isCamFront) {
        double turn = 0;
        double x = isCamFront ? Robot.getX1Vis() : Robot.getX2Vis();
        double area = 4 * (isCamFront ? Robot.getArea1Vis() : Robot.getArea2Vis());
        double kP = Num.inRange(.0125 / Math.sqrt(area) - .01, 0.01, 0.025);

        visionPID.setP(kP);

        if(!Num.isWithinRange(x, 0.5 / area)) {
            //turn = visionPID.getOutput((isCamFront ? -0.2 : -0.55) * Math.sqrt(area), x);
            turn = visionPID.getOutput((isCamFront ? -7 : -10) + area, x);
        }

        return turn;
    }

    /** Resets the vision-managed turning's PID. */
    public void resetVisionPID() {
        visionPID.resetErrors();
    }

    public void reset() {
        RobotMap.leftMaster.set(0);
        RobotMap.rightMaster.set(0);
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