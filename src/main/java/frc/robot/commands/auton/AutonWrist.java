package frc.robot.commands.auton;

import org.team217.pid.*;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

public class AutonWrist extends Command {
    double tar = 0;
    APID apid = RobotMap.wristAPID;

    public AutonWrist(double target) {
        requires(Robot.kWristSubsystem);
        tar = target;
    }

    public AutonWrist(double target, double accelTime) {
        this(target);
        apid.setAccelTime(accelTime);
    }

    public AutonWrist(double target, PID pid, double accelTime) {
        this(target);
        apid = new APID(pid.setTimeout(100), accelTime);
    }

    public AutonWrist(double target, PID pid) {
        this(target, pid, 0);
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
        Robot.kWristSubsystem.set(0);
    }

    @Override
    protected void interrupted() {
        Robot.kWristSubsystem.set(0);
    }
}