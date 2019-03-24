package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team217.ctre.*;
import org.team217.pid.*;
import frc.robot.*;
import frc.robot.PresetState.Preset;

public class TelescopeSubsystem extends Subsystem {
    WPI_TalonSRX telescope1 = RobotMap.telescope;
    
    DigitalInput telescopeInLimit1 = RobotMap.telescopeInLimit;
    DigitalInput telescopeOutLimit1 = RobotMap.telescopeOutLimit;

    public Preset lastPreset = Preset.Manual;
    APID telescopeAPID = RobotMap.telescopeAPID;

    boolean isTelescopeOut = false;

    @Override
    protected void initDefaultCommand() {
    }

    /**
     * Returns the new telescope speed after checking the telescope limits.
     * 
     * @param speed
     *        The current telescope speed
     */
    public double limitCheck(double speed) {
        if (!telescopeOutLimit1.get() && speed >= 0) {   
            speed = 0;
        }
        else if (!telescopeInLimit1.get()){
            telescope1.resetEncoder();
            if(speed <= 0){
                speed = 0;
            }
        }
        return speed;
    }

    /**
     * Runs the telescope.
     * 
     * @param speed
     *        The telescope speed
     */
    public void set(double speed) {
        double telescopeMult = 1;

        if(telescope1.getEncoder() <= 5000){
            telescopeMult = .75;
        }
        else if(telescope1.getEncoder() >= 15000){
            telescopeMult = .75;
        }

        speed = limitCheck(speed);
        telescope1.set(speed * telescopeMult);
    }


    /** Moves the telescope to the out position. */
    public void setOut() {
        if (!isTelescopeOut) {
            telescopeAPID.initialize();
            isTelescopeOut = true;
        }

        double speed = telescopeAPID.getOutput(telescope1.getEncoder(), 28000); //TODO: Get correct value
        set(speed);
    }

    /** Moves the telescope to the in position. */
    public void setIn() {
        if (isTelescopeOut) {
            telescopeAPID.initialize();
            isTelescopeOut = false;
        }

        double speed = telescopeAPID.getOutput(telescope1.getEncoder(), 0);
        set(speed);
    }

    /** Returns {@code true} if the telescope is out. */
    public boolean getTelescopeOut() {
        return isTelescopeOut;
    }
    
    /**
     * Runs the telescope using {@code APID} to reach a preset.
     * 
     * @param presetState
     *        The {@code Preset} state
     */
    public void preset(Preset presetState) {
        switch (presetState) {
        case Low:
            setIn();
            break;
        case Mid:
            setIn();
            break;
        case High:
            setOut();
            break;
        case Ball:
            setIn();
            break;
        case RocketAdj:
            switch (lastPreset) {
            case Low:
                setIn();
                break;
            case Mid:
                setIn();
                break;
            case High:
                setOut();
                break;
            default:
                presetState = Preset.Manual;
                break;
            }
        default:
            break;
        }
        
        if (!presetState.equals(Preset.Manual) && !presetState.equals(Preset.RocketAdj)) {
            lastPreset = presetState;
        }
    }
}