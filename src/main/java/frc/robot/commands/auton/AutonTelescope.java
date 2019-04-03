package frc.robot.commands.auton;

import org.team217.pid.*;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

/**
 * Runs the telescope in auton control mode using {@code APID}.
 * 
 * @author ThunderChickens 217
 */
public class AutonTelescope extends Command {
    APID apid = RobotMap.telescopeAPID;
    double tar = 0;

    /**
     * Runs the telescope in auton control mode using {@code APID}.
     * 
     * @param target
     *        The {@code PID} target
     * 
     * @author ThunderChickens 217
     */
    public AutonTelescope(double target) {
        requires(Robot.kTelescopeSubsystem);
        tar = target;
    }

    /**
     * Runs the telescope in auton control mode using {@code APID}.
     * 
     * @param target
     *        The {@code PID} target
     * @param timeout
     *        The time before automatically ending the command, in seconds
     * 
     * @author ThunderChickens 217
     */
    public AutonTelescope(double target, double timeout) {
        this(target);
        setTimeout(timeout);
    }

    @Override
    protected void initialize() {
        apid.initialize();
    }

    @Override
    protected void execute() {
        Robot.kTelescopeSubsystem.set(apid.getOutput(RobotMap.telescope.getEncoder(), tar));
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Robot.kTelescopeSubsystem.reset();
    }

    @Override
    protected void interrupted() {
        Robot.kTelescopeSubsystem.reset();  
    }
}