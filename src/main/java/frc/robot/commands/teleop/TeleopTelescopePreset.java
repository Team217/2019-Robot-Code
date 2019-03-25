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

/**
 * Runs the telescope in teleop control mode.
 * 
 * @author ThunderChickens 217
 */
public class TeleopTelescopePreset extends Command {

    boolean isPreset = false;
    Preset presetState = Preset.Manual;
    
    /**
     * Runs the telescope in teleop control mode.
     * 
     * @author ThunderChickens 217
     */
    public TeleopTelescopePreset() {
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
            Robot.kTelescopeSubsystem.lastPreset = presetState;
        }

        if (isPreset) {
            presetState = PresetState.getPresetState();
            Robot.kTelescopeSubsystem.preset(presetState);
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
        Robot.kTelescopeSubsystem.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.kTelescopeSubsystem.set(0);
    }
}