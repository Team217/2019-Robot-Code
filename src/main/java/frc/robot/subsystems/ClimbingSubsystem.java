/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import org.team217.ctre.*;
import org.team217.rev.*;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.*;

/*
public class ClimbingSubsystem extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    CANSparkMax leftRearClimb1 = RobotMap.leftRearClimb;
    CANSparkMax rightRearClimb1 = RobotMap.rightRearClimb;
    WPI_TalonSRX climbRoller1 = RobotMap.climbRoller;
    DoubleSolenoid leftPTO = RobotMap.leftPTOSolenoid; //front climber
    DoubleSolenoid rightPTO = RobotMap.rightPTOSolenoid; //back climber

    enum PTOMode {
        driveMode, climbMode
    };

    public static PTOMode currentPTO = PTOMode.driveMode;

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    public void driveMode() {
        leftPTO.set(Value.kReverse);
        rightPTO.set(Value.kReverse);
        currentPTO = PTOMode.driveMode;
    }

    public void fullClimbMode() {
        leftPTO.set(Value.kForward);
        rightPTO.set(Value.kForward);
        currentPTO = PTOMode.climbMode;
    }

    public void frontClimbMode() {
        leftPTO.set(Value.kForward);
        rightPTO.set(Value.kReverse);
        currentPTO = PTOMode.climbMode;
    }

    public void backClimbMode() {
        leftPTO.set(Value.kReverse);
        rightPTO.set(Value.kForward);
        currentPTO = PTOMode.climbMode;
    }

    public void climbDrive(double speed) {
        leftRearClimb1.set(speed);
        rightRearClimb1.set(speed);
    }

}
*/