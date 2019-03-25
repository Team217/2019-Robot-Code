package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;
import frc.robot.PresetState.Preset;

public class AutonArmPreset extends Command {
    boolean isBack = false;
    Preset preset = Preset.Manual;

    public AutonArmPreset(Preset preset, boolean isBack) {
        requires(Robot.kArmSubsystem);
        this.preset = preset;
        this.isBack = isBack;
    }

    @Override
    protected void initialize() {
        Robot.kArmSubsystem.lastPreset = Preset.Manual;
    }

    @Override
    protected void execute() {
        Robot.kArmSubsystem.preset(preset, isBack);
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