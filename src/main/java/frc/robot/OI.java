/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    public Joystick driver = new Joystick(RobotMap.driverPort);
    public Joystick oper = new Joystick(RobotMap.operPort);

    // Operator Buttons

    public Button squareOper = new JoystickButton(oper, RobotMap.buttonSquare); //1
    public Button xOper = new JoystickButton(oper, RobotMap.buttonX); //2
    public Button circleOper = new JoystickButton(oper, RobotMap.buttonCircle); //3
    public Button triangleOper = new JoystickButton(oper, RobotMap.buttonTriangle); //4
    public Button leftBumperOper = new JoystickButton(oper, RobotMap.leftBumper); //5
    public Button rightBumperOper = new JoystickButton(oper, RobotMap.rightBumper); //6
    public Button leftTriggerOper = new JoystickButton(oper, RobotMap.leftTrigger); //7
    public Button rightTriggerOper = new JoystickButton(oper, RobotMap.rightTrigger); //8
    public Button buttonShareOper = new JoystickButton(oper, RobotMap.buttonShare); //9
    public Button buttonOptionOper = new JoystickButton(oper, RobotMap.buttonOption); //10
    public Button leftAnalogOper = new JoystickButton(oper, RobotMap.leftAnalog); //11
    public Button rightAnalogOper = new JoystickButton(oper, RobotMap.rightAnalog); //12
    public Button psButtonOper = new JoystickButton(oper, RobotMap.psButton); //13
    public Button touchPadOper = new JoystickButton(oper, RobotMap.touchPad); //14

    // Driver Buttons

    public Button squareDriver = new JoystickButton(driver, RobotMap.buttonSquare); //1
    public Button xDriver = new JoystickButton(driver, RobotMap.buttonX); //2
    public Button circleDriver = new JoystickButton(driver, RobotMap.buttonCircle); //3
    public Button triangleDriver = new JoystickButton(driver, RobotMap.buttonTriangle); //4
    public Button leftBumperDriver = new JoystickButton(driver, RobotMap.leftBumper); //5
    public Button rightBumperDriver = new JoystickButton(driver, RobotMap.rightBumper); //6
    public Button leftTriggerDriver = new JoystickButton(driver, RobotMap.leftTrigger); //7
    public Button rightTriggerDriver = new JoystickButton(driver, RobotMap.rightTrigger); //8
    public Button buttonShareDriver = new JoystickButton(driver, RobotMap.buttonShare); //9
    public Button buttonOptionDriver = new JoystickButton(driver, RobotMap.buttonOption); //10
    public Button leftAnalogDriver = new JoystickButton(driver, RobotMap.leftAnalog); //11
    public Button rightAnalogDriver = new JoystickButton(driver, RobotMap.rightAnalog); //12
    public Button psButtonDriver = new JoystickButton(driver, RobotMap.psButton); //13
    public Button touchPadDriver = new JoystickButton(driver, RobotMap.touchPad); //14
}
