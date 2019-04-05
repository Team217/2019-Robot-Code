package frc.robot.commandgroups;

import org.team217.pid.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

import frc.robot.PresetState.Preset;
import frc.robot.commandgroups.subgroups.*;
import frc.robot.commands.auton.*;

public class BackRocketGroup extends CommandGroup {
    public BackRocketGroup() {
        // pick up the hatch panel
        addParallel(new AutonElevator(13000));
        addParallel(new HatchPickupGroup(0.5));
        addSequential(new AutonElevatorTarget(13000), 1.5);
        addParallel(new OutToPresetGroup(Preset.Mid, false));

        // drive to rocket
        addSequential(new AutonDriveTimed(-0.45, 5.25, true));
        addSequential(new AutonTurn(170, new PID(0.015, 0.0001, 0), 1.0, 0.75));
        addSequential(new AutonDriveVision(0.25, true, 1.7, 7.0)); //to pick up the vision target

        // release and back up
        addSequential(new AutonHatchPickup(true, 0.25));
        addSequential(new AutonDriveTimed(-0.25, 1.0));
        addParallel(new PresetGroup(Preset.Low, false));
        addSequential(new AutonTurn(180, new PID(0.015, 0.0001, 0), 1.0, 0.75));

        // pick up another hatch panel
        addSequential(new AutonDriveTimed(0.65, 2.25));
        addSequential(new TeleopCommands());
    }
}