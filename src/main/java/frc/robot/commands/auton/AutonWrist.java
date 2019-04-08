package frc.robot.commands.auton;

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
     * 
     * @author ThunderChickens 217
     */
    public AutonWrist(double target) {
        requires(Robot.kWristSubsystem);
        tar = target;
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
        return false;
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