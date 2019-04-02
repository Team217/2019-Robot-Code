package frc.robot.commandgroups.subgroups;

import frc.robot.commands.auton.*;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class HatchPickupGroup extends CommandGroup {
    public HatchPickupGroup(double delay) {
        addSequential(new AutonHatchPickup(true));
        addSequential(new AutonDelay(delay));
        addSequential(new AutonHatchPickup(false));
    }

    public HatchPickupGroup() {
        this(1);
    }
}