/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.*;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Runs the drivebase in auton control mode using a timer.
 * 
 * @author ThunderChickens 217
 */
public class AutonDriveT extends Command {
    boolean isDriveStraight = false;
    double speed;

    /**
     * Runs the drivebase in auton control mode using a timer.
     * 
     * @param speed
     *        The forward/backward speed
     * @param time
     *        The time to drive
     * @param isDriveStraight
     *        {@code true} [not default] if the robot should use gyro correction to drive straight
     * 
     * @author ThunderChickens 217
     */
    public AutonDriveT(double speed, double time, boolean isDriveStraight) {
        this(speed, time);
        this.isDriveStraight = isDriveStraight;
    }

    /**
     * Runs the drivebase in auton control mode using a timer.
     * 
     * @param speed
     *        The forward/backward speed
     * @param time
     *        The time to drive
     * 
     * @author ThunderChickens 217
     */
    public AutonDriveT(double speed, double time) {
        this.speed = speed;
        setTimeout(time);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.kDrivingSubsystem.drive(speed, isDriveStraight);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.kDrivingSubsystem.drive(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.kDrivingSubsystem.drive(0);
    }
}