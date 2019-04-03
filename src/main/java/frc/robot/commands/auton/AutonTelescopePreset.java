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
     * @param timeout
     *        The time before automatically ending the command, in seconds
     * 
     * @author ThunderChickens 217
     */
    public AutonTelescopePreset(Preset presetState, double timeout) {
        requires(Robot.kTelescopeSubsystem);
        this.presetState = presetState;
        setTimeout(timeout);
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
        return isTimedOut() || Robot.kTelescopeSubsystem.atPreset;
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