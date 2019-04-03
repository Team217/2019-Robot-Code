package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

/**
 * Holds the arm in place in auton control mode.
 * 
 * @author ThunderChickens 217
 */
public class AutonArmHold extends Command {

    /**
     * Holds the arm in place in auton control mode.
     * 
     * @author ThunderChickens 217
     */
    public AutonArmHold() {
        requires(Robot.kArmSubsystem);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.kArmSubsystem.set(0);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.kArmSubsystem.reset();
    }

    @Override
    protected void interrupted() {
        Robot.kArmSubsystem.reset();
    }
}