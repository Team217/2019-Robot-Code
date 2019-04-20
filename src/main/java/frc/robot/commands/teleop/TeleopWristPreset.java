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
import org.team217.rev.*;

/**
 * Runs the wrist in teleop control mode using {@code APID} to reach a preset.
 * 
 * @author ThunderChickens 217
 */
public class TeleopWristPreset extends Command {
    CANSparkMax rightArm1 = RobotMap.rightArm;

    boolean isPreset = false;
    boolean isAuto = false;
    boolean isBack = false;
    boolean setBack = true;
    boolean lastBack = false;

    Preset presetState = Preset.Manual;
    
    
    /**
     * Runs the wrist in teleop control mode using {@code PID} to reach a preset.
     * 
     * @author ThunderChickens 217
     */
    public TeleopWristPreset() {
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
            isPreset = PresetState.getWristStatus();
        }
        else if (!PresetState.getWristStatus()) {
            isPreset = false;
            setBack = true;
            presetState = Preset.Manual;
            Robot.kWristSubsystem.lastPreset = presetState;
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
            presetState = PresetState.getPreset();
            Robot.kWristSubsystem.preset(presetState, isBack);
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
        Robot.kWristSubsystem.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.kWristSubsystem.reset();
    }
}