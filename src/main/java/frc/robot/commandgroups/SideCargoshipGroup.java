/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commandgroups;

import org.team217.pid.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

import frc.robot.PresetState.Preset;
import frc.robot.commandgroups.subgroups.*;
import frc.robot.commands.auton.*;

public class SideCargoship extends CommandGroup {
    public SideCargoship() {
        // pick up the hatch panel
        addParallel(new AutonElevator(13000));
        addParallel(new HatchPickupGroup(0.45));
        addSequential(new AutonElevatorTarget(13000), 1.5);
        addParallel(new AutonArm(10));
        addParallel(new AutonTelescope(20000));
        addSequential(new AutonTelescopeTarget(20000), 0.5);

        // To Cargo Ship
        addParallel(new PresetGroup(Preset.Low, false));
        addSequential(new AutonDriveTimed(0.5, 3));
        addSequential(new AutonTurn(-95, new PID(0.015, 0.0001, 0), 1.0, 0.75));
        addSequential(new AutonDriveVision(0.25, true, 1.7, 3.0));
        addSequential(new AutonHatchPickup(true, 0.25));

        // Move to next Cargo Location
        addSequential(new AutonDriveTimed(-0.25, 0.5));
        addSequential(new AutonTurn(0, new PID(0.015, 0.0001, 0), 1.0, 0.75));
        addSequential(new AutonDriveTimed(0.25, 0.5));
        addSequential(new AutonTurn(-95, new PID(0.015, 0.0001, 0), 1.0, 0.75));
        addSequential(new AutonDriveVision(0.25, true, 1.7, 3.0));
    }
}
