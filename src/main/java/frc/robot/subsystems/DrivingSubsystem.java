package frc.robot.subsystems;

import org.team217.pid.*;
import frc.robot.*;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
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
    public double targetAngle = 0.0;
    boolean useAntiTipAngle = false;

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

    public void teleopDrive(double speed, double turn, boolean antiTipOn) {
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

    public void autonDrive(double speed, boolean isDriveStraight) {
        double turn = 0.0;

        if (isDriveStraight) {
            double correction = 0.5 * driveStraightPID.getOutput(RobotMap.pigeonDrive.getAngle(), targetAngle);
            turn = correction * speed;
            speed *= (1 - Math.abs(correction));
        }

        teleopDrive(speed, turn, false);
    }

    public void autonTurn(double turn) {
        teleopDrive(0.0, turn, false);
    }
}

final class MotorSpeed {
    public double leftSpeed = 0.0;
    public double rightSpeed = 0.0;

    public MotorSpeed() {
    }

    public MotorSpeed(double leftSpeed, double rightSpeed) {
        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;
    }
}