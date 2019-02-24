/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commandgroups.subgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.*;
import frc.robot.commands.*;

public class TeleopArmWristCommands extends CommandGroup {
  /**
   * Add your docs here.
   */
  public TeleopArmWristCommands() {
    requires(Robot.kLiftingMechanism);

    addParallel(new TeleopArmAndWrist()); //keep wrist parallel to floor
    
    addParallel(new TeleopMoveWrist());
    addParallel(new TeleopArm());
  }

  }

