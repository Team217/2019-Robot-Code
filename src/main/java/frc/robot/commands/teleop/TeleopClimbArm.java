package frc.robot.commands.teleop;

import org.team217.*;
import org.team217.pid.*;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

/**
 * Runs the arm in teleop control mode using {@code APID} to climb.
 * 
 * @author ThunderChickens 217
 */
public class TeleopClimbArm extends Command {
    PID pid = new PID(0.05, 0, 0, 100);
    
    /**
     * Runs the arm in teleop control mode using {@code APID} to climb.
     * 
     * @author ThunderChickens 217
     */
    public TeleopClimbArm() {
        requires(Robot.kArmSubsystem);
    }

    @Override
    protected void initialize() {
        pid.resetErrors();
    }

    @Override
    protected void execute() {
        double speed = 0.5 + .004 * -RobotMap.rightMaster.getPosition();

        double pitch = RobotMap.pigeonDrive.getPitch();
        double correction = Num.inRange(pid.getOutput(pitch, 0), 0.2);

        Robot.kArmSubsystem.set(speed + correction);

        System.out.println("Arm speed: " + speed);
        System.out.println("Correction: " + correction);
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