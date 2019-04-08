/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.teleop;

import org.team217.*;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

/**
 * Runs the wrist in teleop control mode.
 * 
 * @author ThunderChickens 217
 */
public class TeleopWrist extends Command {

    boolean isPreset = false;
    boolean isAuto = false;
    
    /**
     * Runs the wrist in teleop control mode.
     * 
     * @author ThunderChickens 217
     */
    public TeleopWrist() {
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
        if (PresetState.getPOVStatus()) {
            isPreset = PresetState.getStatus();
        }
        else if (!PresetState.getStatus()) {
            isPreset = false;
        }

        if (Robot.m_oi.rightTriggerOper.get() || Robot.m_oi.leftTriggerOper.get()) {
            isAuto = false;
        }
        else if (Robot.m_oi.buttonShareOper.get()) {
            //isAuto = true;
        }

        if (!isPreset && !isAuto) {
            //moving wrist independently
            double upSpeed = 1 + Num.deadband(Robot.m_oi.oper.getRawAxis(3), 0.05);
            double downSpeed = 1 + Num.deadband(Robot.m_oi.oper.getRawAxis(4), 0.05);

            if (Robot.m_oi.leftTriggerOper.get()) { //moving up
                Robot.kWristSubsystem.set(upSpeed);
            }
            else if (Robot.m_oi.rightTriggerOper.get()) { //moving down
                Robot.kWristSubsystem.set(-downSpeed);
            }
            else {
                Robot.kWristSubsystem.set(0);
            }
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
        Robot.kWristSubsystem.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.kWristSubsystem.reset();
    }
}