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
 * Runs the intake in teleop control mode.
 * 
 * @author ThunderChickens 217
 */
public class TeleopIntake extends Command {
    /**
     * Runs the intake in teleop control mode.
     * 
     * @author ThunderChickens 217
     */
    public TeleopIntake() {
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
        double speed = 0;

        if (Robot.m_oi.rightBumperOper.get()) { //out
            speed = -1.0;
        }
        else if(!RobotMap.ballLimit.get()){ //hold in
            speed = .05;
        }
        else if (Robot.m_oi.leftBumperOper.get()) { //in
            speed = .75;
        }
        
        Robot.kIntakeSubsystem.intake(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.kIntakeSubsystem.intake(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.kIntakeSubsystem.intake(0);
    }
}
