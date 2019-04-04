package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

/**
 * Runs the hatch pickup in auton control mode.
 * 
 * @author ThunderChickens 217
 */
public class AutonHatchPickup extends Command {
    boolean extend = false;

    /**
     * Runs the hatch pickup in auton control mode.
     * 
     * @param shouldExtend
     *        {@code true} if the hatch intake should be extended
     * @param timeout
     *        The time before automatically ending the command, in seconds
     * 
     * @author ThunderChickens 217
     */
    public AutonHatchPickup(boolean shouldExtend, double timeout) {
        requires(Robot.kIntakeSubsystem);
        extend = shouldExtend;
        setTimeout(timeout);
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
        return isTimedOut();
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