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
        return false;
    }

    @Override
    protected void end() {
        Robot.kArmSubsystem.reset();
    }

    @Override
    protected void interrupted() {
        Robot.kArmSubsystem.reset();
    }
}