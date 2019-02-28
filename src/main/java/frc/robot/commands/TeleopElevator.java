/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import org.team217.*;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;
import org.team217.pid.*;

public class TeleopElevator extends Command {
    boolean isAuto = false;
    APID elevAPID = RobotMap.elevAPID;

    public TeleopElevator() {
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
        double armSpeed = Range.deadband(Robot.m_oi.oper.getRawAxis(5), 0.08);
        if (armSpeed != 0) {
            isAuto = false;
        }
        else if (Robot.m_oi.oper.getPOV() == 0) {
            isAuto = true;
        }
        else if (Robot.m_oi.oper.getPOV() != -1) {
            isAuto = false;
        }
        System.out.println(Robot.m_oi.oper.getPOV());

        double leftAnalog = Range.deadband(Robot.m_oi.oper.getY(), 0.08);
        if (isAuto) {
            leftAnalog = elevAPID.getOutput(RobotMap.rightElevator.getEncoder(), -15300);
        }
        else {
            elevAPID.initialize();
        }
        
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
        Robot.kLiftingMechanism.elevator(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.kLiftingMechanism.elevator(0);
    }
}
