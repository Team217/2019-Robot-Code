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
        double speed = 0;

        if(!RobotMap.ballLimit.get()){
            speed = .45;
        }
        
        if (Robot.m_oi.xOper.get()) {
            speed = 0.75;
        }
        else if (Robot.m_oi.oper.getPOV() == 180) {
            speed = -0.35;
        }
       // else if (Robot.m_oi.rightBumperOper.get()) {
        //    speed = -0.5;
       // }
        else if (Robot.m_oi.oper.getPOV() == 0) {
            speed = -1.0;
        }
    

        Robot.kIntakeSubsystem.intake(speed);
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
