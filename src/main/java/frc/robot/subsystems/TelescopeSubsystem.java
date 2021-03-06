package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.team217.*;
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

    public double lastTelescopePos = 0;

    int telescopeState = 0;

    public boolean atPreset = false;

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
            lastTelescopePos = 0;

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
        speed = Num.inRange(speed, 1);
        
        double telescopeMult = 0.8;

        if (speed != 0) {
            lastTelescopePos = telescope1.getEncoder();
        }
        else {
            if (Math.abs(lastTelescopePos - telescope1.getEncoder()) > 500) {
                lastTelescopePos = telescope1.getEncoder();
            }
            speed = RobotMap.telescopeHoldPID.getOutput(telescope1.getEncoder(), lastTelescopePos);
            telescopeMult = 1;
        }

        speed = limitCheck(speed);
        telescope1.set(speed * telescopeMult);
    }

    /** Moves the telescope to the in position. */
    public void setIn() {
        if (telescopeState != 0) {
            telescopeAPID.initialize();
            telescopeState = 0;
        }

        atPreset = Num.isWithinRange(telescope1.getEncoder(), -800, 800);

        double speed = telescopeAPID.getOutput(telescope1.getEncoder(), 0);
        set(speed);
    }

    /** Moves the telescope to the out position. */
    public void setOut() {
        if (telescopeState != 1) {
            telescopeAPID.initialize();
            telescopeState = 1;
        }

        atPreset = Num.isWithinRange(telescope1.getEncoder(),  13200, 14800);

        double speed = telescopeAPID.getOutput(telescope1.getEncoder(), 14000); //TODO: Get correct value //comp is 14300
        set(speed);
    }

    public void setClimb() {
        if (telescopeState != 2) {
            telescopeAPID.initialize();
            telescopeState = 2;
        }

        atPreset = Num.isWithinRange(telescope1.getEncoder(), 9560, 11160);

        double speed = telescopeAPID.getOutput(telescope1.getEncoder(), 10360);
        set(speed);
    }

    public void setStart() {
        if (telescopeState != 3) {
            telescopeAPID.initialize();
            telescopeState = 3;
        }

        atPreset = Num.isWithinRange(telescope1.getEncoder(), 8700, 10300);

        double speed = telescopeAPID.getOutput(telescope1.getEncoder(), 9500);
        set(speed);
    }

    /** Returns {@code 1} if the telescope is out, {@code 0} if in, {@code 2} if climbing position. */
    public int getTelescopeState() {
        return telescopeState;
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
        case HighBall:
            setOut();
            break;
        case Ball:
            setIn();
            break;
        case Climb:
            setClimb();
            break;
        case Start:
            setStart();
            break;
        default:
            break;
        }

        lastPreset = presetState;
    }

    public void reset() {
        telescope1.set(0);
    }
}