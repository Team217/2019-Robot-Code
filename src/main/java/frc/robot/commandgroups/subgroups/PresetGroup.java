package frc.robot.commandgroups.subgroups;

import frc.robot.PresetState.Preset;
import frc.robot.commands.auton.*;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class PresetGroup extends CommandGroup {

    public PresetGroup(Preset presetState, boolean isBack, double timeout) {
        addParallel(new AutonArmPreset(presetState, isBack, timeout));
        addParallel(new AutonElevatorPreset(presetState, isBack, timeout));
        addParallel(new AutonWristPreset(presetState, isBack, timeout));
        addParallel(new AutonTelescopePreset(presetState, timeout));
    }
}