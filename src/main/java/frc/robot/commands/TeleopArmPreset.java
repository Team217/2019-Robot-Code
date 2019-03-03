/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;
import frc.robot.subsystems.LiftingMechanism.Preset;
import org.team217.rev.*;

/**
 * Runs the arm in teleop control mode using {@code PID} to reach a preset.
 * 
 * @author ThunderChickens
 */
public class TeleopArmPreset extends Command {
    CANSparkMax rightArm1 = RobotMap.rightArm;

    boolean isPreset = false;
    boolean isBack = false;
    boolean setBack = true;

    Preset presetState = Preset.Manual;

    /**
     * Runs the arm in teleop control mode using {@code PID} to reach a preset.
     * 
     * @author ThunderChickens
     */
    public TeleopArmPreset() {
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
        }

        if (setBack && PresetState.getPOVStatus()) {
            isBack = rightArm1.getPosition() < -80;
            isBack = Robot.m_oi.xOper.get() ? !isBack : isBack;
            setBack = !Robot.m_oi.xOper.get();
        }
        else {
            setBack = !PresetState.getPOVStatus();
        }

        if (isPreset) {

            if (Robot.m_oi.oper.getPOV() == 180) {
                presetState = Preset.Low;
            }
            else if (Robot.m_oi.oper.getPOV() == 270) {
                presetState = Preset.Mid;
            }
            else if (Robot.m_oi.oper.getPOV() == 0) {
                presetState = Preset.High;
            }
            else if (Robot.m_oi.oper.getPOV() == 90) {
                presetState = Preset.RocketAdj;
            }

            Robot.kLiftingMechanism.armPreset(presetState, isBack);
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
