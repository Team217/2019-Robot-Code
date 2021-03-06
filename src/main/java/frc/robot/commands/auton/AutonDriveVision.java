/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auton;

import frc.robot.*;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Runs the drivebase in auton control mode using vision.
 * 
 * @author ThunderChickens 217
 */
public class AutonDriveVision extends Command {
    double speed;
    boolean isCamFront;
    double targetArea = 1;

    /**
     * Runs the drivebase in auton control mode using vision.
     * 
     * @param speed
     *        The forward/backward speed
     * @param isCamFront
     *        {@code true} if using the front camera
     * @param timeout
     *        The time before automatically ending the command, in seconds
     * 
     * @author ThunderChickens 217
     */
    public AutonDriveVision(double speed, boolean isCamFront, double targetArea, double timeout) {
        this.speed = speed;
        this.isCamFront = isCamFront;
        this.targetArea = targetArea;
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
        Robot.kDrivingSubsystem.visionDrive(-speed, isCamFront);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        double area = isCamFront ? Robot.getArea1Vis() : Robot.getArea2Vis();
        return area < 0.2 ? isTimedOut() : area >= targetArea;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.kDrivingSubsystem.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.kDrivingSubsystem.reset();
    }
}