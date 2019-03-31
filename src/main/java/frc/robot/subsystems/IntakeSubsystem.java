/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import org.team217.rev.CANSparkMax;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.*;

/**
 * Manages the robot's intake mechanism.
 * 
 * @author ThunderChickens 217
 */
public class IntakeSubsystem extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    CANSparkMax intakeOne = RobotMap.intakeOne; //SparkMAX 
    DoubleSolenoid hatchSolenoid = RobotMap.hatchSolenoid;

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    /**
     * Runs the intake motors.
     * 
     * @param speed
     *        The intake speed
     */
    public void set(double speed) {
        intakeOne.set(-speed);
    }

    /** Extends the hatch intake. */
    public void extend() {
        hatchSolenoid.set(DoubleSolenoid.Value.kForward);
    }
    
    /** Retracts the hatch intake. */
    public void retract() {
        hatchSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
}
