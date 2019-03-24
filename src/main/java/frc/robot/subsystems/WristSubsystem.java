package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team217.*;
import org.team217.ctre.*;
import org.team217.rev.*;
import org.team217.wpi.*;
import org.team217.pid.*;
import frc.robot.*;
import frc.robot.PresetState.Preset;

public class WristSubsystem extends Subsystem {
    WPI_TalonSRX wrist1 = RobotMap.wrist;
    CANSparkMax rightArm1 = RobotMap.rightArm;
    
    //AnalogGyro intakeGyro1 = RobotMap.intakeGyro;

    DigitalInput wristBackLimit1 = RobotMap.wristBackLimit;
    DigitalInput wristFrontLimit1 = RobotMap.wristFrontLimit;

    public Preset lastPreset = Preset.Manual;
    
    PID wristGyroPID = RobotMap.wristGyroPID;
    APID wristAPID = RobotMap.wristAPID;

    @Override
    protected void initDefaultCommand() {
    }

    /**
     * Returns the new wrist speed after checking the wrist limits.
     * 
     * @param speed
     *        The current wrist speed
     */
    public double limitCheck(double speed) {
        if (!wristBackLimit1.get() && speed <= 0.0) { 
            speed = 0;
        }
        else if (!wristFrontLimit1.get() && speed >= 0.0) { 
            speed = 0;
        }
        return speed;
    }

    /**
     * Runs the wrist.
     * 
     * @param speed
     *        The wrist speed
     */
    public void set(double speed) {
        double wristMult = .7;
        speed = limitCheck(speed);
        wrist1.set(speed * wristMult);
    }
    
    /** Runs the wrist using an {@code AnalogGyro}. */
/*
    public void auto() {
        double armAngle = intakeGyro1.getAngle(); // Might be pitch or yaw, depending on how electical electricities
        double speed = 0;

        if (rightArm1.getPosition() < 60) {
            speed = Num.inRange(-wristGyroPID.getOutput(armAngle, 0), 1.0);
        }
        else if (rightArm1.getPosition() >= 60 && rightArm1.getPosition() <= 75) {
            if (armAngle < 80) {
                speed = -.7073;
            }
            else if (armAngle > 100) {
                speed = .7073;
            }
        }
        else {
            speed = Num.inRange(-wristGyroPID.getOutput(armAngle, 178), 0.5);
        }

        set(speed);
    }
*/

    /**
     * Runs the wrist using {@code APID} to reach a preset.
     * 
     * @param presetState
     *        The {@code Preset} state 
     * @param isBack
     *        {@code true} if the preset moves the arm to the back region of the bot
     */
    public void preset(Preset presetState, boolean isBack) {
        double target = 0;
        switch (presetState) {
        case Low:
            target = (isBack) ? -2918 : -19;
            break;
        case Mid:
            target = (isBack) ? -275 : -3608;
            break;
        case High:
            target = (isBack) ? -264 : -3608;
            break;
        case Ball:
            target = (isBack) ? -2080 : -2080;
            break;
        case RocketAdj:
            switch (lastPreset) {
                case Low:
                target = (isBack) ? -2918 : -19;
                break;
            case Mid:
                target = (isBack) ? -275 : -3608;
                break;
            case High:
                target = (isBack) ? -264 : -3608;
                break;
            default:
                presetState = Preset.Manual;
                break;
            }
        default:
            break;
        }

        double speed = 0;
        
        if (!presetState.equals(Preset.Manual)) {
            if (!lastPreset.equals(presetState)) {
                wristAPID.initialize();
            }
            else {
                speed = Num.inRange(-wristAPID.getOutput(wrist1.getEncoder(), target), 2);
            }

            if (!presetState.equals(Preset.RocketAdj)) {
                lastPreset = presetState;
            }
        }

        set(speed);
    }
}