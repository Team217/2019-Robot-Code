package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;
import frc.robot.PresetState.Preset;

public class AutonWristPreset extends Command {
    boolean isBack = false;
    Preset presetState = Preset.Manual;

    public AutonWristPreset(Preset preset, boolean isBack) {
        requires(Robot.kWristSubsystem);
        this.isBack = isBack;
        presetState = preset;
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