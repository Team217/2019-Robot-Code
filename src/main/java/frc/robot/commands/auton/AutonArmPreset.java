package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;
import frc.robot.PresetState.Preset;

public class AutonArmPreset extends Command {
    boolean isBack = false;
    Preset presetState = Preset.Manual;

    public AutonArmPreset(Preset preset, boolean isBack) {
        requires(Robot.kArmSubsystem);
        this.isBack = isBack;
        presetState = preset;
    }

    @Override
    protected void initialize() {
        Robot.kArmSubsystem.lastPreset = Preset.Manual;
    }

    @Override
    protected void execute() {
        Robot.kArmSubsystem.preset(presetState, isBack);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.kArmSubsystem.set(0);
    }

    @Override
    protected void interrupted() {
        Robot.kArmSubsystem.set(0);
    }
}