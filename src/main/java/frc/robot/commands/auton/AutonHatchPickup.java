package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

public class AutonHatchPickup extends Command {
    boolean extend = false;

    public AutonHatchPickup(boolean extend) {
        requires(Robot.kIntakeSubsystem);
        this.extend = extend;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        if (extend) {
            Robot.kIntakeSubsystem.extend();
        }
        else {
            Robot.kIntakeSubsystem.retract();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.kIntakeSubsystem.retract();
    }

    @Override
    protected void interrupted() {
        Robot.kIntakeSubsystem.retract();
    }
}