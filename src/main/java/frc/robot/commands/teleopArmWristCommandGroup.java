/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.*;

public class teleopArmWristCommandGroup extends CommandGroup {
    /**
     * Add your docs here.
     */
    public teleopArmWristCommandGroup() {
        requires(Robot.kLiftingMechanism);

        addParallel(new teleopArmAndWrist()); // keep wrist parallel to floor

        addParallel(new teleopMoveWrist());
        addParallel(new teleopArm());
    }

}
