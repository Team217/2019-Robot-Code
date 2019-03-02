package frc.robot.commands;

import frc.robot.*;
import org.team217.*;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleopDrive extends Command {
    public TeleopDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    boolean antiTipOn = false;

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.kDrivingSubsystem.resetVisionPID();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double speed = Range.deadband(Robot.m_oi.driver.getY(), 0.08);

        if (Robot.kClimbingSubsystem.isClimbing()) {
            speed *= -1.0;
            Robot.kDrivingSubsystem.drive(speed);
        }
        else {
            if (Robot.m_oi.leftTriggerDriver.get()) {
                antiTipOn = false;
            }
            else if (Robot.m_oi.rightTriggerDriver.get()) {
                antiTipOn = true;
            }
    
            if (Robot.m_oi.rightBumperDriver.get()) {
                Robot.kDrivingSubsystem.visionDrive(speed, antiTipOn);
            }
            else {
                Robot.kDrivingSubsystem.resetVisionPID();
                
                double turn = Range.deadband(Robot.m_oi.driver.getZ(), 0.08);
                Robot.kDrivingSubsystem.drive(speed, turn, antiTipOn);
            }
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}