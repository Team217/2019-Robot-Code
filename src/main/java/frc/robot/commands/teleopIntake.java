/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

public class teleopIntake extends Command {
  public teleopIntake() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(Robot.m_oi.xOper.get()) {
      Robot.kIntakeSubsystem.intake(.5);
    }
    else if(Robot.m_oi.oper.getPOV() == 180) {
      Robot.kIntakeSubsystem.intake(-.35);
    }
    else if(Robot.m_oi.rightBumperOper.get()) {
      Robot.kIntakeSubsystem.intake(-.5);
    }
    else if(Robot.m_oi.oper.getPOV() == 0) {
      Robot.kIntakeSubsystem.intake(-.75);
    }
    else {
      Robot.kIntakeSubsystem.intake(0.0);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
