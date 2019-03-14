/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

/**
 * Runs the climber in teleop control mode.
 * 
 * @author ThunderChickens 217
 */
public class TeleopClimb extends Command {
    /**
     * Runs the climber in teleop control mode.
     * 
     * @author ThunderChickens 217
     */
    public TeleopClimb() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (Robot.m_oi.xDriver.get()) {
            Robot.kClimbingSubsystem.setDrivePTO();
        }
        else if (Robot.m_oi.circleDriver.get()) {
            Robot.kClimbingSubsystem.setClimbPTO();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }

}