package frc.robot.commandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commandgroups.subgroups.*;
import frc.robot.commands.auton.*;

public class ManualHatchGroup extends CommandGroup {
    public ManualHatchGroup() {
        // pick up the hatch panel
        addParallel(new AutonElevator(13000));
        addParallel(new HatchPickupGroup(0.5));
        addSequential(new AutonElevatorTarget(13000), 1.5);

        // run in teleop mode
        addSequential(new TeleopGroup());
    }
}