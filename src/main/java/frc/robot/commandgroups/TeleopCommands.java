/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.*;
import frc.robot.commands.teleop.*;
import frc.robot.commandgroups.subgroups.*;

/**
 * Command Group that runs teleop.
 * 
 * @author ThunderChickens 217
 */
public class TeleopCommands extends CommandGroup {
    /**
     * Command Group that runs teleop.
     * 
     * @author ThunderChickens 217
     */
    public TeleopCommands() {
        requires(Robot.kDrivingSubsystem);
        requires(Robot.kIntakeSubsystem);
        requires(Robot.kArmSubsystem);
        requires(Robot.kWristSubsystem);
        requires(Robot.kElevatorSubsystem);
        requires(Robot.kTelescopeSubsystem);

        addParallel(new TeleopDrive());
        addParallel(new TeleopIntake());
        addParallel(new TeleopHatchPickup());
        addParallel(new TeleopArmWristCommands());
        addParallel(new TeleopElevator());
        addParallel(new TeleopElevatorPreset());
        addParallel(new TeleopClimb());
    }
}
