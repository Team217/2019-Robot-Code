/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import org.team217.*;
import org.team217.pid.*;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

public class teleopElevator extends Command {
    boolean isLastPosition = false;
    PID elevatorHoldPID = RobotMap.elevatorHoldPID;

    public teleopElevator() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        super("teleopElevator");
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        double leftAnalog = Range.deadband(Robot.m_oi.oper.getY(), 0.08);
        /*
        isLastPosition = leftAnalog != 0;

        if (isLastPosition) {
            Robot.kLiftingMechanism.lastElevatorPos = RobotMap.rightElevator.getEncoder();
        }
        else {
            leftAnalog = elevatorHoldPID.getOutput(RobotMap.rightElevator.getEncoder(), Robot.kLiftingMechanism.lastElevatorPos);
        }
        */
        Robot.kLiftingMechanism.elevator(leftAnalog);
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
