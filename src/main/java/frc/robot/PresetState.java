package frc.robot;

import org.team217.*;

/**
 * Checks the state of various controls to determine if the bot should run presets.
 * 
 * @author ThunderChickens 217
 */
public class PresetState {
    /** Returns {@code true} if all controls, excluding `oper.getPOV()`, permit presets. */
    public static boolean getStatus() {
        return getArmStatus() && getWristStatus() && getElevStatus();
    }

    /** Returns {@code true} if the arm controls permit presets. */
    public static boolean getArmStatus() {
        double armSpeed = Range.deadband(Robot.m_oi.oper.getRawAxis(5), 0.08);
        return armSpeed == 0;
    }

    /** Returns {@code true} if the wrist controls permit presets. */
    public static boolean getWristStatus() {
        return !(Robot.m_oi.rightTriggerOper.get() || Robot.m_oi.leftTriggerOper.get());
    }

    /** Returns {@code true} if the elevator controls permit presets. */
    public static boolean getElevStatus() {
        double leftAnalog = Range.deadband(Robot.m_oi.oper.getY(), 0.08);
        return leftAnalog == 0;
    }

    /** Returns {@code true} if `oper.getPOV()` permits presets. */
    public static boolean getPOVStatus() {
        return Robot.m_oi.oper.getPOV() != -1;
    }
}