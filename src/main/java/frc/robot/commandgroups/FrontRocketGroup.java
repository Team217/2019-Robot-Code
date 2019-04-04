package frc.robot.commandgroups;

import org.team217.pid.*;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.PresetState.Preset;
import frc.robot.commandgroups.subgroups.*;
import frc.robot.commands.auton.*;

public class FrontRocketGroup extends CommandGroup {
    public FrontRocketGroup() {
        // Pick up the hatch panel
        addSequential(new HatchStartGroup());

        // Drive off platform
        addSequential(new AutonDriveTimed(0.5, 2.0));

        // Face the rocket somewhat head-on
        addSequential(new AutonTurn(90, new PID(0.03, 0.001, 0), 0.25, 1.5));
        addParallel(new PresetGroup(Preset.Mid, false)); // Go to the mid preset
        addSequential(new AutonDriveTimed(0.5, 0.75));
        addSequential(new AutonTurn(30, new PID(0.03, 0.001, 0), 0.25, 1.25));
    }
}