package frc.robot.commandgroups.subgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.PresetState.Preset;
import frc.robot.commands.auton.*;

public class OutToPresetGroup extends CommandGroup {
    private OutToPresetGroup(int i) {
        addParallel(new AutonArm(10));
        addParallel(new AutonTelescope(20000));
    }

    private OutToPresetGroup(boolean e) {
        addParallel(new AutonArmTarget(10));
        addParallel(new AutonTelescopeTarget(20000));
    }

    public OutToPresetGroup(Preset presetState, boolean isBack) {
        addParallel(new OutToPresetGroup(1));
        addSequential(new OutToPresetGroup(true), 0.5);
        addSequential(new PresetGroup(presetState, isBack));
    }
}