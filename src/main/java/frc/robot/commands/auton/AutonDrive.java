/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auton;

import org.team217.pid.*;
import frc.robot.*;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Runs the drivebase in auton control mode using {@code PID}.
 * 
 * @author ThunderChickens 217
 */
public class AutonDrive extends Command {
    APID driveAPID;
    boolean isDriveStraight = false;
    boolean forward = true;
    double target = 0.0;

    /**
     * Runs the drivebase in auton control mode using {@code PID}.
     * 
     * @param target
     *        The PID taret
     * @param pid
     *        The {@code PID} variable
     * @param accelTime
     *        The time to accelerate to max speed, in seconds
     * @param isDriveStraight
     *        {@code true} [not default] if the robot should use gyro correction to drive straight
     * 
     * @author ThunderChickens 217
     */
    public AutonDrive(double target, PID pid, double accelTime, boolean isDriveStraight) {
        requires(Robot.kDrivingSubsystem);
        
        this.isDriveStraight = isDriveStraight;
        this.target = target;
        driveAPID = new APID(pid.setTimeout(100), accelTime);
    }

    /**
     * Runs the drivebase in auton control mode using {@code PID}.
     * 
     * @param target
     *        The PID taret
     * @param pid
     *        The {@code PID} variable
     * @param accelTime
     *        The time to accelerate to max speed, in seconds
     * @param isDriveStraight
     *        {@code true} [not default] if the robot should use gyro correction to drive straight
     * @param timeout
     *        The time before automatically ending the command, in seconds
     * 
     * @author ThunderChickens 217
     */
    public AutonDrive(double target, PID pid, double accelTime, boolean isDriveStraight, double timeout) {
        this(target, pid, accelTime, isDriveStraight);
        setTimeout(timeout);
    }

    /**
     * Runs the drivebase in auton control mode using {@code PID}.
     * 
     * @param target
     *        The PID taret
     * @param pid
     *        The {@code PID} variable
     * @param isDriveStraight
     *        {@code true} [not default] if the robot should use gyro correction to drive straight
     * 
     * @author ThunderChickens 217
     */
    public AutonDrive(double target, PID pid, boolean isDriveStraight) {
        this(target, pid, 0.0, isDriveStraight);
    }

    /**
     * Runs the drivebase in auton control mode using {@code PID}.
     * 
     * @param target
     *        The PID taret
     * @param pid
     *        The {@code PID} variable
     * @param isDriveStraight
     *        {@code true} [not default] if the robot should use gyro correction to drive straight
     * @param timeout
     *        The time before automatically ending the command, in seconds
     * 
     * @author ThunderChickens 217
     */
    public AutonDrive(double target, PID pid, boolean isDriveStraight, double timeout) {
        this(target, pid, 0.0, isDriveStraight, timeout);
    }

    /**
     * Runs the drivebase in auton control mode using {@code PID}.
     * 
     * @param target
     *        The PID taret
     * @param pid
     *        The {@code PID} variable
     * 
     * @author ThunderChickens 217
     */
    public AutonDrive(double target, PID pid) {
        this(target, pid, 0.0, false);
    }

    /**
     * Runs the drivebase in auton control mode using {@code PID}.
     * 
     * @param target
     *        The PID taret
     * @param pid
     *        The {@code PID} variable
     * @param timeout
     *        The time before automatically ending the command, in seconds
     * 
     * @author ThunderChickens 217
     */
    public AutonDrive(double target, PID pid, double timeout) {
        this(target, pid, 0.0, false, timeout);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        if (RobotMap.rightMaster.getPosition() > target) {
            forward = false;
        }

        driveAPID.initialize();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        double speed = driveAPID.getOutput(RobotMap.rightMaster.getPosition(), target);
        Robot.kDrivingSubsystem.set(speed, isDriveStraight);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return isTimedOut() || ((forward) ? RobotMap.rightMaster.getPosition() >= target : RobotMap.rightMaster.getPosition() <= target);
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