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

public class DoubleSideCargoshipGroup extends CommandGroup {
    public DoubleSideCargoshipGroup(String side, String level) {
        // pick up the hatch panel
        addParallel(new AutonElevator(13000));
        addSequential(new HatchPickupGroup(0.5));
        addParallel(new ElevatorToPresetGroup(Preset.Low, false));

        try {
            if (level.equals(Robot.level2)) {
                addSequential(new AutonDriveTimed(0.45, 0.5));
            }
        }
        catch (Exception e) {}

        if (side.equals(Robot.right)) {
            // To Cargo Ship
            addParallel(new AutonAngle(12, .75));
            addSequential(new AutonDriveTimed(0.45, 0.5, true));
            addSequential(new AutonDriveTimed(1.0, 1.725, true));
            addSequential(new AutonDriveTimed(0.4, 0.25, true));
            addSequential(new AutonTurn(-92, new PID(0.012, 0.0001, 0).setMinMax(0.1), 2, 1.00, 1.2));
            addSequential(new AutonDriveVision(0.22, true, 1.95, 4.0));
            addSequential(new AutonDriveTimed(.25, 0.3));
            addSequential(new AutonHatchPickup(true, 0.25));
    
            // To Human Player
            addSequential(new AutonDriveTimed(-0.45, 0.5));
            addSequential(new AutonTurn(-194, new PID(0.008, 0.0001, 0).setMinMax(0.1), 1.2, 1.00, 1));
            addParallel(new AutonAngle(-180, 2));
            addSequential(new AutonDriveTimed(1.0, 2.1, true));
            addSequential(new AutonDriveTimed(0.45, 0.25, true));
            addSequential(new AutonDriveVision(0.25, true, 2, 5.0));
            addSequential(new AutonDriveTimed(.25, 0.3));
            addSequential(new AutonHatchPickup(false, 0.25));

            // To Cargoship
            addSequential(new AutonAngle());
            addParallel(new AutonAngle(-192, 0.5));
            addSequential(new AutonDriveTimed(-0.35, 0.5, true));
            addSequential(new AutonDriveTimed(-1.0, 2.4, true));
            addSequential(new AutonDriveTimed(-0.4, 0.25, true));
            addSequential(new AutonTurn(-90, new PID(0.01, 0.0001, 0).setMinMax(0.1), 1.25, 1.00, 1.15));
            addSequential(new AutonDriveVision(0.25, true, 2.1, 4.0));
            addSequential(new AutonDriveTimed(.25, 0.3));

            addSequential(new TeleopGroup());
        }
        else {
            // To Cargo Ship
            addParallel(new AutonAngle(-12, 0.75));
            addSequential(new AutonDriveTimed(0.45, 0.5, true));
            addSequential(new AutonDriveTimed(1.0, 1.6, true));
            addSequential(new AutonDriveTimed(0.4, 0.25, true));
            addSequential(new AutonTurn(92, new PID(0.008, 0.0001, 0).setMinMax(0.1), 2, 1.00, 1));
            addSequential(new AutonDriveVision(0.25, true, 2, 4.0));
            addSequential(new AutonDriveTimed(.25, 0.3));
            addSequential(new AutonHatchPickup(true, 0.25));
    
            // To Human Player
            addSequential(new AutonDriveTimed(-0.45, 0.5));
            addSequential(new AutonTurn(196, new PID(0.008, 0.0001, 0).setMinMax(0.1), 1.5, 1.00, 1));
            addParallel(new AutonAngle(180, 2));
            addSequential(new AutonDriveTimed(1.0, 2, true));
            addSequential(new AutonDriveTimed(0.45, 0.25, true));
            addSequential(new AutonDriveVision(0.25, true, 2, 5.0));
            addSequential(new AutonDriveTimed(.25, 0.3));
            addSequential(new AutonHatchPickup(false, 0.25));

            // To Cargoship
            addSequential(new AutonAngle());
            addParallel(new AutonAngle(192, 0.5));
            addSequential(new AutonDriveTimed(-0.35, 0.5, true));
            addSequential(new AutonDriveTimed(-1.0, 2.4, true));
            addSequential(new AutonDriveTimed(-0.4, 0.25, true));
            addSequential(new AutonTurn(92, new PID(0.008, 0.0001, 0).setMinMax(0.1), 1.5, 1.0, 1.15));
            addSequential(new AutonDriveVision(0.25, true, 2.1, 4.0));
            addSequential(new AutonDriveTimed(.25, 0.3));

            addSequential(new TeleopGroup());
        }
    }

    public DoubleSideCargoshipGroup(String side) {
        this(side, Robot.level1);
    }

    public DoubleSideCargoshipGroup() {
        this(Robot.right);
    }
}