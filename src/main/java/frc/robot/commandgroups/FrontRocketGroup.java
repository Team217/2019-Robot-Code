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
        addParallel(new HatchPickupGroup(0.5));
        addSequential(new AutonElevatorTarget(13000), 1.5);
        addParallel(new OutToPresetGroup(Preset.Mid, false));

        // drive to rocket
        addSequential(new AutonDriveTimed(0.5, 2.0));
        addSequential(new AutonDriveVision(0.25, true, 1.7, 7.0));

        // release and back up
        addSequential(new AutonHatchPickup(true, 0.25));
        addSequential(new AutonAngle());
        addParallel(new AutonAngle(-7, 0.35));
        addSequential(new AutonDriveTimed(-0.35, 0.5, true));

        // pick up another hatch panel
        addParallel(new PresetGroup(Preset.Low, true));
        addSequential(new AutonDriveTimed(-0.7, 1.5, true));
        addSequential(new AutonDriveVision(-0.25, false, 1.8, 7.0));
        addSequential(new HatchPickupGroup());
        addSequential(new AutonDriveTimed(0.4, 0.5));
        addSequential(new TeleopCommands());
    }
}