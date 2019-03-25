package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;
import frc.robot.PresetState.Preset;

public class AutonElevatorPreset extends Command {
    boolean isBack = false;
    Preset preset = Preset.Manual;

    public AutonElevatorPreset(Preset preset, boolean isBack) {
        requires(Robot.kElevatorSubsystem);
        this.preset = preset;
        this.isBack = isBack;
    }

    @Override
    protected void initialize() {
        Robot.kElevatorSubsystem.lastPreset = Preset.Manual;
    }

    @Override
    protected void execute() {
        Robot.kElevatorSubsystem.preset(preset, isBack);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.kElevatorSubsystem.set(0);
    }

    @Override
    protected void interrupted() {
        Robot.kElevatorSubsystem.set(0);
    }
}