package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

/**
 * Changes the target angle for driving.
 * 
 * @author ThunderChickens 217
 */
public class AutonAngle extends Command {
    double angle = 0;

    /**
     * Changes the target angle for driving.
     * 
     * @param angle
     *        The new driving target angle
     * @param timeout
     *        The time before automatically ending the command, in seconds
     * 
     * @author ThunderChickens 217
     */
    public AutonAngle(double angle, double timeout) {
        this.angle = angle;
        setTimeout(timeout);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.kDrivingSubsystem.targetAngle = angle;
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
}