package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

/**
 * Holds the telescope in place in auton control mode.
 * 
 * @author ThunderChickens 217
 */
public class AutonTelescopeHold extends Command {

    /**
     * Holds the telescope in place in auton control mode.
     * 
     * @author ThunderChickens 217
     */
    public AutonTelescopeHold() {
        requires(Robot.kTelescopeSubsystem);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.kTelescopeSubsystem.set(0);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.kTelescopeSubsystem.reset();
    }

    @Override
    protected void interrupted() {
        Robot.kTelescopeSubsystem.reset();
    }
}