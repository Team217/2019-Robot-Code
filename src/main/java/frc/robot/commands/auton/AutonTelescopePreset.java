package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;
import frc.robot.PresetState.Preset;

public class AutonTelescopePreset extends Command {
    Preset presetState = Preset.Manual;

    public AutonTelescopePreset(Preset presetState) {
        requires(Robot.kTelescopeSubsystem);
        this.presetState = presetState;
    }

    @Override
    protected void initialize() {
        Robot.kTelescopeSubsystem.lastPreset = Preset.Manual;
    }

    @Override
    protected void execute() {
        Robot.kTelescopeSubsystem.preset(presetState);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.kTelescopeSubsystem.set(0);
    }

    @Override
    protected void interrupted() {
        Robot.kTelescopeSubsystem.set(0);
    }
}