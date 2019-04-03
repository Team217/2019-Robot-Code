/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

/**
 * Runs the telescope in teleop control mode.
 * 
 * @author ThunderChickens 217
 */
public class TeleopTelescope extends Command {

    boolean isPreset = false;
    int direction = 0;
    
    /**
     * Runs the telescope in teleop control mode.
     * 
     * @author ThunderChickens 217
     */
    public TeleopTelescope() {
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

        if (!isPreset) {
            double speed = 0;

            if (Robot.m_oi.squareOper.get()) { //out
                speed = 1;
            }
            else if (Robot.m_oi.xOper.get()) { //in
                speed = -1;
            }
            
            Robot.kTelescopeSubsystem.set(speed);
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
        Robot.kTelescopeSubsystem.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.kTelescopeSubsystem.reset();
    }
}