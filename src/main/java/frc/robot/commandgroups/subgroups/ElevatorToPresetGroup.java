package frc.robot.commandgroups.subgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.PresetState.Preset;
import frc.robot.commands.auton.*;

public class ElevatorToPresetGroup extends CommandGroup {
    public ElevatorToPresetGroup(Preset presetState, boolean isBack) {
        addParallel(new AutonElevator(13000, false));
        addSequential(new AutonElevatorTarget(13000), 1.5);
        addSequential(new OutToPresetGroup(presetState, isBack));
    }
}