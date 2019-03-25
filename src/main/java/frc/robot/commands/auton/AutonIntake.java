package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

public class AutonIntake extends Command {
    double speed = 0;

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
        Robot.kIntakeSubsystem.set(0);
    }

    @Override
    protected void interrupted() {
        Robot.kIntakeSubsystem.set(0);
    }
}