package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;
import frc.robot.PresetState.Preset;

/**
 * Runs the telescope in auton control mode using {@code APID} to reach a preset.
 * 
 * @author ThunderChickens 217
 */
public class AutonTelescopePreset extends Command {
    Preset presetState = Preset.Manual;

    /**
     * Runs the telescope in auton control mode using {@code APID} to reach a preset.
     * 
     * @param presetState
     *        The {@code Preset} state
     * 
     * @author ThunderChickens 217
     */
    public AutonTelescopePreset(Preset presetState) {
        requires(Robot.kTelescopeSubsystem);
        this.presetState = presetState;
    }

    @Override
    protected void initialize() {
        Robot.kTelescopeSubsystem.lastPreset = Preset.Manual;
        Robot.kTelescopeSubsystem.atPreset = false;
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
        Robot.kTelescopeSubsystem.reset();
    }

    @Override
    protected void interrupted() {
        Robot.kTelescopeSubsystem.reset();
    }
}