/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.team217.ctre.*;
import org.team217.pid.*;
import org.team217.rev.*;

import edu.wpi.first.wpilibj.*;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    //SparkMAX
    // 2 for arm brushless
    // 2 for intake brushed
    // 1 for wrist brushed

    //TalonSRX
    // 2 for elevator

    //DriveBase _IDs
    public static final int leftDrive_ID1 = 12; //SparkMAX 12
    public static final int leftDrive_ID2 = 13; //SparkMAX 13
    public static final int leftDrive_ID3 = 14; //SparkMAX 14

    public static final int rightDrive_ID1 = 3; //SparkMAX 3
    public static final int rightDrive_ID2 = 2; //SparkMAX 2
    public static final int rightDrive_ID3 = 1; //SparkMAX 1

    public static final int pigeonDrive_ID = 0; //PigeonIMU

    //Lift Mechanism _IDs
    public static final int telescope_ID = 11; //SparkMAX 11 
    public static final int rightArm_ID = 4; //SparkMAX 4 
    public static final int leftElevator_ID = 15; //TalonSRX 15
    public static final int rightElevator_ID = 0; //TalonSRX 0
    public static final int wrist_ID = 8; //TalonSRX 8 

    public static final int elevatorBottomLimit_ID = 7; //DigitalInput Limit Switch
    public static final int elevatorTopLimit_ID = 9; //DigitalInput Limit Switch
    public static final int wristFrontLimit_ID = 5; //DigitalInput Limit Switch
    public static final int wristBackLimit_ID = 6; //DigitalInput Limit Switch
    public static final int ballLimit_ID = 8; //DigitalInput Limit Switch 
    public static final int telescopeOutLimit_ID = 1; //DigitalInput Limit Switch 
    public static final int telescopeInLimit_ID = 2; //DigitalInput Limit Switch 
    public static final int LED_ID = 4; //DigitalInput LED

    //Intake _IDs
    public static final int intake_ID1 = 10; //VictorSPX 10 
    public static final int intake_ID2 = 5; //VictorSPX 5 

    public static final int topHatchSolenoid_ID1 = 1; //Double Solenoid
    public static final int topHatchSolenoid_ID2 = 2; //Double Solenoid

    public static final int intakeGyro_ID = 0; //AnalogGyro

    //Climber _IDs
    public static final int climbPTOSolenoid_ID1 = 0; //Double Solenoid
    public static final int climbPTOSolenoid_ID2 = 3; //Double Solenoid

    //Driving Controllers/Gyro
    public static CANSparkMax leftMaster = new CANSparkMax(leftDrive_ID1, MotorType.kBrushless);
    public static CANSparkMax leftMidSlave = new CANSparkMax(leftDrive_ID2, MotorType.kBrushless);
    public static CANSparkMax leftBackSlave = new CANSparkMax(leftDrive_ID3, MotorType.kBrushless);

    public static CANSparkMax rightMaster = new CANSparkMax(rightDrive_ID1, MotorType.kBrushless);
    public static CANSparkMax rightMidSlave = new CANSparkMax(rightDrive_ID2, MotorType.kBrushless);
    public static CANSparkMax rightBackSlave = new CANSparkMax(rightDrive_ID3, MotorType.kBrushless);

    public static PigeonIMU pigeonDrive = new PigeonIMU(pigeonDrive_ID);

    //Lift Mechanism Controllers/Gyro/Limits
    public static CANSparkMax telescope = new CANSparkMax(telescope_ID, MotorType.kBrushed); //CANSparkMAX, Brushless
    public static CANSparkMax rightArm = new CANSparkMax(rightArm_ID, MotorType.kBrushless); //CANSparkMAX, Brushless

    public static WPI_TalonSRX leftElevator = new WPI_TalonSRX(leftElevator_ID); //WPI_TalonSRX
    public static WPI_TalonSRX rightElevator = new WPI_TalonSRX(rightElevator_ID); //WPI_TalonSRX
    public static WPI_TalonSRX wrist = new WPI_TalonSRX(wrist_ID); //WPI_TalonSRX

    public static AnalogGyro intakeGyro = new AnalogGyro(intakeGyro_ID);

    public static DigitalInput elevatorBottomLimit = new DigitalInput(elevatorBottomLimit_ID);
    public static DigitalInput elevatorTopLimit = new DigitalInput(elevatorTopLimit_ID);
    //  public static DigitalInput armFrontLimit = new DigitalInput(armFrontLimit_ID);
    //  public static DigitalInput armBackLimit = new DigitalInput(armBackLimit_ID);
    public static DigitalInput wristFrontLimit = new DigitalInput(wristFrontLimit_ID);
    public static DigitalInput wristBackLimit = new DigitalInput(wristBackLimit_ID);
    public static DigitalInput ballLimit = new DigitalInput(ballLimit_ID);
    public static DigitalInput telescopeOutLimit = new DigitalInput(telescopeOutLimit_ID);
    public static DigitalInput telescopeInLimit = new DigitalInput(telescopeInLimit_ID);

    //Intake Controllers/Solenoids
    public static WPI_VictorSPX intakeOne = new WPI_VictorSPX(intake_ID1); //VictorSPX 
    public static WPI_VictorSPX intakeTwo = new WPI_VictorSPX(intake_ID2); //VictorSPX

    public static DoubleSolenoid topHatchSolenoid = new DoubleSolenoid(topHatchSolenoid_ID1,
            topHatchSolenoid_ID2);

    //Climber PTOs
    public static DoubleSolenoid climbPTOSolenoid = new DoubleSolenoid(climbPTOSolenoid_ID1, climbPTOSolenoid_ID2);

    //PID
    public static final PID elevatorHoldPID = new PID(0.0005, 0.000005, 0, 100).setMinMax(-0.1, 0.1);
    public static final PID armHoldPID = new PID(0.05, 0.0002, 0, 100).setMinMax(-0.1, 0.1);
    public static final PID wristGyroPID = new PID(0.05, 0.005, 0, 100).setMinMax(-0.25, 0.25);
    public static final PID visionPID = new PID(0, 0.002, 0, 100).setMinMax(-0.2, 0.2);
    public static final APID armAPID = new APID(new PID(0.05, 0.0002, 0, 100).setMinMax(-0.2, 0.2), 1.5);
    public static final APID wristAPID = new APID(new PID(0.001, 0.00001, 0, 100).setMinMax(-0.2, 0.2), 1.0);
    public static final APID elevAPID = new APID(new PID(0.0005, 0.000005, 0, 100).setMinMax(-0.2, 0.2), 1.5);
    public static final APID telescopeAPID = new APID(new PID(0.0001, 0, 0, 100).setMinMax(-0.1, 0.1), 0.5);

    //Joysticks
    public static final int driverPort = 0;
    public static final int operPort = 1;

    //Buttons
    public static final int buttonSquare = 1;
    public static final int buttonX = 2;
    public static final int buttonCircle = 3;
    public static final int buttonTriangle = 4;
    public static final int leftBumper = 5;
    public static final int rightBumper = 6;
    public static final int leftTrigger = 7;
    public static final int rightTrigger = 8;
    public static final int buttonShare = 9;
    public static final int buttonOption = 10;
    public static final int leftAnalog = 11;
    public static final int rightAnalog = 12;
    public static final int psButton = 13;
    public static final int touchPad = 14;

    //Axes
    public static final int X_Axis = 0;
    public static final int Y_Axis = 1;
    public static final int Z_Axis = 2;
    public static final int L_Rotate = 3;
    public static final int R_Rotate = 4;
    public static final int Z_RotateAxis = 5;

    //Potentiometer lmao nvm
    //public static final int potentiometer_ID = 0;
    //public static AnalogPotentiometer poteniometer = new AnalogPotentiometer(potentiometer_ID);
}
