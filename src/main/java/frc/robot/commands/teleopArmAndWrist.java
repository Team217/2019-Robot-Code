/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import org.team217.*;
import org.team217.ctre.*;
import org.team217.pid.*;
import org.team217.rev.*;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

public class teleopArmAndWrist extends Command {
  WPI_TalonSRX wrist1 = RobotMap.wrist;
  CANSparkMax rightArm1 = RobotMap.rightArm;
  AnalogGyro intakeGyro1 = RobotMap.intakeGyro;
  boolean isRunning = false;
  PID wristPID = new PID(0.03, 0, 0);

  public teleopArmAndWrist() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  // reset wrist to be in line with arm in init?
  @Override
  protected void initialize() {
    intakeGyro1.initGyro();
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
      double armAngle = intakeGyro1.getAngle(); // Might be pitch or yaw, depending on how electical electricities
  
      double leftAnalog = Range.deadband(Robot.m_oi.oper.getRawAxis(5), 0.05);
      Robot.kLiftingMechanism.arm(leftAnalog);
      System.out.println("Arm Gyro " + armAngle);

      double speed = 0;

      if (rightArm1.getPosition() > -96) { // get potentiometer values
        speed = Range.inRange(-wristPID.getOutput(armAngle, 0), -0.5, 0.5);
      }
  
      if (rightArm1.getPosition() < -96 && rightArm1.get() > -112) {
        if (armAngle < 80) {
          speed = -.3707;
        } else if (armAngle > 100) {
          speed = .3707;
        }
      }
  
      if (rightArm1.getPosition() < -112) { // get potentiometer values
        speed = Range.inRange(-wristPID.getOutput(armAngle, 178), -0.5, 0.5);
      }

      wrist1.set(speed);
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
