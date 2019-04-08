package frc.robot.commands.auton;

import org.team217.*;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

/**
 * Holds the elevator in place in auton control mode.
 * 
 * @author ThunderChickens 217
 */
public class AutonTelescopeTarget extends Command {
    boolean isPreset = true;
    double tar = 0;

    /**
     * Checks if the telescope has reached a target value.
     * 
     * @author ThunderChickens 217
     */
    public AutonTelescopeTarget() {
    }

    /**
     * Checks if the telescope has reached a target value.
     * 
     * @param target
     *        The target value
     * 
     * @author ThunderChickens 217
     */
    public AutonTelescopeTarget(double target) {
        tar = target;
        isPreset = false;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return isPreset ? Robot.kTelescopeSubsystem.atPreset : Num.isWithinRange(RobotMap.telescope.getEncoder(), tar - 50, tar + 50);
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