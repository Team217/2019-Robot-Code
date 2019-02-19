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

  public static final int pigeonDrive_ID = 0; //PigeonIMU (not right _ID)

  //Lift Mechanism _IDs
  public static final int leftArm_ID = 11; //SparkMAX 11 
  public static final int rightArm_ID = 4; //SparkMAX 4 
  public static final int leftElevator_ID = 15; //TalonSRX 15
  public static final int rightElevator_ID = 0; //TalonSRX 0
  public static final int wrist_ID = 8; //TalonSRX 8 
/*
  public static final int elevatorBottomLimit_ID = 0; //DigitalInput Limit Switch //needs _ID
  public static final int elevatorTopLimit_ID = 0; //DigitalInput Limit Switch //needs _ID
  public static final int armFrontLimit_ID = 0; //DigitalInput Limit Switch //needs _ID
  public static final int armBackLimit_ID = 0; //DigitalInput Limit Switch //needs _ID
  public static final int electricalPanelLimit_ID = 0; //DigitalInput Limit Switch //needs _ID
  public static final int wristFrontLimit_ID = 0; //DigitalInput Limit Switch //needs _ID
  public static final int wristBackLimit_ID = 0; //DigitalInput Limit Switch //needs _ID
*/
  //Intake _IDs
  public static final int intake_ID1 = 10; //VictorSPX 10 
  public static final int intake_ID2 = 5; //VictorSPX 5 !!

  public static final int topHatchSolenoid_ID1 = 1; //Double Solenoid
  public static final int topHatchSolenoid_ID2 = 2; //Double Solenoid

  public static final int intakeGyro_ID = 0; //AnalogGyro

  //Climber _IDs
  /*
  public static final int leftRearClimb_ID = 9; //SparkMAX 9
  public static final int rightRearClimb_ID = 6; //SparkMAX 6

  public static final int leftPTOSolenoid_ID1 = 0; //Double Solenoid //needs _ID
  public static final int leftPTOSolenoid_ID2 = 3; //Double Solenoid //needs _ID

  //public static final int rightPTOSolenoid_ID1 = 0; //Double Solenoid //needs _ID
  //public static final int rightPTOSolenoid_ID2 = 0; //Double Solenoid //needs _ID

  public static final int climbRoller_ID = 7; //TalonSRX 7
  */

  //Driving Controllers/Gyro
  public static CANSparkMax leftMaster = new CANSparkMax(RobotMap.leftDrive_ID1, MotorType.kBrushless);
  public static CANSparkMax leftMidSlave = new CANSparkMax(RobotMap.leftDrive_ID2, MotorType.kBrushless);
  public static CANSparkMax leftBackSlave = new CANSparkMax(RobotMap.leftDrive_ID3, MotorType.kBrushless);

  public static CANSparkMax rightMaster = new CANSparkMax(RobotMap.rightDrive_ID1, MotorType.kBrushless);
  public static CANSparkMax rightMidSlave = new CANSparkMax(RobotMap.rightDrive_ID2, MotorType.kBrushless);
  public static CANSparkMax rightBackSlave = new CANSparkMax(RobotMap.rightDrive_ID3, MotorType.kBrushless);

  public static PigeonIMU pigeonDrive = new PigeonIMU(RobotMap.pigeonDrive_ID);


  //Lift Mechanism Controllers/Gyro/Limits
  public static CANSparkMax leftArm = new CANSparkMax(RobotMap.leftArm_ID, MotorType.kBrushless); //CANSparkMAX, Brushless
  public static CANSparkMax rightArm = new CANSparkMax(RobotMap.rightArm_ID, MotorType.kBrushless); //CANSparkMAX, Brushless

  public static WPI_TalonSRX leftElevator = new WPI_TalonSRX(RobotMap.leftElevator_ID); //WPI_TalonSRX
  public static WPI_TalonSRX rightElevator = new WPI_TalonSRX(RobotMap.rightElevator_ID); //WPI_TalonSRX
  public static WPI_TalonSRX wrist = new WPI_TalonSRX(RobotMap.wrist_ID); //WPI_TalonSRX
  
  public static AnalogGyro intakeGyro = new AnalogGyro(RobotMap.intakeGyro_ID); 
/*
  public static DigitalInput elevatorBottomLimit = new DigitalInput(RobotMap.elevatorBottomLimit_ID);
  public static DigitalInput elevatorTopLimit = new DigitalInput(RobotMap.elevatorTopLimit_ID);
  public static DigitalInput armFrontLimit = new DigitalInput(RobotMap.armFrontLimit_ID);
  public static DigitalInput armBackLimit = new DigitalInput(RobotMap.armBackLimit_ID);
  public static DigitalInput wristFrontLimit = new DigitalInput(RobotMap.wristFrontLimit_ID);
  public static DigitalInput wristBackLimit = new DigitalInput(RobotMap.wristBackLimit_ID);
*/

  //Intake Controllers/Solenoids
  public static WPI_VictorSPX intakeOne = new WPI_VictorSPX(RobotMap.intake_ID1); //VictorSPX 
  public static WPI_VictorSPX intakeTwo = new WPI_VictorSPX(RobotMap.intake_ID2); //VictorSPX

  public static DoubleSolenoid topHatchSolenoid = new DoubleSolenoid(RobotMap.topHatchSolenoid_ID1, RobotMap.topHatchSolenoid_ID2);
 

  //Climber Controllers
  /*
  public static CANSparkMax leftRearClimb = new CANSparkMax(RobotMap.leftRearClimb_ID, MotorType.kBrushless);
  public static CANSparkMax rightRearClimb = new CANSparkMax(RobotMap.rightRearClimb_ID, MotorType.kBrushless);

  public static WPI_TalonSRX climbRoller = new WPI_TalonSRX(RobotMap.climbRoller_ID);

  public static DoubleSolenoid leftPTOSolenoid = new DoubleSolenoid(RobotMap.leftPTOSolenoid_ID1, RobotMap.leftPTOSolenoid_ID2);
  public static DoubleSolenoid rightPTOSolenoid = new DoubleSolenoid(RobotMap.rightPTOSolenoid_ID1, RobotMap.rightPTOSolenoid_ID2);
  */

	//PID
	public static PID drivePID = new PID(0.0, 0.0, 0.0, 100);
	public static PID turnPID = new PID(0.0, 0.0, 0.0, 100);
	public static PID intakePID = new PID(0.0, 0.0, 0.0, 100);
	public static PID elevatorPID = new PID(0.0, 0.0, 0.0, 100);
  public static PID elevatorMaintainPID = new PID(0.0, 0.0, 0.0, 100);
  public static PID armPID = new PID(0.0, 0.0, 0.0, 100);
  public static PID armMaintainPID = new PID(0.0, 0.0, 0.0, 100);


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
