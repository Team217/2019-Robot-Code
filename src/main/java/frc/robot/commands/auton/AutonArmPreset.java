package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;
import frc.robot.PresetState.Preset;

/**
 * Runs the arm in auton control mode using {@code APID} to reach a preset.
 * 
 * @author ThunderChickens 217
 */
public class AutonArmPreset extends Command {
    boolean isBack = false;
    Preset presetState = Preset.Manual;

    /**
     * Runs the arm in auton control mode using {@code APID} to reach a preset.
     * 
     * @param presetState
     *        The {@code Preset} state
     * @param isBack
     *        {@code true} if the preset moves the arm to the back region of the bot
     * @param timeout
     *        The time before automatically ending the command, in seconds
     * 
     * @author ThunderChickens 217
     */
    public AutonArmPreset(Preset presetState, boolean isBack, double timeout) {
        requires(Robot.kArmSubsystem);
        this.presetState = presetState;
        this.isBack = isBack;
        setTimeout(timeout);
    }

    @Override
    protected void initialize() {
        Robot.kArmSubsystem.lastPreset = Preset.Manual;
        Robot.kArmSubsystem.atPreset = false;
    }

    @Override
    protected void execute() {
        Robot.kArmSubsystem.preset(presetState, isBack);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut() || Robot.kArmSubsystem.atPreset;
    }

    @Override
    protected void end() {
        Robot.kArmSubsystem.reset();
    }

    @Override
    protected void interrupted() {
        Robot.kArmSubsystem.reset();
    }
}