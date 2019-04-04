package frc.robot.commandgroups;

import org.team217.pid.*;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.PresetState.Preset;
import frc.robot.commandgroups.subgroups.*;
import frc.robot.commands.auton.*;

public class FrontRocketGroup extends CommandGroup {
    public FrontRocketGroup() {
        // pick up the hatch panel
        addParallel(new AutonElevator(13000));
        addParallel(new HatchPickupGroup(0.45));
        addSequential(new AutonElevatorTarget(13000), 1.5);
        addParallel(new AutonArm(10));
        addParallel(new AutonTelescope(20000));
        addSequential(new AutonTelescopeTarget(20000), 0.5);

        // drive off platform
        addParallel(new PresetGroup(Preset.Mid, false));
        addSequential(new AutonDriveTimed(0.5, 1.5));

        // drive to rocket
        addSequential(new AutonDriveVision(0.25, true, 1.7, 7.0)); //to pick up the vision target

        // release and back up
        addSequential(new AutonHatchPickup(true, 0.25));
        addSequential(new AutonDriveTimed(-0.25, 1.0));
        addSequential(new AutonTurn(0, new PID(0.015, 0.0001, 0), 1.0, 0.75));
    }
}