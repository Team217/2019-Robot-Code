/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.*;

public class ClimbingSubsystem extends Subsystem {
    private Value currentPTO = Value.kForward;

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    public boolean isClimbing() {
        return currentPTO == Value.kReverse;
    }

    public void setClimbPTO() {
        currentPTO = Value.kReverse;
        setPTO(currentPTO);
    }

    public void setDrivePTO() {
        currentPTO = Value.kForward;
        setPTO(currentPTO);
    }

    public void setPTO(Value value) {
        RobotMap.climbPTOSolenoid.set(value);
    }
}