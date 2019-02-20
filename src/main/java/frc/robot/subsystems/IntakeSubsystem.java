/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class IntakeSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  WPI_VictorSPX intakeOne1 = RobotMap.intakeOne; //VictorSPX 
  WPI_VictorSPX intakeTwo1 = RobotMap.intakeTwo; // VictorSPX 
  DoubleSolenoid topHatchSolenoid1 = RobotMap.topHatchSolenoid;

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void intake(double speed){
    intakeOne1.set(speed);
    intakeTwo1.set(speed);
  }

  public void extend(){
    topHatchSolenoid1.set(DoubleSolenoid.Value.kForward);
  }

  public void retract(){
    topHatchSolenoid1.set(DoubleSolenoid.Value.kReverse);
  }
}
