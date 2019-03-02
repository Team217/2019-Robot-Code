/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.*;

import edu.wpi.first.wpilibj.command.Command;

public class AutonDriveV extends Command {
    double speed;

    public AutonDriveV(double speed, double timeout) {
        this.speed = speed;
        setTimeout(timeout);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.kDrivingSubsystem.resetVisionPID();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.kDrivingSubsystem.visionDrive(speed);
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