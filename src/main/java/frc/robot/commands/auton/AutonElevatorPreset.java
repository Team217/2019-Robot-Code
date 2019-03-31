package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;
import frc.robot.PresetState.Preset;

/**
 * Runs the elevator in auton control mode using {@code APID} to reach a preset.
 * 
 * @author ThunderChickens 217
 */
public class AutonElevatorPreset extends Command {
    boolean isBack = false;
    Preset presetState = Preset.Manual;

    /**
     * Runs the elevator in auton control mode using {@code APID} to reach a preset.
     * 
     * @param presetState
     *        The {@code Preset} state
     * @param isBack
     *        {@code true} if the preset moves the arm to the back region of the bot
     * 
     * @author ThunderChickens 217
     */
    public AutonElevatorPreset(Preset presetState, boolean isBack) {
        requires(Robot.kElevatorSubsystem);
        this.presetState = presetState;
        this.isBack = isBack;
    }

    @Override
    protected void initialize() {
        Robot.kElevatorSubsystem.lastPreset = Preset.Manual;
    }

    @Override
    protected void execute() {
        Robot.kElevatorSubsystem.preset(presetState, isBack);
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