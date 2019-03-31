package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;
import frc.robot.PresetState.Preset;

/**
 * Runs the wrist in auton control mode using {@code APID} to reach a preset.
 * 
 * @author ThunderChickens 217
 */
public class AutonWristPreset extends Command {
    boolean isBack = false;
    Preset presetState = Preset.Manual;

    /**
     * Runs the wrist in auton control mode using {@code APID} to reach a preset.
     * 
     * @param presetState
     *        The {@code Preset} state
     * @param isBack
     *        {@code true} if the preset moves the arm to the back region of the bot
     * 
     * @author ThunderChickens 217
     */
    public AutonWristPreset(Preset presetState, boolean isBack) {
        requires(Robot.kWristSubsystem);
        this.isBack = isBack;
        this.presetState = presetState;
    }

    @Override
    protected void initialize() {
        Robot.kWristSubsystem.lastPreset = Preset.Manual;
    }

    @Override
    protected void execute() {
        Robot.kWristSubsystem.preset(presetState, isBack);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.kWristSubsystem.set(0);
    }

    @Override
    protected void interrupted() {
        Robot.kWristSubsystem.set(0);
    }
}