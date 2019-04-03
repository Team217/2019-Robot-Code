package frc.robot.commandgroups.subgroups;

import frc.robot.commands.auton.*;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class HatchStartGroup extends CommandGroup {

    public HatchStartGroup() {
        addSequential(new AutonElevator(5000));
        addSequential(new HatchPickupGroup());
        addSequential(new AutonElevator(7000));
    }
}