/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import org.team217.rev.*;
import org.team217.pid.*;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

/**
 * Runs the wrist in teleop control mode using an {@code AnalogGyro}.
 * 
 * @author ThunderChickens 217
 */
public class TeleopWristGyro extends Command {
    CANSparkMax rightArm1 = RobotMap.rightArm;
    AnalogGyro intakeGyro1 = RobotMap.intakeGyro;

    boolean isPreset = false;
    boolean isAuto = false;
    
    PID wristGyroPID = RobotMap.wristGyroPID;
    
    /**
     * Runs the wrist in teleop control mode using an {@code AnalogGyro}.
     * 
     * @author ThunderChickens 217
     */
    public TeleopWristGyro() {
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
            isAuto = true;
        }

        if (!isPreset && isAuto) {
            if (Robot.m_oi.psButtonOper.get()) {
                RobotMap.intakeGyro.reset();
            }

            Robot.kWristSubsystem.auto();
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
        Robot.kWristSubsystem.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.kWristSubsystem.set(0);
    }
}