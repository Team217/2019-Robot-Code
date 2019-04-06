package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

/**
 * Changes the target angle for driving.
 * 
 * @author ThunderChickens 217
 */
public class AutonAngle extends Command {
    double angle = 0;
    boolean isCustom = true;

    /**
     * Changes the target angle for driving.
     * 
     * @param angle
     *        The new driving target angle
     * @param timeout
     *        The time before automatically ending the command, in seconds
     * 
     * @author ThunderChickens 217
     */
    public AutonAngle(double angle, double timeout) {
        this.angle = angle;
        setTimeout(timeout);
    }

    /**
     * Changes the target angle for driving to the current angle.
     * 
     * @author ThunderChickens 217
     */
    public AutonAngle() {
        isCustom = false;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        if (isCustom) {
            if (isTimedOut()) {
                Robot.kDrivingSubsystem.targetAngle = angle;
            }
            return isTimedOut();
        }
        
        Robot.kDrivingSubsystem.targetAngle = RobotMap.pigeonDrive.getAngle();
        return true;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
}