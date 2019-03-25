package frc.robot.commands.auton;

import org.team217.pid.*;
import org.team217.rev.*;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

public class AutonArm extends Command {
    CANSparkMax rightArm1 = RobotMap.rightArm;
    APID armAPID = RobotMap.armAPID;

    double tar = 0;

    public AutonArm(double target) {
        requires(Robot.kArmSubsystem);
        tar = target;
    }

    public AutonArm(double target, double timeout) {
        this(target);
        setTimeout(timeout);
    }

    public AutonArm(double target, PID pid) {
        this(target);
        armAPID.setPID(pid.setTimeout(100));
    }

    public AutonArm(double target, PID pid, double timeout) {
        this(target, pid);
        setTimeout(timeout);
    }

    @Override
    protected void initialize() {
        armAPID.initialize();
    }

    @Override
    protected void execute() {
        Robot.kArmSubsystem.set(armAPID.getOutput(rightArm1.getPosition(), tar));
    }

    @Override
    protected boolean isFinished() {
        return false;
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