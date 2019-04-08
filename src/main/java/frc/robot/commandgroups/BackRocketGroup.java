package frc.robot.commandgroups;

import org.team217.pid.*;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.*;
import frc.robot.PresetState.Preset;
import frc.robot.commandgroups.subgroups.*;
import frc.robot.commands.auton.*;

public class BackRocketGroup extends CommandGroup {
    public BackRocketGroup(String side) {
        // pick up the hatch panel
        addParallel(new AutonElevator(13000));
        addParallel(new HatchPickupGroup(0.5));
        addSequential(new AutonElevatorTarget(13000), 1.5);
        addParallel(new OutToPresetGroup(Preset.Mid, false));

        if (side.equals(Robot.right)) {
            // drive to rocket
            addParallel(new AutonAngle(206, 1.0));
            addSequential(new AutonDriveTimed(-0.4, 1.0));
            addSequential(new AutonDriveTimed(-0.65, 2.5, true));
            addSequential(new AutonDriveTimed(-0.4, 0.75, true));
            addSequential(new AutonTurn(162, new PID(0.015, 0.0001, 0), 1.0, 0.75));
            addSequential(new AutonDriveTimed(-0.2, 1));
            addSequential(new AutonDriveVision(0.2, true, 1.7, 7.0)); //to pick up the vision target
            addSequential(new AutonDriveTimed(0.2, 0.25));
    
            // release and back up
            addSequential(new AutonHatchPickup(true, 0.25));
            addSequential(new AutonDriveTimed(-0.2, 1.0));
            addParallel(new PresetGroup(Preset.Low, false));
            addSequential(new AutonTurn(185, new PID(0.015, 0.0001, 0), 1.0, 0.75));
    
            // pick up another hatch panel
            addParallel(new AutonAngle(170, 1.25));
            addSequential(new AutonDriveTimed(0.65, 2.25, true));
            addSequential(new TeleopCommands());
        }
        else {
            // drive to rocket
            addParallel(new AutonAngle(-206, 1.0));
            addSequential(new AutonDriveTimed(-0.4, 1.0));
            addSequential(new AutonDriveTimed(-0.65, 2.5, true));
            addSequential(new AutonDriveTimed(-0.4, 0.75, true));
            addSequential(new AutonTurn(-162, new PID(0.015, 0.0001, 0), 1.0, 0.75));
            addSequential(new AutonDriveTimed(-0.2, 1));
            addSequential(new AutonDriveVision(0.2, true, 1.7, 7.0)); //to pick up the vision target
            addSequential(new AutonDriveTimed(0.2, 0.25));
    
            // release and back up
            addSequential(new AutonHatchPickup(true, 0.25));
            addSequential(new AutonDriveTimed(-0.2, 1.0));
            addParallel(new PresetGroup(Preset.Low, false));
            addSequential(new AutonTurn(-185, new PID(0.015, 0.0001, 0), 1.0, 0.75));
    
            // pick up another hatch panel
            addParallel(new AutonAngle(-170, 1.25));
            addSequential(new AutonDriveTimed(0.65, 2.25, true));
            addSequential(new TeleopCommands());
        }
    }
}