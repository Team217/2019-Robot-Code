/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.time.Clock;

import org.team217.*;

import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.*;
import edu.wpi.first.wpilibj.smartdashboard.*;

import frc.robot.commandgroups.*;
import frc.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    public static final DrivingSubsystem kDrivingSubsystem = new DrivingSubsystem();
    public static final IntakeSubsystem kIntakeSubsystem = new IntakeSubsystem();
    public static final ArmSubsystem kArmSubsystem = new ArmSubsystem();
    public static final TelescopeSubsystem kTelescopeSubsystem = new TelescopeSubsystem();
    public static final WristSubsystem kWristSubsystem = new WristSubsystem();
    public static final ElevatorSubsystem kElevatorSubsystem = new ElevatorSubsystem();
    public static final ClimbingSubsystem kClimbingSubsystem = new ClimbingSubsystem();
    public static OI m_oi;

    Command teleopCommand;
    Command autonomousCommand;

    static double x1 = 0;
    static double y1 = 0;
    static double area1 = 0;

    static double x2 = 0;
    static double y2 = 0;
    static double area2 = 0;

    static final Clock clock = Clock.systemUTC();
    boolean pigeonValid = true;
    long pigeonValidStart = 0;
    double lastRoll = 0, lastPitch = 0;

    // Auton stuff
    SendableChooser<String> auton = new SendableChooser<String>();
    static final String manualHatch = "Manual Hatch";
    static final String manualCargo = "Manual Cargo";
    static final String frontRocket = "Front Rocket";
    static final String backRocket = "Back Rocket";
    static final String frontCargo = "Front Cargoship";
    static final String sideCargo = "Side Cargoship";

    SendableChooser<String> position = new SendableChooser<String>();
    public static final String left = "Left";
    public static final String right = "Right";

    public static boolean isAuton = true;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        m_oi = new OI();

        RobotMap.rightMaster.resetEncoder();
        RobotMap.leftMaster.resetEncoder();

        RobotMap.rightArm.resetEncoder();
        RobotMap.telescope.resetEncoder();

        RobotMap.telescope.invertEncoder(false); // TODO: true for comp bot, false for practice
        RobotMap.telescope.setEncoder(24827); // TODO: Get comp bot values
        Robot.kTelescopeSubsystem.lastTelescopePos = 24827;

        RobotMap.rightElevator.resetEncoder();
        RobotMap.leftElevator.resetEncoder();

        RobotMap.leftElevator.invertEncoder(false); // TODO: true for comp bot, false for practice
        RobotMap.leftElevator.setEncoder(8580); // TODO: Get comp bot values
        Robot.kElevatorSubsystem.lastElevatorPos = 8580;

        RobotMap.wrist.resetEncoder();

        RobotMap.pigeonDrive.reset();
       // RobotMap.intakeGyro.reset();

        kClimbingSubsystem.setDrivePTO();

        putAuton();
    }

    /**
     * This function is called every robot packet, no matter the mode. Use
     * this for items like diagnostics that you want ran during disabled,
     * autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic functions, but before
     * LiveWindow and SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        PresetState.getPresetState();
    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
     * the robot is disabled.
     */
    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
        kArmSubsystem.lastArmPos = RobotMap.rightArm.getPosition();
        kElevatorSubsystem.lastElevatorPos = RobotMap.leftElevator.getEncoder();
        Scheduler.getInstance().run();
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable
     * chooser code works with the Java SmartDashboard. If you prefer the
     * LabVIEW Dashboard, remove all of the chooser code and uncomment the
     * getString code to get the auto name from the text box below the Gyro
     *
     * <p>You can add additional auto modes by adding additional commands to the
     * chooser code above (like the commented example) or additional comparisons
     * to the switch structure below with additional strings & commands.
     */
    @Override
    public void autonomousInit() {
        if (teleopCommand != null) {
            teleopCommand.cancel();
        }

        autonomousCommand = autonSelector();

        // schedule the autonomous command (example)
        kClimbingSubsystem.setDrivePTO();
        if (autonomousCommand != null) {
            autonomousCommand.start();
        }
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
        if (!isValidPigeon()) {
            RobotMap.pigeonDrive.resetPitch();
            RobotMap.pigeonDrive.resetRoll();
        }

        if (isAuton && m_oi.touchPadDriver.get()) {
            autonomousCommand.cancel();
            autonomousCommand = new TeleopCommands();
            autonomousCommand.start();
        }

        smartDashboard();
        cams();

        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        RobotMap.pigeonDrive.reset();
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }

        teleopCommand = new TeleopCommands();

        if (teleopCommand != null) {
            teleopCommand.start();
        }

        kClimbingSubsystem.setDrivePTO();
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        cams();
        smartDashboard();

        if (!isValidPigeon()) {
            RobotMap.pigeonDrive.resetPitch();
            RobotMap.pigeonDrive.resetRoll();
        }

        Scheduler.getInstance().run();
    }

    /** Returns the X of Vision Camera 1. */
    public static double getX1Vis() { //front
        return x1;
    }

    /** Returns the Area of Vision Camera 2. */
    public static double getArea1Vis() { //front
        return area1;
    }

    /** Returns the X of Vision Camera 2. */
    public static double getX2Vis() { //back
        return x2;
    }

    /** Returns the Area of Vision Camera 2. */
    public static double getArea2Vis() { //back
        return area2;
    }

    @Override
    public void testInit() {
        RobotMap.rightElevator.resetEncoder();
      //  RobotMap.intakeGyro.reset();
        RobotMap.rightArm.resetEncoder();
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
        //Elevator
        double elevatorSpeed = Num.deadband(Robot.m_oi.oper.getY(), 0.05);

        kElevatorSubsystem.set(elevatorSpeed);

        //Arm
        double armSpeed = Num.deadband(-Robot.m_oi.oper.getRawAxis(5), 0.05);
        RobotMap.rightArm.set(armSpeed);
        RobotMap.leftArm.set(armSpeed);

        //Wrist
        double upSpeed = 1 + Num.deadband(Robot.m_oi.oper.getRawAxis(3), 0.05);
        double downSpeed = 1 + Num.deadband(Robot.m_oi.oper.getRawAxis(4), 0.05);

        if (Robot.m_oi.leftTriggerOper.get()) { //moving up
            Robot.kWristSubsystem.set(upSpeed);
        }
        else if (Robot.m_oi.rightTriggerOper.get()) { //moving down
            Robot.kWristSubsystem.set(-downSpeed);
        }
        else {
            Robot.kWristSubsystem.set(0);
        }

        //Telescope
        double telescopeSpeed = 0;

        if (Robot.m_oi.squareOper.get()) { //out
            telescopeSpeed = 1;
        }
        else if (Robot.m_oi.xOper.get()) { //in
            telescopeSpeed = -1;
        }
        
        Robot.kTelescopeSubsystem.set(telescopeSpeed);
        
        if (Robot.m_oi.circleOper.get()) {
            Robot.kIntakeSubsystem.extend();
        }
        else if (Robot.m_oi.triangleOper.get()) {
            Robot.kIntakeSubsystem.retract();
        }

        smartDashboard();
    }

    public void cams() {
        NetworkTable table1 = NetworkTableInstance.getDefault().getTable("limelight-front"); //first limelight (front)
        NetworkTableEntry tx1 = table1.getEntry("tx"); //first limelight
        NetworkTableEntry ty1 = table1.getEntry("ty"); //first limelight
        NetworkTableEntry ta1 = table1.getEntry("ta"); //first limelight

        x1 = tx1.getDouble(0.0); //first limelight
        y1 = ty1.getDouble(0.0); //first limelight
        area1 = ta1.getDouble(0.0); //first limelight

        SmartDashboard.putNumber("LimelightX1", x1); //first limelight
        SmartDashboard.putNumber("LimelightY1", y1); //first limelight
        SmartDashboard.putNumber("LimelightA1", area1); //first limelight

        NetworkTable table2 = NetworkTableInstance.getDefault().getTable("limelight-bacc"); //second limelight (back)
        NetworkTableEntry tx2 = table2.getEntry("tx"); //second limelight
        NetworkTableEntry ty2 = table2.getEntry("ty"); //second limelight
        NetworkTableEntry ta2 = table2.getEntry("ta"); //second limelight

        x2 = tx2.getDouble(0.0); //second limelight
        y2 = ty2.getDouble(0.0); //second limelight
        area2 = ta2.getDouble(0.0); //second limelight

        SmartDashboard.putNumber("LimelightX2", x2); //second limelight
        SmartDashboard.putNumber("LimelightX2", y2); //second limelight
        SmartDashboard.putNumber("LimelightA2", area2); //second limelight
    }

    /** Sends data to SmartDashboard. */
    public void smartDashboard() {
        SmartDashboard.putNumber("Pigeon (drive) Yaw", RobotMap.pigeonDrive.getAngle());
        SmartDashboard.putNumber("Pigeon (drive) Pitch", RobotMap.pigeonDrive.getPitch());
        SmartDashboard.putNumber("Pigeon (drive) Roll", RobotMap.pigeonDrive.getRoll());
       // SmartDashboard.putNumber("Wrist Gyro", RobotMap.intakeGyro.getAngle());

        //SmartDashboard.putNumber("Left Drive Encoder", RobotMap.leftMaster.getPosition());
        //SmartDashboard.putNumber("Right Drive Encoder", RobotMap.rightMaster.getPosition());

        SmartDashboard.putNumber("Left Elevator Encoder", RobotMap.leftElevator.getEncoder());
        SmartDashboard.putNumber("Right Elevator Encoder", RobotMap.rightElevator.getEncoder());

        SmartDashboard.putNumber("Right Arm Encoder", RobotMap.rightArm.getPosition());
        //SmartDashboard.putNumber("Right Arm Encoder", RobotMap.rightArm.getPosition());

        SmartDashboard.putNumber("Right Wrist Encoder", RobotMap.wrist.getEncoder());
        SmartDashboard.putBoolean("Intake Limit", RobotMap.ballLimit.get());

        SmartDashboard.putBoolean("Wrist Limit Front", RobotMap.wristFrontLimit.get());
        SmartDashboard.putBoolean("Wrist Limit Back", RobotMap.wristBackLimit.get());

        SmartDashboard.putBoolean("Telescope Limit Out", RobotMap.telescopeOutLimit.get());
        SmartDashboard.putBoolean("Telescope Limit In", RobotMap.telescopeInLimit.get());
        SmartDashboard.putNumber("Telescope Encoder", RobotMap.telescope.getEncoder());

        //SmartDashboard.putBoolean("Arm Limit Front", RobotMap.armFrontLimit.get());
        //SmartDashboard.putBoolean("Arm Limit Back", RobotMap.armBackLimit.get());

        SmartDashboard.putBoolean("Elevator Bottom Limit", RobotMap.elevatorBottomLimit.get());
        SmartDashboard.putBoolean("Elevator Top Limit", RobotMap.elevatorTopLimit.get());

        SmartDashboard.putBoolean("Driver Controlled", !isAuton);
    }

    public void putAuton() {
        auton.getSelected();
        SmartDashboard.putData("Auton Selection", auton);
        auton.addOption(manualHatch, manualHatch);
        auton.addOption(manualCargo, manualCargo);
        auton.addOption(frontRocket, frontRocket);
        auton.addOption(backRocket, backRocket);
        auton.addOption(frontCargo, frontCargo);
        auton.addOption(sideCargo, sideCargo);

        position.getSelected();
        SmartDashboard.putData("Side Selection", position);
        position.addOption(right, right);
        position.addOption(left, left);
    }

    public Command autonSelector() {
        String autonSelected = auton.getSelected();
        String positionSelected = position.getSelected();

        RobotMap.pigeonDrive.reset();
        kDrivingSubsystem.targetAngle = 0;
        isAuton = true;

        int mult = positionSelected.equals(right) ? 1 : -1;

        switch (autonSelected) {
        case frontRocket:
            RobotMap.pigeonDrive.setYaw(32 * mult);
            kDrivingSubsystem.targetAngle = 32 * mult;

            return new FrontRocketGroup(positionSelected);
        case backRocket:
            RobotMap.pigeonDrive.setYaw(180 * mult);
            kDrivingSubsystem.targetAngle = 180 * mult;

            return new BackRocketGroup(positionSelected);
        case frontCargo:
            return new FrontCargoshipGroup(positionSelected);
        case sideCargo:
            return new SideCargoshipGroup(positionSelected);
        case manualHatch:
            return new ManualHatchGroup();
        case manualCargo:
        default:
            return new TeleopCommands();
        }
    }

    public boolean isValidPigeon() {
        if (RobotMap.pigeonDrive.getRoll() == lastRoll && RobotMap.pigeonDrive.getPitch() == lastPitch) {
            if (pigeonValid) {
                pigeonValid = false;
                pigeonValidStart = clock.millis();
            }
            else if (clock.millis() - pigeonValidStart > 10000l) {
                return false;
            }
        }
        else {
            pigeonValid = true;
            lastRoll = RobotMap.pigeonDrive.getRoll();
            lastPitch = RobotMap.pigeonDrive.getPitch();
        }

        return true;
    }
}
