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
    public SideCargoshipGroup(String side, String level) {
        // pick up the hatch panel
        addParallel(new AutonElevator(13000));
        addSequential(new HatchPickupGroup(0.5));
        addParallel(new ElevatorToPresetGroup(Preset.Low, false));

        if (level.equals(Robot.level2)) {
            addSequential(new AutonDriveTimed(0.45, 0.5));
        }

        if (side.equals(Robot.right)) {
            // To Cargo Ship
            addParallel(new AutonAngle(10, 0.75));
            addSequential(new AutonDriveTimed(0.45, 0.75, true));
            addSequential(new AutonDriveTimed(0.8, 1.75, true));
            addSequential(new AutonDriveTimed(0.4, 0.25, true));
            addSequential(new AutonTurn(-95, new PID(0.01, 0.0001, 0).setMinMax(0.1), 2, 0.65, 1.25));
            addSequential(new AutonDriveVision(0.25, true, 2.1, 4.0));
            addSequential(new AutonDriveTimed(.25, 0.3));
            addSequential(new AutonHatchPickup(true, 0.25));
    
            // Move to next Cargo Location
            addSequential(new AutonDriveTimed(-0.3, 1.0));
            addParallel(new AutonHatchPickup(false, 0.1));
            addSequential(new AutonTurn(-78, new PID(0.015, 0.0001, 0), 1.0, 0.75));
            addSequential(new TeleopGroup());
        }
        else {
            // To Cargo Ship
            addParallel(new AutonAngle(-10, 0.75));
            addSequential(new AutonDriveTimed(0.45, 0.75, true));
            addSequential(new AutonDriveTimed(0.8, 1.75, true));
            addSequential(new AutonDriveTimed(0.4, 0.25, true));
            addSequential(new AutonTurn(95, new PID(0.01, 0.0001, 0).setMinMax(0.1), 2, 0.65, 1.25));
            addSequential(new AutonDriveVision(0.25, true, 2.1, 4.0));
            addSequential(new AutonDriveTimed(.25, 0.3));
            addSequential(new AutonHatchPickup(true, 0.25));
    
            // Move to next Cargo Location
            addSequential(new AutonDriveTimed(-0.3, 1.0));
            addParallel(new AutonHatchPickup(false, 0.1));
            addSequential(new AutonTurn(78, new PID(0.015, 0.0001, 0), 1.0, 0.75));
            addSequential(new TeleopGroup());
        }
    }

    public SideCargoshipGroup(String side) {
        this(side, Robot.level1);
    }

    public SideCargoshipGroup() {
        this(Robot.right);
    }
}