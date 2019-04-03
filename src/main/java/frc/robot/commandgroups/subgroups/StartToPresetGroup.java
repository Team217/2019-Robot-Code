package frc.robot.commandgroups.subgroups;

import frc.robot.PresetState.Preset;
import frc.robot.commands.auton.*;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class StartToPresetGroup extends CommandGroup {

    public StartToPresetGroup(Preset presetState, boolean isBack, double delay) {
        addSequential(new AutonElevator(7000));
        addSequential(new AutonDelay(delay));
        addSequential(new PresetGroup(presetState, isBack));
    }

    public StartToPresetGroup(Preset presetState, boolean isBack) {
        this(presetState, isBack, 0);
    }
}