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

/**
 * Runs the elevator in teleop control mode.
 * 
 * @author ThunderChickens 217
 */
public class TeleopElevator extends Command {
    boolean isAuto = false;
    boolean canAuto = false;
    APID elevAPID = RobotMap.elevAPID;
    double target = 0;

    /**
     * Runs the elevator in teleop control mode.
     * 
     * @author ThunderChickens 217
     */
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
        if (Robot.m_oi.rightTriggerOper.get() || Robot.m_oi.leftTriggerOper.get()) {
            canAuto = false;
        }
        else if (Robot.m_oi.touchPadOper.get()) {
            canAuto = true;
        }

        double leftAnalog = Range.deadband(Robot.m_oi.oper.getY(), 0.08);

        if (canAuto) {
            double armSpeed = Range.deadband(Robot.m_oi.oper.getRawAxis(5), 0.08);
    
            if (armSpeed != 0 || leftAnalog != 0) {
                isAuto = false;
            }
            else if (Robot.m_oi.oper.getPOV() == 0) {
                elevAPID.initialize();
                isAuto = true;
                target = -15300;
            }
            else if (Robot.m_oi.oper.getPOV() != -1) {
                elevAPID.initialize();
                isAuto = true;
                target = 0;
            }
            System.out.println(Robot.m_oi.oper.getPOV());
    
            if (isAuto) {
                leftAnalog = elevAPID.getOutput(RobotMap.rightElevator.getEncoder(), target);
            }
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
