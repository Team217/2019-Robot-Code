package frc.robot.commandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.*;
import frc.robot.commands.teleop.*;

public class AutonClimbGroup extends CommandGroup {
    public AutonClimbGroup() {
        requires(Robot.kWristSubsystem);;
        requires(Robot.kIntakeSubsystem);
        
        // Teleop
        addParallel(new TeleopWrist());
        addParallel(new TeleopIntake());

        // Auton
        addParallel(new TeleopClimbDrive());
        addParallel(new TeleopClimbArm());

        // End
        addParallel(new TeleopClimbCancel());
    }
}
