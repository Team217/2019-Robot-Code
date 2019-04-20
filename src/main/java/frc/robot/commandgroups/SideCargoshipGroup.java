/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commandgroups;

import org.team217.pid.*;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.*;
import frc.robot.PresetState.Preset;
import frc.robot.commandgroups.subgroups.*;
import frc.robot.commands.auton.*;

public class SideCargoshipGroup extends CommandGroup {
    public SideCargoshipGroup(String side) {
        // pick up the hatch panel
        addParallel(new AutonElevator(13000));
        addParallel(new HatchPickupGroup(0.5));
        addSequential(new AutonElevatorTarget(13000), 1.5);
        addParallel(new OutToPresetGroup(Preset.Low, false));

        if (side.equals(Robot.right)) {
            // To Cargo Ship
            addParallel(new AutonAngle(10, 1));
            addSequential(new AutonDriveTimed(0.45, 4.25, true));
            addSequential(new AutonTurn(-95, new PID(0.01, 0.0001, 0), 2, 0.75, 1.25));
            addSequential(new AutonDriveVision(0.25, true, 2, 4.0));
            addSequential(new AutonDriveTimed(.25, 0.5));
            addSequential(new AutonHatchPickup(true, 0.25));
    
            // Move to next Cargo Location
            addSequential(new AutonDriveTimed(-0.3, 1.0));
            addParallel(new AutonHatchPickup(false, 0.1));
            addSequential(new AutonTurn(-78, new PID(0.015, 0.0001, 0), 1.0, 0.75));
            addSequential(new TeleopGroup());
        }
        else {
            // To Cargo Ship
            addParallel(new AutonAngle(-10, 1));
            addSequential(new AutonDriveTimed(0.45, 4.25, true));
            addSequential(new AutonTurn(95, new PID(0.01, 0.0001, 0), 2, 0.75, 1.25));
            addSequential(new AutonDriveVision(0.25, true, 2, 4.0));
            addSequential(new AutonDriveTimed(.25, 0.5));
            addSequential(new AutonHatchPickup(true, 0.25));
    
            // Move to next Cargo Location
            addSequential(new AutonDriveTimed(-0.3, 1.0));
            addParallel(new AutonHatchPickup(false, 0.1));
            addSequential(new AutonTurn(78, new PID(0.015, 0.0001, 0), 1.0, 0.75));
            addSequential(new TeleopGroup());
        }
    }
}
