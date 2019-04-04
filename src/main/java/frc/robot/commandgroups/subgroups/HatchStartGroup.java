package frc.robot.commandgroups.subgroups;

import frc.robot.commands.auton.*;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class HatchStartGroup extends CommandGroup {

    public HatchStartGroup() {
        addParallel(new AutonElevator(5000));
        addSequential(new AutonElevatorTarget(5000), 1.5);

        addSequential(new HatchPickupGroup());
        
        addParallel(new AutonElevator(7000));
        addSequential(new AutonElevatorTarget(7000), 1.5);
    }
}