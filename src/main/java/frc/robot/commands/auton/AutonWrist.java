package frc.robot.commands.auton;

import org.team217.*;
import org.team217.pid.*;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

/**
 * Runs the wrist in auton control mode using {@code APID}.
 * 
 * @author ThunderChickens 217
 */
public class AutonWrist extends Command {
    double tar = 0;
    APID apid = RobotMap.wristAPID;

    /**
     * Runs the wrist in auton control mode using {@code APID}.
     * 
     * @param target
     *        The {@code PID} target
     * @param timeout
     *        The time before automatically ending the command, in seconds
     * 
     * @author ThunderChickens 217
     */
    public AutonWrist(double target, double timeout) {
        requires(Robot.kWristSubsystem);
        tar = target;
        setTimeout(timeout);
    }

    @Override
    protected void initialize() {
        apid.initialize();
    }

    @Override
    protected void execute() {
        Robot.kWristSubsystem.set(apid.getOutput(RobotMap.wrist.getEncoder(), tar));
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut() || Num.isWithinRange(RobotMap.wrist.getEncoder(), tar - 50, tar + 50);
    }

    @Override
    protected void end() {
        Robot.kWristSubsystem.reset();
    }

    @Override
    protected void interrupted() {
        Robot.kWristSubsystem.reset();
    }
}