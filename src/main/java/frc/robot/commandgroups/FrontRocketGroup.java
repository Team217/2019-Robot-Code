package frc.robot.commandgroups;

import org.team217.pid.*;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.PresetState.Preset;
import frc.robot.commandgroups.subgroups.*;
import frc.robot.commands.auton.*;

public class FrontRocketGroup extends CommandGroup {
    public FrontRocketGroup() {
        // pick up the hatch panel
        addParallel(new AutonElevator(13000)); //change elev value higher than 8530
        addParallel(new HatchPickupGroup(0.5));
        addSequential(new AutonElevatorTarget(13000));

        // drive off platform
        addParallel(new PresetGroup(Preset.Mid, false));
        addSequential(new AutonDriveTimed(.25, 1)); //to pick up the vision target

        /*
        // place on mid rocket
        addParallel(new PresetHoldGroup(Preset.Mid, false, 2)); // Go to the mid preset (if fallen)
        addSequential(new AutonDriveVision(.25, true, 2)); //more than 2 seconds?, get to rocket

        addParallel(new PresetHoldGroup(Preset.Mid, false, .4)); // Go to the mid preset (if fallen)
        addSequential(new AutonDriveTimed(.1, .4)); //to place on rocket

        addParallel(new PresetHoldGroup(Preset.Mid, false, .75)); // Go to the mid preset (if fallen)
        addSequential(new AutonHatchPickup(true));


        // grab from HPS
        addParallel(new PresetHoldGroup(Preset.Mid, false, .5)); // Go to the mid preset (if fallen)
        addSequential(new AutonDriveTimed(-.25, .5)); //change to pid when ben makes a last encoder thing
        addSequential(new AutonTurn(-58, new PID(0.03, 0.001, 0), 1.25));

        addParallel(new PresetHoldGroup(Preset.Low, true, 1.5)); //move arm
        addSequential(new AutonDriveTimed(-.25, 1.5)); //gets vision into view

        addParallel(new PresetHoldGroup(Preset.Low, true, 2.0)); //hold arm
        addSequential(new AutonDriveVision(-.25, false, 2.0));

        addParallel(new PresetHoldGroup(Preset.Low, true, .75)); //hold arm
        addSequential(new AutonDriveTimed(-.1, .75)); //just before HPS

        addParallel(new PresetHoldGroup(Preset.Low, true, .75)); //hold arm
        addSequential(new AutonHatchPickup(false)); //grabs from HPS


        // place on low (high?) rocket
        addParallel(new PresetHoldGroup(Preset.Low, true, .75)); //hold arm
        addSequential(new AutonDriveTimed(.3, .75)); //drive away from HPS

        addParallel(new PresetHoldGroup(Preset.Low, false, 3.5)); //move arm to low front
        addSequential(new AutonTurn(-90, new PID(0.03, 0.001, 0), 1.25)); //cant go head on, sry bb

        addParallel(new PresetHoldGroup(Preset.Low, false, 3.5)); //move arm to low front
        addSequential(new AutonDriveTimed(.25, 1.5));

        addParallel(new PresetHoldGroup(Preset.Low, false, 1.5));
        addSequential(new AutonTurn(0, new PID(0.03, 0.001, 0), 1.25));

        addParallel(new PresetHoldGroup(Preset.Low, false, 1));
        addSequential(new AutonDriveTimed(.25, 1));

        addParallel(new PresetHoldGroup(Preset.Low, false, 1));
        addSequential(new AutonDriveVision(.25, true, 1.5));

        addParallel(new PresetHoldGroup(Preset.Low, false, .75));
        addSequential(new AutonDriveTimed(.1, .75));

        addParallel(new PresetHoldGroup(Preset.Low, false, .75));
        addSequential(new AutonHatchPickup(true));
        
        addParallel(new PresetHoldGroup(Preset.Low, false, .75));
        addSequential(new AutonDriveTimed(-.1, .75));
        */
    }
}