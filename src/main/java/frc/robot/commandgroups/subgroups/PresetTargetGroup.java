package frc.robot.commandgroups.subgroups;

import frc.robot.commands.auton.*;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class PresetTargetGroup extends CommandGroup {

    public PresetTargetGroup() {
        addParallel(new AutonArmTarget());
        addParallel(new AutonElevatorTarget());
        addParallel(new AutonTelescopeTarget());
        addParallel(new AutonWristTarget());
    }
}