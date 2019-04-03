package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

/**
 * Runs the intake in auton control mode.
 * 
 * @author ThunderChickens 217
 */
public class AutonIntake extends Command {
    double speed = 0;

    /**
     * Runs the intake in auton control mode.
     * 
     * @param speed
     *        The intake speed
     * @param timeout
     *        The time before automatically ending the command, in seconds
     * 
     * @author ThunderChickens 217
     */
    public AutonIntake(double speed, double timeout) {
        requires(Robot.kIntakeSubsystem);
        this.speed = speed;
        setTimeout(timeout);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.kIntakeSubsystem.set(speed);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Robot.kIntakeSubsystem.reset();
    }

    @Override
    protected void interrupted() {
        Robot.kIntakeSubsystem.reset();
    }
}