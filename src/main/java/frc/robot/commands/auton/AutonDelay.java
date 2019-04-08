package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Delays an action in auton control mode.
 * 
 * @author ThunderChickens 217
 */
public class AutonDelay extends Command {

    /**
     * Delays an action in auton control mode.
     * 
     * @param timeout
     *        The delay, in seconds
     * 
     * @author ThunderChickens 217
     */
    public AutonDelay(double timeout) {
        setTimeout(timeout);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
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