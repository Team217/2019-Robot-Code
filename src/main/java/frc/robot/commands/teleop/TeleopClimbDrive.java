package frc.robot.commands.teleop;

import org.team217.*;
import org.team217.pid.*;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

/**
 * Runs the climber in teleop control mode using {@code APID}.
 * 
 * @author ThunderChickens 217
 */
public class TeleopClimbDrive extends Command {
    APID apid = new APID(new PID(0.2, 0, 0, 100), 0.5);
    
    /**
     * Runs the climber in teleop control mode using {@code APID}.
     * 
     * @author ThunderChickens 217
     */
    public TeleopClimbDrive() {
        requires(Robot.kDrivingSubsystem);
    }

    @Override
    protected void initialize() {
        apid.initialize();
        RobotMap.rightMaster.resetEncoder();
    }

    @Override
    protected void execute() {
        double speed = Num.inRange(-apid.getOutput(RobotMap.rightMaster.getPosition(), -57), 0.3);

        double tilt = Num.deadband(RobotMap.pigeonDrive.getRoll(), 2.5);
        double turn = speed * Num.inRange(0.01 * tilt, 0.1); // Correct the climber automatically to stay level
        speed += Math.abs(turn);

        Robot.kDrivingSubsystem.set(speed, turn);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.kDrivingSubsystem.reset();
    }

    @Override
    protected void interrupted() {
        Robot.kDrivingSubsystem.reset();
    }
}