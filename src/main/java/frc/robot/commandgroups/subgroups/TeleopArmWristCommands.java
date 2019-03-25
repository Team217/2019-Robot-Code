/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commandgroups.subgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.*;
import frc.robot.commands.teleop.*;

/**
 * Command Group that runs the arm and wrist in teleop.
 * 
 * @author ThunderChickens 217
 */
public class TeleopArmWristCommands extends CommandGroup {
    /**
     * Command Group that runs the arm and wrist in teleop.
     * 
     * @author ThunderChickens 217
     */
    public TeleopArmWristCommands() {
        requires(Robot.kArmSubsystem);
        requires(Robot.kWristSubsystem);
        requires(Robot.kTelescopeSubsystem);

        addParallel(new TeleopArm());
        addParallel(new TeleopArmPreset());
        addParallel(new TeleopWrist());
        //addParallel(new TeleopWristGyro());
        addParallel(new TeleopWristPreset());
        addParallel(new TeleopTelescope());
        addParallel(new TeleopTelescopePreset());
    }

}
