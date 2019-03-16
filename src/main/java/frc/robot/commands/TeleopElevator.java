/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import org.team217.*;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

/**
 * Runs the elevator in teleop control mode.
 * 
 * @author ThunderChickens 217
 */
public class TeleopElevator extends Command {
    boolean isPreset = false;

    /**
     * Runs the elevator in teleop control mode.
     * 
     * @author ThunderChickens 217
     */
    public TeleopElevator() {
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
        if (PresetState.getPOVStatus()) {
            isPreset = PresetState.getStatus();
        }
        else if (!PresetState.getStatus()) {
            isPreset = false;
        }

        if (!isPreset) {
            double speed = Range.deadband(Robot.m_oi.oper.getY(), 0.08);
            Robot.kLiftingMechanism.elevator(speed);
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
        Robot.kLiftingMechanism.elevator(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.kLiftingMechanism.elevator(0);
    }
}
