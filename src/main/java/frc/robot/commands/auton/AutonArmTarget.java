package frc.robot.commands.auton;

import org.team217.*;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

/**
 * Holds the arm in place in auton control mode.
 * 
 * @author ThunderChickens 217
 */
public class AutonArmTarget extends Command {
    boolean isPreset = true;
    double tar = 0;

    /**
     * Checks if the arm has reached a target value.
     * 
     * @author ThunderChickens 217
     */
    public AutonArmTarget() {
    }

    /**
     * Checks if the arm has reached a target value.
     * 
     * @param target
     *        The target value
     * 
     * @author ThunderChickens 217
     */
    public AutonArmTarget(double target) {
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
        return isPreset ? Robot.kArmSubsystem.atPreset : Num.isWithinRange(RobotMap.rightArm.getPosition(), tar - 1, tar + 1);
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
}