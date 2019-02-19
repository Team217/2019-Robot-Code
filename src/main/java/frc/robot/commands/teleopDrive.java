package frc.robot.commands;

import frc.robot.*;
import org.team217.*;
import org.team217.pid.*;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class teleopDrive extends Command {
    PID turnPID1 = new PID(100);

    public teleopDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    boolean antiTipOn = false;

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double speed = Range.deadband(Robot.m_oi.driver.getY(), 0.1);
        double turn = Range.deadband(Robot.m_oi.driver.getZ(), 0.1);

        if (Robot.m_oi.rightBumperDriver.get()) {
            double x = Robot.getX1Vis();
            double area = Robot.getArea1Vis();

            double kP = Range.inRange(.03 / Math.sqrt(area) - .01, 0.015, 0.025);

            turnPID1.setPID(kP, 0.002, 0);
            turnPID1.setMinMax(-0.2, 0.2);

            double visSpeed;

            if (Range.isWithinRange(x, -0.5, 0.5)) {
                visSpeed = 0.0;
            }
            else {
                visSpeed = turnPID1.getOutput(0, x);
            }

            turn = visSpeed;
        }

        if (Robot.m_oi.leftTriggerDriver.get()) {
            antiTipOn = false;
        }
        else if (Robot.m_oi.rightTriggerDriver.get()) {
            antiTipOn = true;
        }

        Robot.kDrivingSubsystem.teleopDrive(speed, turn, antiTipOn);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}