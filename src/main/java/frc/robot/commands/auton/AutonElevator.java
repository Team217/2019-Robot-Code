package frc.robot.commands.auton;

import org.team217.*;
import org.team217.pid.*;
import org.team217.ctre.*;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

/**
 * Runs the elevator in auton control mode using {@code APID}.
 * 
 * @author ThunderChickens 217
 */
public class AutonElevator extends Command {
    WPI_TalonSRX leftElevator1 = RobotMap.leftElevator;
    APID apid = RobotMap.elevAPID;

    double tar = 0;

    /**
     * Runs the elevator in auton control mode using {@code APID}.
     * 
     * @param target
     *        The {@code PID} target
     * @param timeout
     *        The time before automatically ending the command, in seconds
     * 
     * @author ThunderChickens 217
     */
    public AutonElevator(double target, double timeout) {
        requires(Robot.kElevatorSubsystem);
        tar = target;
        setTimeout(timeout);
    }

    @Override
    protected void initialize() {
        apid.initialize();
    }

    @Override
    protected void execute() {
        Robot.kElevatorSubsystem.set(apid.getOutput(leftElevator1.getEncoder(), tar));
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut() || Num.isWithinRange(RobotMap.leftElevator.getEncoder(), tar - 50, tar + 50);
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