package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

/**
 * Holds the elevator in place in auton control mode.
 * 
 * @author ThunderChickens 217
 */
public class AutonElevatorHold extends Command {

    /**
     * Holds the elevator in place in auton control mode.
     * 
     * @author ThunderChickens 217
     */
    public AutonElevatorHold() {
        requires(Robot.kElevatorSubsystem);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.kElevatorSubsystem.set(0);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.kElevatorSubsystem.reset();
    }

    @Override
    protected void interrupted() {
        Robot.kElevatorSubsystem.reset();
    }
}