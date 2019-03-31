package frc.robot.commands.auton;

import org.team217.pid.*;
import org.team217.rev.*;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

/**
 * Runs the arm in auton control mode using {@code APID}.
 * 
 * @author ThunderChickens 217
 */
public class AutonArm extends Command {
    CANSparkMax rightArm1 = RobotMap.rightArm;
    APID apid = RobotMap.armAPID;

    double tar = 0;

    /**
     * Runs the arm in auton control mode using {@code APID}.
     * 
     * @param target
     *        The {@code PID} target
     * 
     * @author ThunderChickens 217
     */
    public AutonArm(double target) {
        requires(Robot.kArmSubsystem);
        tar = target;
    }

    /**
     * Runs the arm in auton control mode using {@code APID}.
     * 
     * @param target
     *        The {@code PID} target
     * @param pid
     *        The {@code PID} variable
     * 
     * @author ThunderChickens 217
     */
    public AutonArm(double target, PID pid) {
        this(target);
        apid.setPID(pid.setTimeout(100));
    }

    @Override
    protected void initialize() {
        apid.initialize();
    }

    @Override
    protected void execute() {
        Robot.kArmSubsystem.set(apid.getOutput(rightArm1.getPosition(), tar));
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Robot.kArmSubsystem.set(0);
    }

    @Override
    protected void interrupted() {
        Robot.kArmSubsystem.set(0);
    }
}