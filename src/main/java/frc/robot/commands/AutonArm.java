package frc.robot.commands;

import org.team217.pid.*;
import org.team217.rev.*;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

public class AutonArm extends Command {
    CANSparkMax rightArm1 = RobotMap.rightArm;
    APID armAPID = RobotMap.armAPID;

    double tar = 0;

    public AutonArm(double target) {
        tar = target;
        requires(Robot.kArmSubsystem);
    }

    public AutonArm(double target, double timeout) {
        this(target);
        setTimeout(timeout);
    }

    public AutonArm(double target, double kP, double kI, double kD) {
        this(target);
        armAPID.setPID(new PID(kP, kI, kD, 100));
    }

    public AutonArm(double target, double kP, double kI, double kD, double timeout) {
        this(target, kP, kI, kD);
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