/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;
import frc.robot.PresetState.Preset;

import org.team217.pid.*;

/**
 * Runs the elevator in teleop control mode using {@code APID} to reach a preset.
 * 
 * @author ThunderChickens 217
 */
public class TeleopElevatorPreset extends Command {
    boolean isPreset = false;
    APID elevAPID = RobotMap.elevAPID;
    double target = 0;
    boolean isBack = false;
    boolean setBack = true;
    boolean lastBack = false;


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
            setBack = true;
            presetState = Preset.Manual;
            Robot.kElevatorSubsystem.lastPreset = presetState;
        }
        
        if (setBack && PresetState.getPOVStatus()) {
            isBack = Robot.m_oi.touchPadOper.get();
            setBack = !isBack;
            if (isBack != lastBack) {
                Robot.kArmSubsystem.lastPreset = Preset.Manual;
            }
            lastBack = isBack;
        }
        else {
            setBack = !PresetState.getPOVStatus();
        }

        if (isPreset) {
            presetState = PresetState.getPresetState();
            Robot.kElevatorSubsystem.preset(presetState, isBack);
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
        Robot.kElevatorSubsystem.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.kElevatorSubsystem.reset();
    }
}
