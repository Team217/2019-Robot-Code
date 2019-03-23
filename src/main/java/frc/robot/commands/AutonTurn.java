/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import org.team217.*;
import org.team217.pid.*;
import frc.robot.*;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Runs the drivebase in auton control mode using a {@code PigeonIMU} and {@code PID}.
 * 
 * @author ThunderChickens 217
 */
public class AutonTurn extends Command {
    PID turnPID = new PID(100);
    APID turnAPID;
    double target = 0.0;

    /**
     * Runs the drivebase in auton control mode using a {@code PigeonIMU}.
     * 
     * @param target
     *        The target angle, in degrees
     * @param kP
     *        The kP value for {@code PID}
     * @param kI
     *        The kI value for {@code PID}
     * @param kD
     *        The kD value for {@code PID}
     * @param accelTime
     *        The time to accelerate to max speed, in seconds
     * @param timeout
     *        The time before automatically ending the command, in seconds
     * 
     * @author ThunderChickens 217
     */
    public AutonTurn(double target, double kP, double kI, double kD, double accelTime, double timeout) {
        requires(Robot.kDrivingSubsystem);
        setTimeout(timeout);
        
        this.target = target;
        turnAPID = new APID(turnPID.setPID(kP, kI, kD), accelTime);
    }

    /**
     * Runs the drivebase in auton control mode using a {@code PigeonIMU}.
     * 
     * @param target
     *        The target angle, in degrees
     * @param kP
     *        The kP value for {@code PID}
     * @param kI
     *        The kI value for {@code PID}
     * @param kD
     *        The kD value for {@code PID}
     * @param accelTime
     * @param timeout
     *        The time before automatically ending the command, in seconds
     * 
     * @author ThunderChickens 217
     */
    public AutonTurn(double target, double kP, double kI, double kD, double timeout) {
        this(target, kP, kI, kD, 0.0, timeout);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        turnAPID.initialize();
        Robot.kDrivingSubsystem.targetAngle = target;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.kDrivingSubsystem.autonTurn(turnAPID.getOutput(RobotMap.pigeonDrive.getAngle(), target));
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return Num.isWithinRange(RobotMap.pigeonDrive.getAngle(), target - 2, target + 2) || isTimedOut();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.kDrivingSubsystem.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.kDrivingSubsystem.set(0);
    }
}