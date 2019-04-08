package frc.robot.commandgroups.subgroups;

import frc.robot.commands.auton.*;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class HatchPickupGroup extends CommandGroup {
    public HatchPickupGroup(double delay) {
        addSequential(new AutonHatchPickup(true, delay));
        addSequential(new AutonHatchPickup(false, 0.1));
    }

    public HatchPickupGroup() {
        this(0.5);
    }
}