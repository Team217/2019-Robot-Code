/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commandgroups;

import frc.robot.PresetState.Preset;
import frc.robot.commandgroups.subgroups.*;
import frc.robot.commands.auton.*;

import org.team217.pid.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class FrontCargoshipGroup extends CommandGroup {
    public FrontCargoshipGroup() {
        // pick up the hatch panel
        addParallel(new AutonElevator(13000)); //change elev value higher than 8530
        addParallel(new HatchPickupGroup(0.5));
        addSequential(new AutonElevatorTarget(13000), 1.5);
        addParallel(new OutToPresetGroup(Preset.Low, false));

        // places Hatch on cargoship
        addSequential(new AutonDriveTimed(.4, 1.5));
        addSequential(new AutonDriveVision(.25, true, 2.0, 5));
        addSequential(new AutonDriveTimed(.25, 0.5));
        addSequential(new AutonHatchPickup(true, .25));

        // lines up to the other cargo location
        addSequential(new AutonDriveTimed(-.35, 1.5));
        addParallel(new AutonHatchPickup(false, 0.1));
        addSequential(new AutonTurn(12, new PID(0.015, 0.0001, 0), 1.0, 0.75));
        addSequential(new TeleopCommands());
    }
}
