/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;
import frc.robot.PresetState.Preset;

import org.team217.pid.*;

/**
 * Runs the elevator in teleop control mode using {@code PID} to reach a preset.
 * 
 * @author ThunderChickens 217
 */
public class TeleopElevatorPreset extends Command {
    boolean isPreset = false;
    APID elevAPID = RobotMap.elevAPID;
    double target = 0;

    Preset presetState = Preset.Manual;

    /**
     * Runs the elevator in teleop control mode.
     * 
     * @author ThunderChickens 217
     */
    public TeleopElevatorPreset() {
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
            presetState = Preset.Manual;
            Robot.kElevatorSubsystem.lastPreset = presetState;
        }

        if (isPreset) {
            presetState = PresetState.getPresetState();
            Robot.kElevatorSubsystem.preset(presetState);
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
        Robot.kElevatorSubsystem.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.kElevatorSubsystem.set(0);
    }
}
