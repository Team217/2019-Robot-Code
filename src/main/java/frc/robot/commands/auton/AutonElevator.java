package frc.robot.commands.auton;

import org.team217.pid.*;
import org.team217.ctre.*;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

public class AutonElevator extends Command {
    WPI_TalonSRX leftElevator1 = RobotMap.leftElevator;
    APID elevAPID = RobotMap.elevAPID;

    double tar = 0;

    public AutonElevator(double target) {
        tar = target;
        requires(Robot.kElevatorSubsystem);
    }

    public AutonElevator(double target, double timeout) {
        this(target);
        setTimeout(timeout);
    }

    public AutonElevator(double target, PID pid) {
        this(target);
        elevAPID.setPID(pid.setTimeout(100));
    }

    public AutonElevator(double target, PID pid, double timeout) {
        this(target, pid);
        setTimeout(timeout);
    }

    @Override
    protected void initialize() {
        elevAPID.initialize();
    }

    @Override
    protected void execute() {
        Robot.kElevatorSubsystem.set(elevAPID.getOutput(leftElevator1.getEncoder(), tar));
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.kElevatorSubsystem.set(0);
    }

    @Override
    protected void interrupted() {
        Robot.kElevatorSubsystem.set(0);
    }
}