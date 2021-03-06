/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.*;

/**
 * Manages the robot's climbing mechanism.
 * 
 * @author ThunderChickens 217
 */
public class ClimbingSubsystem extends Subsystem {
    Value currentPTO = Value.kForward;

    DoubleSolenoid elevatorLockDoubleSolenoid1 = RobotMap.elevatorLockDoubleSolenoid;
    DoubleSolenoid spoolLockDoubleSolenoid1 = RobotMap.spoolLockDoubleSolenoid;

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    /** Returns {@code true} if the PTO is set to climbing mode. */
    public boolean isClimbing() {
        return currentPTO == Value.kReverse;
    }

    /** Sets the PTO to climbing mode. */
    public void setClimbPTO() {
        currentPTO = Value.kReverse;
        setPTO(currentPTO);
        elevatorLockDoubleSolenoid1.set(Value.kReverse); // should be extended
        spoolLockDoubleSolenoid1.set(Value.kForward); // should be retracted
    }

    /** Sets the PTO to driving mode. */
    public void setDrivePTO() {
        currentPTO = Value.kForward;
        setPTO(currentPTO);
        elevatorLockDoubleSolenoid1.set(Value.kForward); //should be retracted
        spoolLockDoubleSolenoid1.set(Value.kReverse); //should be extended
    }

    /**
     * Sets the PTO direction.
     * 
     * @param value
     *        The PTO direction {@code Value}
     */
    public void setPTO(Value value) {
        RobotMap.climbPTOSolenoid.set(value);
    }
}