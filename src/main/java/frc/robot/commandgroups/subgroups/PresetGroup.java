package frc.robot.commandgroups.subgroups;

import frc.robot.PresetState.Preset;
import frc.robot.commands.auton.*;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class PresetGroup extends CommandGroup {

    public PresetGroup(Preset presetState, boolean isBack) {
        addParallel(new AutonArmPreset(presetState, isBack));
        addParallel(new AutonElevatorPreset(presetState, isBack));
        addParallel(new AutonWristPreset(presetState, isBack));
        addParallel(new AutonTelescopePreset(presetState));
    }
}