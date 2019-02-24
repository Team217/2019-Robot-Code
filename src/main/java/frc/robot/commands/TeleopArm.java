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

public class TeleopArm extends Command {
    boolean isRunning = true;
    boolean isLastPosition = false;
    PID armHoldPID = RobotMap.armHoldPID;

    public TeleopArm() {
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
        if (Robot.m_oi.rightTriggerOper.get() || Robot.m_oi.leftTriggerOper.get()) {
            isRunning = true;
        }
        else if (Robot.m_oi.touchPadOper.get()) {
            isRunning = false;
        }

        if (isRunning) {
            //moving arm w/wrist independence
            double rightAnalog = Range.deadband(Robot.m_oi.oper.getRawAxis(5), 0.08);
            /*
            isLastPosition = rightAnalog != 0;

            if (isLastPosition) {
                Robot.kLiftingMechanism.lastArmPos = RobotMap.rightArm.getPosition();
            }
            else {
                rightAnalog = armHoldPID.getOutput(RobotMap.rightArm.getPosition(), Robot.kLiftingMechanism.lastArmPos);
            }
            */
            Robot.kLiftingMechanism.arm(rightAnalog);
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
