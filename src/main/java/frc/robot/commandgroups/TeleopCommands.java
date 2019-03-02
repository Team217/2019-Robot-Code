/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.*;
import frc.robot.commands.*;
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
        requires(Robot.kLiftingMechanism);

        addParallel(new TeleopDrive());
        //addParallel(new TeleopMoveWrist());
        addParallel(new TeleopIntake());
        addParallel(new TeleopHatchPickup());
        //addParallel(new TeleopArm());
        //  addParallel(new TeleopArmAndWrist()); 
        addParallel(new TeleopArmWristCommands());
        addParallel(new TeleopElevator());
        addParallel(new TeleopClimb());

        // Add Commands here:
        // e.g. addSequential(new Command1());
        // addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        // addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}