package frc.robot.commandgroups.subgroups;

import frc.robot.PresetState.Preset;
import frc.robot.commands.auton.*;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class PresetHoldGroup extends CommandGroup {

    public PresetHoldGroup(Preset presetState, boolean isBack, double timeout) {
        addSequential(new PresetGroup(presetState, isBack, timeout));
        addParallel(new AutonArmHold());
        addParallel(new AutonElevatorHold());
        addParallel(new AutonTelescopeHold());
    }
}