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
public class TeleopGroup extends CommandGroup {
    /**
     * Command Group that runs teleop.
     * 
     * @author ThunderChickens 217
     */
    public TeleopGroup() {
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
