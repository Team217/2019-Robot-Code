package frc.robot.commands.auton;

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
    APID apid = RobotMap.elevAPID.clone();

    double tar = 0;

    /**
     * Runs the elevator in auton control mode using {@code APID}.
     * 
     * @param target
     *        The {@code PID} target
     * 
     * @author ThunderChickens 217
     */
    public AutonElevator(double target) {
        requires(Robot.kElevatorSubsystem);
        tar = target;
    }

    /**
     * Runs the elevator in auton control mode using {@code APID}.
     * 
     * @param target
     *        The {@code PID} target
     * @param isAccel
     *        {@code true} if the elevator should accelerate
     * 
     * @author ThunderChickens 217
     */
    public AutonElevator(double target, boolean isAccel) {
        this(target);
        if (!isAccel) {
            apid.setAccelTime(0);
        }
    }

    @Override
    protected void initialize() {
        apid.initialize();
    }

    @Override
    protected void execute() {
        Robot.kElevatorSubsystem.set(-apid.getOutput(leftElevator1.getEncoder(), tar));
    }

    @Override
    protected boolean isFinished() {
        return false;
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