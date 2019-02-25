/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import org.team217.pid.*;
import frc.robot.*;

import edu.wpi.first.wpilibj.command.Command;

public class AutonDrive extends Command {
    PID drivePID = new PID(100);
    APID driveAPID;
    boolean isDriveStraight = false;
    boolean forward = true;
    double target = 0.0;

    public AutonDrive(double target, double kP, double kI, double kD, double accelTime, boolean isDriveStraight, double timeout) {
        requires(Robot.kDrivingSubsystem);
        setTimeout(timeout);
        
        this.isDriveStraight = isDriveStraight;
        this.target = target;
        driveAPID = new APID(drivePID.setPID(kP, kI, kD), accelTime);
    }

    public AutonDrive(double target, double kP, double kI, double kD, boolean isDriveStraight, double timeout) {
        this(target, kP, kI, kD, 0.0, isDriveStraight, timeout);
    }

    public AutonDrive(double target, double kP, double kI, double kD, double timeout) {
        this(target, kP, kI, kD, 0.0, false, timeout);
    }

    public AutonDrive(double target, double kP, double kI, double kD, double accelTime, boolean isDriveStraight) {
        this(target, kP, kI, kD, accelTime, isDriveStraight, 0.0);
    }

    public AutonDrive(double target, double kP, double kI, double kD, boolean isDriveStraight) {
        this(target, kP, kI, kD, 0.0, isDriveStraight, 0.0);
    }

    public AutonDrive(double target, double kP, double kI, double kD) {
        this(target, kP, kI, kD, 0.0, false, 0.0);
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
        Robot.kDrivingSubsystem.autonDrive(speed, isDriveStraight);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return isTimedOut() || ((forward) ? RobotMap.rightMaster.getPosition() >= target : RobotMap.rightMaster.getPosition() <= target);
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.kDrivingSubsystem.autonDrive(0, false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.kDrivingSubsystem.autonDrive(0, false);
    }
}