/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.team217.*;

import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.commands.*;
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
    public static final LiftingMechanism kLiftingMechanism = new LiftingMechanism();
    //public static final ClimbingSubsystem kClimbingSubsystem = new ClimbingSubsystem();
    public static OI m_oi;

    Command teleopCommand;
    Command autonomousCommand;

    static double x1 = 0;
    static double y1 = 0;
    static double area1 = 0;

    static double x2 = 0;
    static double y2 = 0;
    static double area2 = 0;

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
        RobotMap.leftArm.resetEncoder();

        RobotMap.rightElevator.resetEncoder();
        RobotMap.leftElevator.resetEncoder();

        RobotMap.pigeonDrive.reset();
        RobotMap.intakeGyro.reset();

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
        kLiftingMechanism.lastArmPos = RobotMap.rightArm.getPosition();
        kLiftingMechanism.lastElevatorPos = RobotMap.rightElevator.getEncoder();
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
        autonomousCommand = new TeleopCommands(); // TODO: idk what we're doing yet chief
        /*
         * String autoSelected = SmartDashboard.getString("Auto Selector",
         * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
         * = new MyAutoCommand(); break; case "Default Auto": default:
         * autonomousCommand = new ExampleCommand(); break; }
         */

        // schedule the autonomous command (example)
        if (autonomousCommand != null) {
            autonomousCommand.start();
        }
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
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

    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        NetworkTable table1 = NetworkTableInstance.getDefault().getTable("limelight-color"); //first limelight (front)
        NetworkTableEntry tx1 = table1.getEntry("tx"); //first limelight
        NetworkTableEntry ty1 = table1.getEntry("ty"); //first limelight
        NetworkTableEntry ta1 = table1.getEntry("ta"); //first limelight

        x1 = tx1.getDouble(0.0); //first limelight
        y1 = ty1.getDouble(0.0); //first limelight
        area1 = ta1.getDouble(0.0); //first limelight

        SmartDashboard.putNumber("LimelightX1", x1); //first limelight
        SmartDashboard.putNumber("LimelightY1", y1); //first limelight
        SmartDashboard.putNumber("LimelightA1", area1); //first limelight

        NetworkTable table2 = NetworkTableInstance.getDefault().getTable("limelight-ir"); //second limelight (back)
        NetworkTableEntry tx2 = table2.getEntry("tx"); //second limelight
        NetworkTableEntry ty2 = table2.getEntry("ty"); //second limelight
        NetworkTableEntry ta2 = table2.getEntry("ta"); //second limelight

        x2 = tx2.getDouble(0.0); //second limelight
        y2 = ty2.getDouble(0.0); //second limelight
        area2 = ta2.getDouble(0.0); //second limelight

        SmartDashboard.putNumber("LimelightX2", x2); //second limelight
        SmartDashboard.putNumber("LimelightX2", y2); //second limelight
        SmartDashboard.putNumber("LimelightA2", area2); //second limelight

        smartDashboard();

        Scheduler.getInstance().run();
    }

    public static double getX1Vis() { //front
        return x1;
    }

    public static double getArea1Vis() { //front
        return area1;
    }

    public static double getX2Vis() { //back
        return x2;
    }

    public static double getArea2Vis() { //back
        return area2;
    }

    @Override
    public void testInit() {
        RobotMap.rightElevator.resetEncoder();
        RobotMap.intakeGyro.reset();
        RobotMap.rightArm.resetEncoder();
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
        //Elevator
        double elevatorSpeed = Range.deadband(Robot.m_oi.oper.getY(), 0.05);

        kLiftingMechanism.elevator(elevatorSpeed);

        //Arm
        double armSpeed = Range.deadband(Robot.m_oi.oper.getRawAxis(5), 0.05);
        RobotMap.rightArm.set(armSpeed);
        RobotMap.leftArm.set(-armSpeed);

        //Wrist
        double upSpeed = 1 + Range.deadband(Robot.m_oi.oper.getRawAxis(3), 0.05);
        double downSpeed = 1 + Range.deadband(Robot.m_oi.oper.getRawAxis(4), 0.05);

        if (Robot.m_oi.leftTriggerOper.get()) { //moving up
            Robot.kLiftingMechanism.wrist(upSpeed);
        }
        else if (Robot.m_oi.rightTriggerOper.get()) { //moving down
            Robot.kLiftingMechanism.wrist(-downSpeed);
        }
        else {
            Robot.kLiftingMechanism.wrist(0);
        }
    }

    public void smartDashboard() {
        SmartDashboard.putNumber("Pigeon (drive) Yaw", RobotMap.pigeonDrive.getAngle());
        SmartDashboard.putNumber("Pigeon (drive) Pitch", RobotMap.pigeonDrive.getPitch());
        SmartDashboard.putNumber("Pigeon (drive) Roll", RobotMap.pigeonDrive.getRoll());
        SmartDashboard.putNumber("Wrist Gyro", RobotMap.intakeGyro.getAngle());

        //SmartDashboard.putNumber("Left Drive Encoder", RobotMap.leftMaster.getPosition());
        SmartDashboard.putNumber("Right Drive Encoder", RobotMap.rightMaster.getPosition());

        //SmartDashboard.putNumber("Left Elevator Encoder", RobotMap.leftElevator.getEncoder());
        SmartDashboard.putNumber("Right Elevator Encoder", RobotMap.rightElevator.getEncoder());

        //SmartDashboard.putNumber("Right Arm Encoder", RobotMap.rightArm.getPosition());
        //SmartDashboard.putNumber("Right Arm Encoder", RobotMap.rightArm.getPosition());

        SmartDashboard.putNumber("Right Wrist Encoder", RobotMap.wrist.getEncoder());
        SmartDashboard.putBoolean("Intake Limit", RobotMap.ballLimit.get());

        SmartDashboard.putBoolean("Wrist Limit Front", RobotMap.wristFrontLimit.get());
        SmartDashboard.putBoolean("Wrist Limit Back", RobotMap.wristBackLimit.get());

        //SmartDashboard.putBoolean("Arm Limit Front", RobotMap.armFrontLimit.get());
        //SmartDashboard.putBoolean("Arm Limit Back", RobotMap.armBackLimit.get());

        //SmartDashboard.putBoolean("Elevator Bottom Limit", RobotMap.elevatorBottomLimit.get());
        //SmartDashboard.putBoolean("Elevator Top Limit", RobotMap.elevatorTopLimit.get());
    }
}
