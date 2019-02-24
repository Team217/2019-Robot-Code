/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import org.team217.*;
import org.team217.pid.*;
import org.team217.rev.*;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

public class TeleopArmAndWrist extends Command {
    CANSparkMax rightArm1 = RobotMap.rightArm;
    DigitalInput wristFrontLimit1 = RobotMap.wristFrontLimit;
    DigitalInput wristBackLimit1 = RobotMap.wristBackLimit;
    AnalogGyro intakeGyro1 = RobotMap.intakeGyro;

    boolean isRunning = false;

    PID wristGyroPID = RobotMap.wristGyroPID;
    Preset presetState = Preset.Manual, lastPreset = Preset.Manual;
    APID armAPID = RobotMap.armAPID;

    enum Preset {
        Manual,
        Low,
        Mid,
        High,
        RocketAdj
    }

    public TeleopArmAndWrist() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    // reset wrist to be in line with arm in init?
    @Override
    protected void initialize() {
        intakeGyro1.initGyro();
        armAPID.initialize();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (Robot.m_oi.rightTriggerOper.get() || Robot.m_oi.leftTriggerOper.get()) {
            isRunning = false;
        }
        else if (Robot.m_oi.touchPadOper.get()) {
            isRunning = true;
        }

        if (isRunning) {
            boolean isBack = rightArm1.getPosition() < -112;

            double armAngle = intakeGyro1.getAngle(); // Might be pitch or yaw, depending on how electical electricities

            double leftAnalog = Range.deadband(Robot.m_oi.oper.getRawAxis(5), 0.08);
            /*
            if (leftAnalog != 0) {
                presetState = Preset.Manual;
            }
            else if (Robot.m_oi.driver.getPOV() == 0) {
                presetState = Preset.Low;
            }
            else if (Robot.m_oi.driver.getPOV() == 90) {
                presetState = Preset.Mid;
            }
            else if (Robot.m_oi.driver.getPOV() == 180) {
                presetState = Preset.High;
            }
            else if (Robot.m_oi.driver.getPOV() == 270) {
                presetState = Preset.RocketAdj;
            }

            double target = 0;

            switch (presetState) {
            case Low:
                target = (isBack) ? -174 : -41;
                break;
            case Mid:
                target = (isBack) ? -136 : -83;
                break;
            case High:
                target = (isBack) ? -125 : -86;
                break;
            case RocketAdj:
                switch (lastPreset) {
                case Low:
                    target = (isBack) ? -174 : -41;
                    break;
                case Mid:
                    target = (isBack) ? -136 : -83;
                    break;
                case High:
                    target = (isBack) ? -125 : -86;
                    break;
                default:
                    presetState = Preset.Manual;
                    break;
                }
            default:
                break;
            }

            if (!presetState.equals(Preset.Manual)) {
                if (!presetState.equals(Preset.RocketAdj)) {
                    lastPreset = presetState;
                }
                leftAnalog = armAPID.getOutput(RobotMap.rightElevator.getEncoder(), target);
            }
            */
            Robot.kLiftingMechanism.arm(leftAnalog);
            System.out.println("Arm Gyro " + armAngle);

            double speed = 0;

            if (rightArm1.getPosition() > -96) {
                speed = Range.inRange(-wristGyroPID.getOutput(armAngle, 0), -1.0, 1.0);
            }
            else if (rightArm1.getPosition() <= -96 && rightArm1.get() >= -112) {
                if (armAngle < 80) {
                    speed = -.7073;
                }
                else if (armAngle > 100) {
                    speed = .7073;
                }
            }
            else {
                speed = Range.inRange(-wristGyroPID.getOutput(armAngle, 178), -0.5, 0.5);
            }

            Robot.kLiftingMechanism.wrist(speed);
        }

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }

}
