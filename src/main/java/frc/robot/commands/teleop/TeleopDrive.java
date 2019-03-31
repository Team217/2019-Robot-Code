package frc.robot.commands.teleop;

import frc.robot.*;
import org.team217.*;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Runs the drivebase in teleop control mode.
 * 
 * @author ThunderChickens 217
 */
public class TeleopDrive extends Command {
    /**
    * Runs the drivebase in teleop control mode.
    * 
    * @author ThunderChickens 217
    */
    public TeleopDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    boolean antiTipOn = false;

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.kDrivingSubsystem.resetVisionPID();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double speed = Num.deadband(Robot.m_oi.driver.getY(), 0.08);
        double turn = Num.deadband(Robot.m_oi.driver.getZ(), 0.08);

        if (Robot.kClimbingSubsystem.isClimbing()) {
            turn /= 2;
            if (turn == 0) {
                double tilt = Num.deadband(RobotMap.pigeonDrive.getRoll(), 2.5);
                //turn = Num.inRange(0.01 * tilt, 0.1); // Correct the climber automatically to stay level
            }
            speed += Math.abs(turn);
            Robot.kDrivingSubsystem.set(speed, turn);
        }
        else {
            if (Robot.m_oi.leftTriggerDriver.get()) {
                antiTipOn = false;
            }
            else if (Robot.m_oi.rightTriggerDriver.get()) {
                antiTipOn = true;
            }
    
            if (Robot.m_oi.rightBumperDriver.get()) {
                Robot.kDrivingSubsystem.visionDrive(speed, true, antiTipOn);
            }
            else if (Robot.m_oi.leftBumperDriver.get()) {
                Robot.kDrivingSubsystem.visionDrive(speed, false, antiTipOn);
            }
            else {
                Robot.kDrivingSubsystem.resetVisionPID();
                Robot.kDrivingSubsystem.set(speed, turn, antiTipOn);
            }
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.kDrivingSubsystem.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        Robot.kDrivingSubsystem.set(0);
    }
}